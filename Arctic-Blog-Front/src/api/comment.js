import request from "./reuqest";
const getAboutCommnets = (page, pageSize) => {
  return request({
    method: "GET",
    url: "/comments/about",
    params: {
      page: page,
      pageSize: pageSize,
    },
  });
};

const getArticleCommnets = (page, pageSize, id) => {
  return request({
    method: "GET",
    url: "/comments/article",
    params: {
      page,
      pageSize,
      articleId: id,
    },
  });
};
const getTalkComments = (page, pageSize, id) => {
  return request({
    method: "GET",
    url: "/comments/talk",
    params: {
      page,
      pageSize,
      talkId: id,
    },
  });
};

const addAboutComment = (content) => {
  return request({
    method: "POST",
    data: {
      content
    },
    url: "/comments/about",
  });
};

const addArticleComment = (articleId, content) => {
  return request({
    method: "POST",
    url: "/comments/article",
    params: {
      articleId,
    },
    data:{
      content
    }
  });
};

const addTalkComment = (talkId, content) => {
  return request({
    method: "POST",
    url: "/comments/article",
    params: {
      talkId,
    },
    data:{
      content
    }
  });
};
const addReply = (content, pid, reply_uid) => {
  return request({
    method: "POST",
    url: "/comments/reply",
    data: {
      content: content,
      pid: pid,
      reply_uid: reply_uid,
    },
  });
};
export {
  getAboutCommnets,
  addAboutComment,
  getArticleCommnets,
  getTalkComments,
  addReply,
  addArticleComment,
  addTalkComment,
};
