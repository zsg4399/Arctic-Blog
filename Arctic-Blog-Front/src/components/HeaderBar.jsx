import React, { useEffect, useState } from "react";
import indexStyle from "../pages/index/index.module.scss";
import {
  Input,
  Col,
  Avatar,
  Button,
  Popover,
  Menu,
  message,
  Image,
  Spin,
} from "antd";
import { NavLink, useNavigate } from "react-router-dom";
import { loginout as userLoginOut } from "../api/user";
import {
  BookOutlined,
  EditOutlined,
  FileOutlined,
  LoadingOutlined,
  LogoutOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { getAvatarInfo } from "../api/login";
import { searchArticles } from "../api/article";
const { Search } = Input;

function HeaderBar() {
  const navigate = useNavigate();
  const [currentSelect, setCurrentSelect] = useState(-1);
  const [loginHide, setLoginHide] = useState(false);
  const [userinfo, setUserinfo] = useState(null);
  const [searchValue, setSearchValue] = useState("");
  const [searchContent, setSearchContent] = useState();
  const [searchOpen, setSearchOpen] = useState(false);
  const [searchLoading, setSearchLoading] = useState(false);
  const addArticle = () => {
    navigate("/article/add");
  };
  const loginout = () => {
    userLoginOut().then((res) => {
      console.log(res);
      if (!!res.data) {
        if (res.data.code === 200) {
          localStorage.removeItem("token");
          localStorage.removeItem("userinfo");
          setLoginHide(false);
          message.success("退出登录成功");
        }
      }
    });
  };
  const switchMenuItem = (e) => {
    const { key } = e;
    switch (key) {
      case "item-1":
        navigate("/person/center/profile");
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
  const onSearch = (value) => {
    setSearchLoading(true);
    searchArticles(value).then((res) => {
      console.log(res);
      setSearchContent(res.data.data.articles);
      setSearchLoading(false);
    });
  };
  useEffect(() => {
    if (searchLoading === false && !!searchContent) {
      setSearchOpen(true);
    }
  }, [searchLoading, searchContent]);
  useEffect(() => {
    getAvatarInfo()
      .then((res) => {
        if (res.data.code === 200) {
          setLoginHide(true);
          setUserinfo(res.data.data);
          localStorage.setItem(
            "user-basic-info",
            JSON.stringify(res.data.data)
          );
        }
      })
      .catch((err) => {
        message.warn(err);
      });
  }, []);

  useEffect(() => {
    console.log(currentSelect);
  }, [currentSelect]);
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
          src={`/static/avatar/${userinfo ? userinfo.avatar : ""}`}
          size={60}
          shape="circle"
          icon={<UserOutlined />}
        />
      </div>
      {userinfo ? userinfo.username : null}
    </div>
  );

  const content = <Menu items={menuitems} onClick={switchMenuItem}></Menu>; //下拉框content部分
  //xxl px>=1600 xl px >=1200 lg px>=992px md>=768px sm >=568px xs<568px
  return (
    <>
      <Col xl={3} lg={4} md={5} sm={6} xs={4}>
        <NavLink className={indexStyle.navlinknone} to="/">
          <strong>React-Blog</strong>
        </NavLink>
      </Col>

      <Col
        xl={{ offset: 11, span: 1 }}
        lg={{ offset: 8, span: 2 }}
        md={{ offset: 5, span: 2 }}
        sm={{ offset: 1, span: 2 }}
        xs={{ offset: 1, span: 3 }}
      >
        <NavLink className={"antd-a-style"} to={"/index/homepage"}>
          首页
        </NavLink>
      </Col>
      <Col xl={1} lg={2} md={2} sm={2} xs={3}>
        <NavLink className={"antd-a-style"} to={"/index/about"}>
          关于
        </NavLink>
      </Col>
      <Popover
        onOpenChange={(e) => {
          setSearchOpen(e);
        }}
        open={searchOpen}
        overlayClassName="searchSelect"
        title={
          <div style={{ textAlign: "center", fontSize: 18 }}>
            Search Articles
          </div>
        }
        content={
          <>
            <ul
              onKeyDown={(e) => {
                console.log(e);
              }}
              role="listbox"
              aria-labelledby="search-hit-label"
              className={indexStyle.searchTable}
            >
              {searchLoading ? (
                <Spin
                  className={indexStyle.searchLoading}
                  indicator={
                    <LoadingOutlined
                      style={{
                        fontSize: 48,
                      }}
                      spin
                    />
                  }
                />
              ) : !!searchContent ? (
                searchContent.map((item, index) => {
                  return (
                    <a
                      href={`https://arctic-zsg.fun/index/article/detail?articleId=${item.id}`}
                    >
                      <li
                        id={"search-hit-item-" + index}
                        role="option"
                        // eslint-disable-next-line eqeqeq
                        aria-selected={currentSelect == index}
                        onMouseEnter={(e) => {
                          const id = e.target.id.split("-")[3];
                          setCurrentSelect(id);
                        }}
                        className={indexStyle.recordStyle}
                        key={item.id}
                      >
                        <div className={indexStyle.recordIcon}>
                          <FileOutlined className={indexStyle.searchHitIcon} />
                        </div>
                        <div>
                          <div
                            className={indexStyle.recordTitle}
                            dangerouslySetInnerHTML={{
                              __html:
                                "<span class='word-hidden'><div class='recordTitle'># Title: </div>&nbsp" +
                                item.title +
                                "</span>",
                            }}
                          />
                          <div
                            className={indexStyle.recordSummary}
                            dangerouslySetInnerHTML={{
                              __html:
                                "<span class='word-hidden'><div class='recordTitle'># Summary: </div>&nbsp" +
                                item.summary +
                                "</span>",
                            }}
                          />
                          <div
                            className={indexStyle.recordContent}
                            dangerouslySetInnerHTML={{
                              __html:
                                "<span class='word-hidden'><div class='recordTitle'># Content: </div/>&nbsp" +
                                item.content +
                                "</span>",
                            }}
                          />
                        </div>
                      </li>
                    </a>
                  );
                })
              ) : (
                <div className={indexStyle.noRecentRecord}>
                  No recent searches
                </div>
              )}
            </ul>
            <div className={indexStyle.searchFooter}>
              <div className={indexStyle.searchLogo}>
                <a href="https://www.elasticsearch.org">
                  <span className={indexStyle.searchLabel}>Search by</span>
                  <Image
                    style={{
                      width: 30,
                      height: 30,
                      marginLeft: 5,
                      marginRight: 5,
                    }}
                    src="/static/static/elasticsearch.png"
                  />
                  <span className={indexStyle.esFont}>ElasticSearch</span>
                </a>
              </div>
              <ul className={indexStyle.searchCommands}>
                <li className={indexStyle.searchCommandLogo}>
                  <svg width="15" height="15" aria-label="Enter key" role="img">
                    <g
                      fill="none"
                      stroke="currentColor"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="1.2"
                    >
                      <path d="M12 3.53088v3c0 1-1 2-2 2H4M7 11.53088l-3-3 3-3"></path>
                    </g>
                  </svg>
                </li>
                <li className={indexStyle.searchLabel}>to select</li>
                <li className={indexStyle.searchCommandLogo}>
                  <svg
                    width="15"
                    height="15"
                    aria-label="Arrow down"
                    role="img"
                  >
                    <g
                      fill="none"
                      stroke="currentColor"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="1.2"
                    >
                      <path d="M7.5 3.5v8M10.5 8.5l-3 3-3-3"></path>
                    </g>
                  </svg>
                </li>
                <li className={indexStyle.searchCommandLogo}>
                  <svg width="15" height="15" aria-label="Arrow up" role="img">
                    <g
                      fill="none"
                      stroke="currentColor"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="1.2"
                    >
                      <path d="M7.5 11.5v-8M10.5 6.5l-3-3-3 3"></path>
                    </g>
                  </svg>
                </li>
                <li className={indexStyle.searchLabel}>to navigate</li>
                <li className={indexStyle.searchCommandLogo}>
                  <svg
                    width="15"
                    height="15"
                    aria-label="Escape key"
                    role="img"
                  >
                    <g
                      fill="none"
                      stroke="currentColor"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="1.2"
                    >
                      <path d="M13.6167 8.936c-.1065.3583-.6883.962-1.4875.962-.7993 0-1.653-.9165-1.653-2.1258v-.5678c0-1.2548.7896-2.1016 1.653-2.1016.8634 0 1.3601.4778 1.4875 1.0724M9 6c-.1352-.4735-.7506-.9219-1.46-.8972-.7092.0246-1.344.57-1.344 1.2166s.4198.8812 1.3445.9805C8.465 7.3992 8.968 7.9337 9 8.5c.032.5663-.454 1.398-1.4595 1.398C6.6593 9.898 6 9 5.963 8.4851m-1.4748.5368c-.2635.5941-.8099.876-1.5443.876s-1.7073-.6248-1.7073-2.204v-.4603c0-1.0416.721-2.131 1.7073-2.131.9864 0 1.6425 1.031 1.5443 2.2492h-2.956"></path>
                    </g>
                  </svg>
                </li>
                <li className={indexStyle.searchLabel}>to close</li>
              </ul>
            </div>
          </>
        }
        placement="bottom"
        trigger="click"
      >
        <Col xl={3} lg={3} md={5} sm={6} xs={6}>
          <Search
            placeholder="站内搜索"
            value={searchValue}
            onKeyDown={(e) => {
              switch (e.key) {
                case "Enter":
                  if (searchOpen === true && currentSelect !== -1) {
                  }
                  break;
                case "Escape":
                  if (searchOpen === true) {
                    setSearchOpen(false);
                  }
                  break;
                case "ArrowUp":
                  if (
                    searchOpen === true &&
                    !!searchContent &&
                    searchContent.length > 1
                  ) {
                    if (currentSelect > 0) {
                      setCurrentSelect(currentSelect - 1);
                    } else {
                      setCurrentSelect(searchContent.length - 1);
                    }
                  }
                  break;
                case "ArrowDown":
                  if (
                    searchOpen === true &&
                    !!searchContent &&
                    searchContent.length > 1
                  ) {
                    if (currentSelect < searchContent.length - 1) {
                      setCurrentSelect(currentSelect + 1);
                    } else {
                      setCurrentSelect(0);
                    }
                  }
                  break;
                default:
              }
            }}
            onChange={(e) => {
              if (e.target.value.length > 25) {
                message.warn("搜索信息长度不能超过25个字");
                return;
              }
              setSearchValue(e.target.value);
            }}
            onSearch={() => {
              onSearch(searchValue);
            }}
            enterButton
          />
        </Col>
      </Popover>
      {!loginHide && (
        <Col offset={1} xl={1} lg={2} md={2} sm={2} xs={3}>
          <NavLink className={"antd-a-style"} to={"/index/login"}>
            登录
          </NavLink>
        </Col>
      )}
      {!loginHide && (
        <Col xl={2} lg={2} md={2} sm={2} xs={3}>
          <NavLink className={"antd-a-style"} to={"/index/register"}>
            注册
          </NavLink>
        </Col>
      )}
      {loginHide && (
        <Popover
          overlayClassName="avatarPopover"
          title={title}
          content={content}
          placement="bottom"
        >
          <Col offset={1} xl={1} lg={1} md={1} sm={1} xs={2}>
            <Avatar
              src={`/static/avatar/${userinfo.avatar}`}
              size={{ xs: 24, sm: 30, md: 36, lg: 42, xl: 48 }}
              shape="circle"
              icon={<UserOutlined />}
            />
          </Col>
        </Popover>
      )}
      {loginHide && (
        <Col xl={3} lg={3} md={3} sm={4} xs={4}>
          <Button
            onClick={addArticle}
            shape="round"
            danger="true"
            className={indexStyle.EditorButton}
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
