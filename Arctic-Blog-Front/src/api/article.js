import request from "./reuqest";

const getAllArticles = (page, pageSize, order) => {
  return request.get("/articles", {
    params: {
      page: page,
      pageSize: pageSize,
      order: order,
    },
  });
};

const SendArticle = (
  title,
  summary,
  html,
  rawhtml,
  categorys,
  tags,
  authorId,
  deleteUrl,
  imageUrl
) => {
  return request.post("/articles", {
    title: title,
    summary: summary,
    articleRaw: rawhtml,
    articleHtml: html,
    categorys: categorys,
    tags: tags,
    authorId: authorId,
    deleteUrl: deleteUrl,
    imageUrl: imageUrl,
  });
};

const getArticleById = (id) => {
  return request.get("/articles/details/" + id);
};

/**
 * 用户给文章点赞功能,并且需要根据用户id进行限流
 * @param {*} articleId
 * @param {*} userId
 * @param {*} islike
 */
const ArticleLike = (articleId, userId, islike) => {
  return request({
    method: "POST",
    params: { articleId: articleId, userId: userId, islike: islike },
    url: "/articles/like",
  });
};

/**
 * 用户给文章点击收藏功能,并且需要根据后端返回结果进行限制点赞功能
 * @param {*} articleId 
 * @param {*} userId 
 * @param {*} isStar 
 * @returns 
 */
const ArticleStar = (articleId, userId, isStar) => {
  return request({
    method: "POST",
    params: { articleId: articleId, userId: userId, isStar: isStar },
    url: "/articles/isStar",
  });
};

const getSwiper=()=>{
  return request({
    method:"GET",
    url:"/articles/getSwiper"
  })
}
export { getAllArticles, SendArticle, getArticleById, ArticleLike,ArticleStar,getSwiper };
