import React, { useState } from "react";
import aboutStyle from "./About.module.scss";
import MyComment from "../../components/comment/MyComment";
import PersonCard from "../../components/personCard/PersonCard";
import { addAboutComment, getAboutCommnets } from "../../api/comment";
import { showDateTime } from "../../utils/DatetimeUtils";
import { CheckOutlined, QqOutlined } from "@ant-design/icons";
import { message } from "antd";

const About = () => {
  const [page, setPage] = useState(1);
  //下滑每次加载5条评论
  const [pageSize] = useState(5);
  const [hasmore, setHasmore] = useState(true);
  const [commentData, setCommentData] = useState([]);
  //滚动加载更多
  const loadmore = () => {
    getAboutCommnets(page, pageSize)
      .then((res) => {
        if (res.data.page.total !== 0)
          setCommentData(
            commentData.concat(
              res.data.page.records.map((record) => {
                const CT = record.createTime;
                record.createTime = showDateTime(CT);
                if (record.children !== null) {
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

  return (
    <div className={aboutStyle.aboutContainer}>
      <h1 className={aboutStyle.HeadFont}>About</h1>
      <div className={aboutStyle.mainGrid}>
        <div className={aboutStyle.leftGrid}>
          <div className={aboutStyle.showDetail}>
            <h3 className={aboutStyle.h3Title}>个人简介</h3>
            <span className={aboutStyle.description}>
              湖南科技大学&nbsp;&nbsp;&nbsp;计算机科学与工程学院&nbsp;&nbsp;&nbsp;数据科学与大数据技术专业&nbsp;&nbsp;&nbsp;大三
            </span>
            <h3 className={aboutStyle.h3Title}>编程技能</h3>
            <span className={aboutStyle.description}>
              <ul>
                <li>
                  <span className="list-point" />
                  Java
                  <CheckOutlined className="my-tick" />
                </li>
                <li>
                  <span className="list-point" />
                  C语言
                  <CheckOutlined className="my-tick" />
                </li>
                <li>
                  <span className="list-point" />
                  JavaScript
                  <CheckOutlined className="my-tick" />
                </li>
                <li>
                  <span className="list-point" />
                  Python
                  <CheckOutlined className="my-tick" />
                </li>
                <li>
                  <span className="list-point" />
                  Spring+SpringMVC+SpringBoot+MyBatis
                  <CheckOutlined className="my-tick" />
                </li>
                <li>
                  <span className="list-point" />
                  RDBMS: Mysql, &nbsp;&nbsp;&nbsp;NoSQL: Redis, MongoDB
                  <CheckOutlined className="my-tick" />
                </li>
                <li>
                  <span className="list-point" />
                  Font Skills: Vue and React ,Scss ,Es6{" "}
                  <CheckOutlined className="my-tick" />
                </li>
              </ul>
            </span>
            <h3 className={aboutStyle.h3Title}>联系方式</h3>
            <span className={aboutStyle.commonLine}>
              <QqOutlined style={{ fontSize: 24 }} /> <p>QQ:1064485698 </p>
            </span>
          </div>

          <MyComment
            className={aboutStyle.Comments}
            commentData={commentData}
            setCommentData={setCommentData}
            hasmore={hasmore}
            loadmore={loadmore}
            addComment={addAboutComment}
          />
        </div>
        <div className={aboutStyle.col1}>
          <PersonCard />
        </div>
      </div>
    </div>
  );
};

export default About;
