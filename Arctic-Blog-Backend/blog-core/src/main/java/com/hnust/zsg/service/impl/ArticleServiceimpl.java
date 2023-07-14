package com.hnust.zsg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.zsg.Exception.InsertException;
import com.hnust.zsg.context.user.UserContextHolder;
import com.hnust.zsg.convert.ArticleConvert;
import com.hnust.zsg.entity.doc.ArticleDoc;
import com.hnust.zsg.entity.doc.ArticleEsDOC;
import com.hnust.zsg.entity.dto.SwiperVO;
import com.hnust.zsg.entity.dto.UploadResponeDTO;
import com.hnust.zsg.entity.po.ArticleLikeStarPO;
import com.hnust.zsg.entity.po.ArticlePO;
import com.hnust.zsg.entity.po.CategoryPO;
import com.hnust.zsg.entity.po.TagPO;
import com.hnust.zsg.entity.vo.*;
import com.hnust.zsg.http.HttpUtils;
import com.hnust.zsg.mapper.*;
import com.hnust.zsg.service.ArticleService;
import com.hnust.zsg.service.CategoryService;
import com.hnust.zsg.service.TagService;
import com.hnust.zsg.utils.ElasticSearchUtil;
import com.hnust.zsg.utils.JacksonUtil;
import com.hnust.zsg.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleServiceimpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private ElasticSearchUtil elasticSearchUtil;


    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String REDIS_ARTICLES = "article";
    private static final String ARTICLES_USER_LIKE = "article:user:isLike";
    private static final String ARTICLES_USER_STAR = "article:user:isStar";

    /**
     * 业务层处理分页逻辑
     *
     * @param page
     * @param order
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class, timeout = 10, isolation = Isolation.READ_COMMITTED)
    public Page<ArticleListVO> getAllArticle(IPage<ArticlePO> page, String order,Long userId) {
        Page<Long> page1 = articleMapper.getAllArticleId(page, order,userId);
        Page<ArticleListVO> article = (Page<ArticleListVO>) page1.convert(this::apply);
        return article;
    }

    /**
     * 查找指定文章信息并返回
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 10, rollbackFor = RuntimeException.class)
    public ArticleDetailVO findArticleById(Long id) {
        ArticlePO articlePO = articleMapper.getArticleById(id);
        ArticleDetailVO articleDetailVO = ArticleConvert.INSTANCE.PO_TO_DETAIL_VO(articlePO);
        UserVO user = userMapper.getUserById(articlePO.getAuthorId());
        articleDetailVO.setAuthor(user);
        List<TagPO> tags = tagMapper.getAllTagsById(id);
        articleDetailVO.setTags(tags);
        List<CategoryPO> categorys = categoryService.getAllCategoryByArticleId(id);
        articleDetailVO.setCategorys(categorys);
        ArticleDoc articleDoc = mongoTemplate.findById(id, ArticleDoc.class);
        if (articleDoc != null) {
            articleDetailVO.setArticleRaw(articleDoc.getArticle_raw());
        }

        return articleDetailVO;

    }

    /**
     * 开启事务添加文章
     *
     * @param articleContentVO
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class, timeout = 480*100, isolation = Isolation.READ_COMMITTED)
    public void addArticle(MultipartFile imageFile, ArticleContentVO articleContentVO) throws RuntimeException {
        String imageUrl = "";
        String deleteUrl = "";
        if (imageFile != null) {
            okhttp3.RequestBody body = null;
            try {
                body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("smfile", imageFile.getOriginalFilename(),
                                okhttp3.RequestBody.create(MediaType.parse("application/octet-stream"),
                                        imageFile.getBytes()))
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Response response = HttpUtils.uploadImage(body, 3);

            String responeString = null;
            try {
                responeString = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadResponeDTO uploadResponeDTO = JacksonUtil.parseObject(responeString, UploadResponeDTO.class);
            if(uploadResponeDTO.getSuccess()!=null&&uploadResponeDTO.getSuccess()){
                imageUrl = uploadResponeDTO.getData().getUrl();
                deleteUrl = uploadResponeDTO.getData().getDelete();
            }
        }

        ArticlePO articlePO = ArticleConvert.INSTANCE.ARTICLE_CONTENTVO_TO_ArticlePO(articleContentVO);
        articlePO.setImageUrl(imageUrl);
        articlePO.setDeleteUrl(deleteUrl);
        articleMapper.insert(articlePO);
        Long articleId = articlePO.getId();
        if (articleId > 0) {
            ArticleDoc articleDoc = new ArticleDoc(articleId, articleContentVO.getArticleRaw());
            mongoTemplate.insert(articleDoc, "article_content");
            try {
                if (articleContentVO.getTags() != null) {
                    tagService.insertTagsByArticleId(articleId, articleContentVO.getTags(), articleContentVO.getAuthorId());
                }
                if (articleContentVO.getCategorys() != null) {
                    categoryService.insertCategorysByArticleId(articleId, articleContentVO.getCategorys(), articleContentVO.getAuthorId());

                }
            } catch (RuntimeException e) {
                Query query = new Query(Criteria.where("_id").is(articleId));
                mongoTemplate.remove(query, ArticleDoc.class);
                e.printStackTrace();
                throw new InsertException();
            }
        }
    }

    public Boolean modifyArticle(ArticleContentVO articleContentVO) {
        return true;
    }

    /**
     * 开启事务删除文章及其标签和分类
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class, timeout = 10, isolation = Isolation.READ_COMMITTED)
    public String deleteArticleById(Long id) {
        Long authorId = UserContextHolder.getContext().getMyUserVO().getId();
        String deleteUrl = articleMapper.getDeleteUrlById(id, authorId);
        if (deleteUrl == null) {
            return null;
        }

        LambdaQueryWrapper<ArticlePO> queryWrapper1 = new LambdaQueryWrapper();
        queryWrapper1.eq(ArticlePO::getAuthorId, authorId);
        queryWrapper1.eq(ArticlePO::getId, id);
        articleMapper.delete(queryWrapper1);

        LambdaQueryWrapper<TagPO> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(TagPO::getArticleId, id);
        tagService.remove(queryWrapper2);
        categoryService.removeArticleCategorys(id);
        //删除对应文章的文字内容
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, ArticleDoc.class);
        return deleteUrl;
    }

    @Override
    public void likeArticle(Long userId, Long articleId, Boolean islike) {
        //设置一小时的过期时间
        if (!RedisUtil.hasKey(ARTICLES_USER_LIKE)) {
            RedisUtil.put(ARTICLES_USER_LIKE, "articleId:" + articleId + ":userId:" + userId, islike);
            RedisUtil.expire(ARTICLES_USER_LIKE, 3600L);
        } else {
            RedisUtil.put(ARTICLES_USER_LIKE, "articleId:" + articleId + ":userId:" + userId, islike);
        }

    }

    /**
     * 用户给文章点赞或者取消点赞
     *
     * @param userId
     * @param articleId
     * @param isStar
     */
    @Override
    public void starArticle(Long userId, Long articleId, Boolean isStar) {
        //设置一小时的过期时间
        if (!RedisUtil.hasKey(ARTICLES_USER_STAR)) {
            RedisUtil.put(ARTICLES_USER_STAR, "articleId:" + articleId + ":userId:" + userId, isStar);
            RedisUtil.expire(ARTICLES_USER_STAR, 3600L);
        } else {
            RedisUtil.put(ARTICLES_USER_STAR, "articleId:" + articleId + ":userId:" + userId, isStar);
        }

    }

    /**
     * 将用户的点赞和收藏记录一次性写入数据库，同时用定时任务来执行
     */
    @Override
    public void insertLikeAndStar() {
        Map<String, Boolean> articleLikeMap = RedisUtil.hget(ARTICLES_USER_LIKE);
        Map<String, Boolean> articleStarMap = RedisUtil.hget(ARTICLES_USER_STAR);
        Map<String, ArticleLikeStarPO> articleLikeStarMap = new HashMap<>();
        //点赞列表不为空
        if (!articleLikeMap.isEmpty()) {
            for (String key : articleLikeMap.keySet()) {
                Boolean value = articleLikeMap.get(key);
                ArticleLikeStarPO articleLikeStarPO = new ArticleLikeStarPO();
                String[] strs = key.split(":");
                Long articleId = Long.valueOf(strs[1]);
                Long userId = Long.valueOf(strs[3]);
                articleLikeStarPO.setArticleId(articleId);
                articleLikeStarPO.setUserId(userId);
                articleLikeStarPO.setIsLike(value);
                articleLikeStarMap.put("articleId:" + articleId + ":userId:" + userId, articleLikeStarPO);
            }
        }
        //收藏列表不为空
        if (!articleStarMap.isEmpty()) {
            for (String key : articleStarMap.keySet()) {
                Boolean value = articleStarMap.get(key);

                String[] strs = key.split(":");
                Long articleId = Long.valueOf(strs[1]);
                Long userId = Long.valueOf(strs[3]);
                ArticleLikeStarPO temp = articleLikeStarMap.get("articleId:" + articleId + ":userId:" + userId);
                if (temp != null) {
                    temp.setIsStar(true);
                    articleLikeStarMap.put("articleId:" + articleId + ":userId:" + userId, temp);
                }
                ArticleLikeStarPO articleLikeStarPO = new ArticleLikeStarPO();
                articleLikeStarPO.setArticleId(articleId);
                articleLikeStarPO.setUserId(userId);
                articleLikeStarPO.setIsStar(value);
                articleLikeStarMap.put("articleId:" + articleId + ":userId:" + userId, articleLikeStarPO);

            }
        }
        //批量插入或者更新
        articleLikeStarSaveOrUpdateBatch(articleLikeStarMap.values());
    }

    @Override
    public Boolean articleLikeStarSaveOrUpdateBatch(Collection<ArticleLikeStarPO> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        //获取SqlSession对象，并且关闭自动提交
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        //通过SQLsession的方式获取mapper对象,spring管理下是默认自动提交的
        ArticleLikeStarMapper articleMapperBatch = sqlSession.getMapper(ArticleLikeStarMapper.class);
        //获取collection集合迭代器
        Iterator<ArticleLikeStarPO> iterator = collection.iterator();
        try {
            for (int i = 1; iterator.hasNext(); i++) {
                ArticleLikeStarPO articleLikeStarPO = iterator.next();
                articleLikeStarPO.setId(articleMapperBatch.getIdByArticleIdAndUserId(articleLikeStarPO.getArticleId(), articleLikeStarPO.getUserId()));
                articleMapperBatch.insertOrUpdate(articleLikeStarPO);
                //每200次插入或者更新操作进行一次提交
                if (i % 200 == 0 || !iterator.hasNext()) {
                    sqlSession.commit();
                    //清除缓存，防止oom
                    sqlSession.clearCache();
                }

            }
        } catch (Exception e) {
            sqlSession.rollback();
            return false;
        } finally {
            //关闭sqlsession
            sqlSession.close();
        }
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 10, rollbackFor = RuntimeException.class)
    public void SwipperToRedis() {
        List<SwiperVO> pictures = articleMapper.getSwipperPicture();

        RedisUtil.hset(RedisUtil.HOME_SWIPPER_4, pictures, 1L, TimeUnit.DAYS);
    }

    @Override
    public ArticleSearchVO searchArticle(String message) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("summary")
                .field("content")
                .field("title")
                //设置关键字只读取25字的上下文
                .fragmentSize(25)
                //设置某个字段未匹配到的情况下，返回的最大字数，初始化默认值是0
                .noMatchSize(10)
                //设置最多只读取一条关键字
                .numOfFragments(1)
                .preTags("<mark>")
                .postTags("</mark>");

        builder.withHighlightBuilder(highlightBuilder);
        //设置最多返回五条记录
        builder.withPageable(PageRequest.of(0, 5));
        //构建查询条件
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery(message, "summary", "content", "title"));
        builder.withQuery(boolQuery);
        SearchHits<ArticleEsDOC> response = elasticSearchUtil.search(builder.build(), ArticleEsDOC.class);
        List<ArticleEsDOC> articles=new ArrayList<>((int)response.getTotalHits());
        response.get().forEach(e->
                {
                    Map<String,List<String>> highLight=e.getHighlightFields();
                    ArticleEsDOC article=new ArticleEsDOC();
                    article.setId(e.getContent().getId());
                    article.setContent(highLight.get("content").get(0));
                    article.setSummary(highLight.get("summary").get(0));
                    article.setTitle(highLight.get("title").get(0));
                    articles.add(article);
                }
                );
        ArticleSearchVO result=new ArticleSearchVO();
        result.setArticles(articles);
        result.setTotal(response.getTotalHits());
        return result;
    }

    private ArticleListVO apply(Long id) {

        //读取Redis查询是否有该文章的信息
        ArticleListVO articleListVO1 = null;
        articleListVO1 = RedisUtil.entries(REDIS_ARTICLES + ":" + id + ":info", ArticleListVO.class);
        if (articleListVO1 != null) {
            return articleListVO1;
        }
        ArticlePO articlePO = articleMapper.getArticleById(id);
        Long authorId = articlePO.getAuthorId();
        ArticleListVO articleListVO2 = ArticleConvert.INSTANCE.POToVo(articlePO);
        articleListVO2.setAuthorName(userMapper.getUsernameById(authorId));
        articleListVO2.setCommentAmount(commentMapper.getCommentAmountByArticleId(id));
        //将文章列表信息添加到Redis缓存中
        RedisUtil.hset(REDIS_ARTICLES + ":" + id + ":info", articleListVO2, 3600L);
        return articleListVO2;
    }
}
