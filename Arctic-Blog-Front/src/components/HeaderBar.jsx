import React, { useEffect, useState } from "react";
import indexStyle from "../pages/index/index.module.scss";
import { Input, Col, Avatar, Button, Popover, Menu } from "antd";
import { NavLink, useNavigate } from "react-router-dom";
import {
  BookOutlined,
  EditOutlined,
  LogoutOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { getAvatarInfo } from "../api/login";
const { Search } = Input;

function HeaderBar() {
  const navigate = useNavigate();
  const [loginHide, setLoginHide] = useState(false);
  const [userinfo, setUserinfo] = useState(null);
  const addArticle = () => {
    navigate("/article/add");
  };
  const loginout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userinfo");
    setLoginHide(false);
  };
  const switchMenuItem = (e) => {
    const { key } = e;
    switch (key) {
      case "item-1":
        navigate("/person/center/profile")
        break;
      case "item-2":
        break;
      case "item-3":
        loginout();
        break;

      default:
        break;
    }
  };

  useEffect(() => {
    getAvatarInfo().then((res) => {
      if (res.data.code === 200) {
        setLoginHide(true);
        setUserinfo(res.data.data) 
        localStorage.setItem("user-basic-info",JSON.stringify(res.data.data))
      } 
    });
  }, []);

  //声明定义菜单元素
  const menuitems = [
    { label: "个人中心", key: "item-1", icon: <UserOutlined /> }, // 菜单项务必填写 key
    { label: "文章管理", key: "item-2", icon: <BookOutlined /> },
    { label: "退出登录", key: "item-3", icon: <LogoutOutlined /> },
  ];
  //下拉框title部分
  const title = (
    <div className="popoverheaderContainer">
      <div className="avatarContainer">
        <Avatar
          src={`/avatar/${userinfo ? userinfo.avatar : ""}`}
          size={60}
          shape="circle"
          icon={<UserOutlined />}
        />
      </div>
      {userinfo ? userinfo.username : null}
    </div>
  );

  const content = <Menu items={menuitems} onClick={switchMenuItem}></Menu>; //下拉框content部分

  return (
    <>
      <Col span={2}>
        <NavLink className={indexStyle.navlinknone} to="/">
          <strong>React-Blog</strong>
        </NavLink>
      </Col>

      <Col span={1} offset={13}>
        <NavLink className={"antd-a-style"} to={"/index/homepage"}>首页</NavLink>
      </Col>
      <Col span={1}>
        <NavLink className={"antd-a-style"} to={"/index/about"}>关于</NavLink>
      </Col>
      <Col span={3}>
        <Search placeholder="站内搜索" onSearch={Search} enterButton />
      </Col>
      {!loginHide && (
        <Col offset={1} span={1}>
          <NavLink className={"antd-a-style"} to={"/index/login"}>登录</NavLink>
        </Col>
      )}
      {!loginHide && (
        <Col span={2}>
          <NavLink className={"antd-a-style"} to={"/index/register"}>注册</NavLink>
        </Col>
      )}
      {loginHide && (
        <Popover
          overlayClassName="avatarPopover"
          title={title}
          content={content}
          placement="bottom"
        >
          <Col offset={1} span={1}>
            <Avatar
              src={`/avatar/${userinfo.avatar}`}
              size="large"
              shape="circle"
              icon={<UserOutlined />}
            />
          </Col>
        </Popover>
      )}
      {loginHide && (
        <Col span={2}>
          <Button
            onClick={addArticle}
            shape="round"
            danger="true"
            icon={<EditOutlined />}
          >
            写文章
          </Button>
        </Col>
      )}
    </>
  );
}

export default HeaderBar;
