import React, { useEffect, useState } from "react";
import indexStyle from "../pages/index/index.module.scss";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Autoplay, Pagination as Pagination1 } from "swiper";
import { Col, Image, Row, Tabs } from "antd";
import { getAllArticles, getSwiper } from "../api/article";
import { Pagination } from "antd";
import { showDateTime } from "../utils/DatetimeUtils";
import {
  ClockCircleOutlined,
  EyeOutlined,
  LikeFilled,
  MessageOutlined,
} from "@ant-design/icons";
import PersonCard from "./personCard/PersonCard";
import "swiper/scss";
import "swiper/scss/navigation";
import "swiper/scss/pagination";

const items = [
  {
    key: "hot",
    label: "最热",
  },
  {
    key: "newest",
    label: "最新",
  },
];

//渲染分页组件前进退回按键
const itemRender = (_, type, originalElement) => {
  if (type === "prev") {
    return <span>上一页</span>;
  }
  if (type === "next") {
    return <span>下一页</span>;
  }
  return originalElement;
};

function HomePage() {
  const [pictures, setPictures] = useState([]);
  const [Articles, setArticles] = useState([
    {
      id: "",
      title: "",
      imageUrl: "",
      createTime: "",
      updateTime: "",
      articleLikes: "",
      articleViews: "",
      authorName: "",
      summary: "",
      commentAmount: "",
    },
  ]); //定义文章列表state
  const [page, setPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [total, setTotal] = useState(500);
  const articleBaseUrl = "/index/article/detail";
  const openArticle = (id) => {
    window.open(`${articleBaseUrl}?articleId=${id}`);
  };
  const switchSort = (e) => {
    console.log(e);
  };
  const changePage = (page, pageSize) => {
    setPage(page);
    setPageSize(pageSize);
  };

  useEffect(() => {
    getAllArticles(page, pageSize, "createTime").then((res) => {
      const data = res.data.data;
      setTotal(data.total);
      const articleList = data.records.map((record) => {
        const CT = record.createTime;
        record.createTime = showDateTime(CT);

        return record;
      });

      setArticles(articleList);
    });
  }, [page, pageSize]);

  useEffect(() => {
    getSwiper()
      .then((res) => {
        setPictures(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <div className={indexStyle.HomePageContainer}>
      <div className={indexStyle.leftContainer}>
        <Swiper
          spaceBetween={50}
          className={indexStyle.swipper}
          modules={[Navigation, Autoplay, Pagination1]}
          pagination={{ clickable: true }}
          autoplay={{ delay: 5000 }}
        >
          {pictures.map((item)=>{
            return <SwiperSlide key={item.id}>
              <img style={{width:"100%"}} src={`${item.imageUrl}`} alt=""/>
            </SwiperSlide>
          })}
        </Swiper>
        <Tabs
          onTabClick={switchSort}
          className={indexStyle.tableHeader}
          items={items}
        />

        <div className={indexStyle.tableContainer}>
          {Articles.map((item, index) => {
            return (
              <div className={indexStyle.tableItem} key={index}>
                <Image
                  className={indexStyle.ImageStyle}
                  src={item.imageUrl}
                  fallback="data:image/png;base64,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"
                />
                <div className={indexStyle.tableItemMaincontent}>
                  <Row className={indexStyle.ContentHeader}>
                    <Col
                      onClick={() => {
                        openArticle(item.id);
                      }}
                      span={18}
                      className={indexStyle.articleTitle}
                    >
                      <div className={indexStyle.titleContainer}>
                        {item.title}
                      </div>
                    </Col>
                    <Col span={2} offset={1}>
                      <EyeOutlined className={indexStyle.logoAndFont} />
                      {item.articleViews}
                    </Col>
                    <Col span={2} offset={1}>
                      <MessageOutlined className={indexStyle.logoAndFont} />
                      {item.commentAmount}
                    </Col>
                  </Row>
                  <Row
                    onClick={() => {
                      openArticle(item.id);
                    }}
                    className={indexStyle.SummaryContainer}
                  >
                    {item.summary}
                  </Row>

                  <Row className={indexStyle.contentFooter}>
                    <Col span={5}>
                      <LikeFilled
                        onClick={(e) => {
                          let isLike = e.currentTarget.getAttribute("islike");

                          if (isLike === "false") {
                            e.currentTarget.classList.add(
                              `${indexStyle.highlightLike}`
                            );
                            e.currentTarget.setAttribute("islike", "true");
                            item.articleLikes++;
                            const newArticles = Articles.map(
                              (element) => element
                            );
                            setArticles(newArticles);
                          } else if (isLike === "true") {
                            e.currentTarget.classList.remove(
                              `${indexStyle.highlightLike}`
                            );
                            e.currentTarget.setAttribute("islike", "false");
                            item.articleLikes--;
                            const newArticles = Articles.map(
                              (element) => element
                            );
                            setArticles(newArticles);
                          }
                        }}
                        islike="false"
                        className={`${indexStyle.logoAndFont} ${indexStyle.item}`}
                      />
                      {item.articleLikes}
                    </Col>

                    <Col className={indexStyle.authorName} span={11} offset={1}>
                      作者：{item.authorName}
                    </Col>
                    <Col span={6} offset={1}>
                      <ClockCircleOutlined className={indexStyle.logoAndFont} />
                      {item.createTime}
                    </Col>
                  </Row>
                </div>
              </div>
            );
          })}
        </div>
        <Pagination
          current={page}
          pageSize={pageSize}
          total={total}
          showQuickJumper
          onChange={changePage}
          itemRender={itemRender}
          className={indexStyle.paginationStyle}
        />
      </div>
      <div className={indexStyle.rightContainer}>
        <PersonCard />
      </div>
    </div>
  );
}

export default HomePage;
