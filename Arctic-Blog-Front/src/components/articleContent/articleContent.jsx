import { Col, Row, message } from "antd";
import React, { useEffect, useState } from "react";
import indexstyle from "./articleContent.module.scss";
import { getArticleById } from "../../api/article";
import { useSearchParams } from "react-router-dom";
import { ClockCircleFilled, EyeFilled, StarFilled } from "@ant-design/icons";
import { addArticleComment, getArticleCommnets } from "../../api/comment";
import MyComment from "../comment/MyComment";
import { showDateTime } from "../../utils/DatetimeUtils";

const ArticleContent = () => {
  const [page, setPage] = useState(1);
  const [pageSize] = useState(5);
  const [hasmore, setHasmore] = useState(true);
  const [commentData, setCommentData] = useState([]);

  const [article, setArticle] = useState({
    id: "",
    title: "",
    imageUrl: "",
    summary: "",
    articleRaw: "",
    articleHtml: "",
    author: { id: "", username: "", avatar: "" },
    createTime: "",
    articleStars: 0,
    articleViews: 0,
    articleLikes: 0,
    tags: [{ id: "", tagName: "" }],
    categorys: [{ id: "", categoryName: "" }],
  });
  const [search] = useSearchParams();
  const articleId = search.get("articleId");

  const loadmore = () => {
    getArticleCommnets(page, pageSize, articleId)
      .then((res) => {
        setCommentData(
          commentData.concat(
            res.data.page.records.map((record) => {
              const CT = record.createTime;
              record.createTime = showDateTime(CT);
              if (
                record.children !== null &&
                typeof record.children !== "undefined"
              ) {
                const length = record.children.length;
                for (let i = 0; i < length; i++) {
                  const CT = record.children[i].createTime;
                  record.children[i].createTime = showDateTime(CT);
                }
              }
              return record;
            })
          )
        );

        setPage(page + 1);
        setHasmore(res.data.hasmore);
      })
      .catch((err) => {
        console.log(err);
        message.error("网络响应超时");
        setHasmore(false);
      });
  };
  useEffect(() => {
    getArticleById(articleId)
      .then((res) => {
        console.log(res);
        setArticle(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [articleId]);

  useEffect(() => {
    document.title = article.title;
  }, [article.title]);
  return (
    <>
      <div className={indexstyle.articleContainer}>
        <Row className={indexstyle.titleContainer}>{article.title}</Row>
        <Row className={indexstyle.detailContainer}>
          <Col style={{ fontWeight: 600 }} span={3}>
            作者:{article.author.username}
          </Col>
          <Col offset={1} span={5}>
            <ClockCircleFilled className={indexstyle.antIcon} />
            发布于{article.createTime.replace("T", " ")}
          </Col>
          <Col offset={1} span={2}>
            <EyeFilled className={indexstyle.antIcon} />
            {article.articleViews}
          </Col>
          <Col offset={1} span={11}>
            <StarFilled className={indexstyle.antIcon} />
            {article.articleStars}
          </Col>
          <Col className={indexstyle.line2Col}>
            <span>分类专栏:</span>
            {article.categorys.map((category) => {
              return <span className="my-category" key={category.id}>{category.categoryName}</span>;
            })}
          </Col>
          <Col className={indexstyle.line2Col}>
            <span>文章标签:</span>
            {article.tags.map(tag=>{
              return <span className="my-tag" key={tag.id}>{tag.tagName}</span>
            })}
          </Col>
        </Row>
        <Row
          className={indexstyle.contentContainer}
          dangerouslySetInnerHTML={{ __html: article.articleHtml }}
        ></Row>
        <Row className={indexstyle.extraContainer}></Row>
      </div>

      <MyComment
        className={indexstyle.articleComments}
        commentData={commentData}
        setCommentData={setCommentData}
        loadmore={loadmore}
        hasmore={hasmore}
        addComment={addArticleComment}
        articleId={articleId}
      />
    </>
  );
};

export default ArticleContent;
