import request from "./reuqest";

const getAllArticles = (page, pageSize, order, userId) => {
  return request.get("/articles", {
    params: {
      page: page,
      pageSize: pageSize,
      order: order,
      userId: userId,
    },
  });
};

/**
 * 发布文章
 * @param {*} title
 * @param {*} summary
 * @param {*} rawhtml
 * @param {*} categorys
 * @param {*} tags
 * @param {*} image 图片资源
 * @returns
 */
const SendArticle = (title, summary, rawhtml, categorys, tags, image) => {
  let formdata = new FormData();
  formdata.append("image", image);
  formdata.append(
    "article",
    JSON.stringify({
      title: title,
      summary: summary,
      articleRaw: rawhtml,
      categorys: categorys,
      tags: tags,
    })
  );
  return request({
    method: "POST",
    url: "/articles",
    data: formdata,
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

const getSwiper = () => {
  return request({
    method: "GET",
    url: "/articles/getSwiper",
  });
};

const searchArticles = (message) => {
  return request({
    method: "GET",
    url: "/articles/search",
    params: {
      message,
    },
  });
};

export {
  getAllArticles,
  SendArticle,
  getArticleById,
  ArticleLike,
  ArticleStar,
  getSwiper,
  searchArticles,
};
