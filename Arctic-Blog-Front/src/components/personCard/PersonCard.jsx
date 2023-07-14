import React, { useEffect, useState } from "react";
import cardStyle from "./PersonCard.module.scss";
import { Avatar, message } from "antd";
import { GithubOutlined } from "@ant-design/icons";
import {getAuthorInfo} from '../../api/user'

const PersonCard = () => {
  const [authorInfo,setAuthorInfo]=useState({articles:0,tags:0,categorys:0,talks:0})
  useEffect(()=>{
    getAuthorInfo().then(res=>{
      setAuthorInfo(res.data.data)
    }).catch(err=>{
      return
    })
  },[])
  return (
    <div className={cardStyle.personCard}>
      <div className={cardStyle.avatarContainer}>
        <Avatar
          size={{
            xs: 32,
            sm: 56,
            md: 72,
            lg: 96,
            xl: 120,
            xxl: 140,
          }}
          src="/static/avatar/profilephoto.jpg"
          alt="北极风"
          draggable={true}
        />
      </div>

      <h3 className={cardStyle.Title}>北极风</h3>
      <span className="my-border" />
      <h5 className={cardStyle.descritpion}>码农一个罢了</h5>
      <a href="https://github.com/zsg4399">
        <GithubOutlined className={`${cardStyle.icon} canClick`} />
      </a>
      <ul className={cardStyle.bottomGrid}>
        <li className={cardStyle.gridCol}>
          <span className={cardStyle.colTag}>Talks</span>
          <span className={cardStyle.colAmount}>{authorInfo.talks}</span>
        </li>
        <li className={cardStyle.gridCol}>
          <span className={cardStyle.colTag}>Articles</span>
          <span className={cardStyle.colAmount}>{authorInfo.articles}</span>
        </li>
        <li className={cardStyle.gridCol}>
          <span className={cardStyle.colTag}>Categorys</span>
          <span className={cardStyle.colAmount}>{authorInfo.categorys}</span>
        </li>
        <li className={cardStyle.gridCol}>
          <span className={cardStyle.colTag}>Tags</span>
          <span className={cardStyle.colAmount}>{authorInfo.tags}</span>
        </li>
      </ul>
    </div>
  );
};

export default PersonCard;
