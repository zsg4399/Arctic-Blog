import { Menu, Row } from "antd";
import React from "react";
import { Outlet } from "react-router-dom";
import HeadBar from "../../components/HeaderBar";
import centerStyle from "./PersonCenter.module.scss";

const menuitems = [
  { label: "个人中心", key: "item-1" }, // 菜单项务必填写 key
  { label: "账号安全", key: "item-2" },
  { label: "我的收藏", key: "item-3" },
  { label: "浏览历史", key: "item-4" },
];

const PersonCenter = () => {
  
  return (
    <div className={centerStyle.centerContainer}>
      <Row className={centerStyle.headContainer}>
        <HeadBar />
      </Row>
      <Row className={centerStyle.mainContainer}>
        <div className={centerStyle.mainContent}>
          <Menu
            items={menuitems}
            theme="light"
            className={centerStyle.leftMenuContainer}
            defaultSelectedKeys="item-1"
          ></Menu>
          <div className={centerStyle.contentContainer}>
            <Outlet />
          </div>
        </div>
      </Row>
      <Row className={centerStyle.footContainer}>
        <p>Copyright © 2022 北极风. Powered by React.</p>
      </Row>
    </div>
  );
};

export default PersonCenter;
