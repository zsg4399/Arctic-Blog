import { Row } from "antd";
import React, { useEffect } from "react";
import { Outlet } from "react-router-dom";
import HeaderBar from "../../components/HeaderBar";
import indexStyle from "./index.module.scss";

function Index() {
  useEffect(() => {
    document.title = "博客首页";
  });
  return (
    <div className={indexStyle.indexContainer}>
      <Row className={indexStyle.headContainer}>
        <HeaderBar />
      </Row>
      <Row className={indexStyle.mainContainer}>
        <div className={indexStyle.mainContent}>
          <Outlet />
        </div>
      </Row>
      <Row className={indexStyle.footContainer}>
        <p>Copyright © 2022 北极风. Powered by React.</p>
      </Row>
    </div>
  );
}

export default Index;
