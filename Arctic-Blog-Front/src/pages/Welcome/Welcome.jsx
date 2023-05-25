import React, { useEffect } from "react";
import welSass from "./welcome.module.scss";
import profilephoto from "../../static/profilephoto.jpg";
import { useNavigate } from "react-router-dom";

function Welcome() {
  const navigate = useNavigate();
  useEffect(() => {
    document.title = "欢迎来到北极风的个人博客";
  });
  const routerJump = (key) => {
    switch (key) {
      case 1:
        navigate("/index/homepage");
        break;
      case 4:
        navigate("/index/about");
        break;
      default:
    }
  };
  return (
    <div className={welSass.welContainer}>
      <div className={welSass.Container}>
        <div className={welSass.profilephotoContainer}>
          <img
            alt="if error happen you will see it"
            className={welSass.profilephoto}
            src={profilephoto}
          ></img>
        </div>
        <h1 className={welSass.h1Style}>北极风</h1>
        <h3 className={welSass.h3Style}>个人博客</h3>
        <span className="my-border"/>
        <h2 className={welSass.h2Style}>欢迎来到我的个人博客</h2>
        <div className={welSass.selectContainer}>
          <div
            className={welSass.selectTitle}
            onClick={() => {
              routerJump(1);
            }}
          >
            首页
          </div>
          <div className={welSass.selectTitle}>技术选型介绍</div>
          <div className={welSass.selectTitle}>导航</div>
          <div
            className={welSass.selectTitle}
            onClick={() => {
              routerJump(4);
            }}
          >
            关于我
          </div>
        </div>
      </div>
    </div>
  );
}

export default Welcome;
