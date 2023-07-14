import React, { useEffect, useState } from "react";
import addStyle from "./addArticle.module.scss";
import { Navigate, useNavigate } from "react-router-dom";
import { BackTop, Button, Col, Input, Row, message } from "antd";
import BraftComponent from "../../components/braftEditor";
import BraftEditor from "braft-editor";
import { VerticalAlignTopOutlined } from "@ant-design/icons";
import TagComponent from "../../components/tag/TagComponet";
import UploadComponet from "../../components/upload/UpLoadComponent";
import CategoryComponent from "../../components/category/CategoryComponet";
import { SendArticle } from "../../api/article";
import { useSelector } from "react-redux";
import { getAvatarInfo } from "../../api/login";
import HeaderBar from "../../components/HeaderBar";
import "braft-editor/dist/output.css";
const { TextArea } = Input;

function AddArticle() {
  const navigate = useNavigate();
  const categorys = useSelector((state) => state.categorys.value);
  const tags = useSelector((state) => state.tags.value);
  const [userinfo, setUserinfo] = useState(null);
  const [title, setTitle] = useState("");
  const [summary, setSummary] = useState("");
  const [editorstate, setEditorState] = useState(
    BraftEditor.createEditorState("")
  );
  const [image, setImage] = useState();
  const titlechange = (e) => {
    setTitle(e.target.value);
  };

  const summarychange = (e) => {
    setSummary(e.target.value);
  };
  useEffect(() => {
    getAvatarInfo().then((res) => {
      if (res.data.code === 200) {
        setUserinfo(res.data.data);
      }
    });
  }, []);
  const addArticle = () => {
    const length = editorstate.toText().length;
    if (length < 200) {
      message.warn("文章内容长度应不小于200个字");
      return;
    }
    if (length > 5000) {
      message.warn("文章内容长度不能超过5000字");
      return;
    }
    if (title.length < 5) {
      message.warn("标题长度不能小于5个字");
      return;
    }
    if (summary.length < 5) {
      message.warn("摘要长度不能小于5个字");
      return;
    }
    if(tags.length===0){
      message.warn("标签是必选项，请选择至少一个标签")
      return;
    }
    SendArticle(
      title,
      summary,
      editorstate.toRAW(),
      categorys,
      tags,
      image
    )
      .then((res) => {
        console.log(res);
        if (res.data.code === 200) {
          navigate("/index/homepage");
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  if(!!localStorage.getItem("token")){
    return (
      <div className={addStyle.indexContainer}>
        <Row className={addStyle.headContainer}>
          <HeaderBar />
        </Row>
        <Row className={addStyle.mainContainer}>
          <div className={addStyle.braftContainer}>
            <Input
              className="article-title"
              onChange={titlechange}
              value={title}
              size="large"
              maxLength={80}
              minLength={5}
              placeholder="请输入文章标题(5~80个字)"
              showCount
            />
            <BraftComponent editorstate={setEditorState} />
          </div>
          <div className={addStyle.articleSettingContainer}>
            <Row className={addStyle.SettingSelect}>
              <Col className="neccessary" span={2}>文章标签</Col>
              <Col span={21} offset={1}>
                <TagComponent />
              </Col>
            </Row>
            <Row className={addStyle.SettingSelect}>
              <Col span={2}>添加封面</Col>
              <Col offset={1} span={21}>
                <UploadComponet setimage={setImage} />
              </Col>
            </Row>
            <Row className={addStyle.SettingSelect}>
              <Col className="neccessary" span={2}>文章摘要</Col>
              <Col span={21} offset={1}>
                <TextArea
                  allowClear
                  value={summary}
                  onChange={summarychange}
                  showCount
                  maxLength={100}
                  minLength={5}
                  rows={4}
                  placeholder="请输入文章摘要，以帮助读者更好的阅读文章(5-100个字)"
                ></TextArea>
              </Col>
            </Row>
            <Row className={addStyle.SettingSelect}>
              <Col span={2}>文章分类</Col>
              <Col span={21} offset={1}>
                <CategoryComponent />
              </Col>
            </Row>
          </div>
        </Row>
        <Row className={addStyle.footContainer}>
          <Col style={{ fontSize: 18 }} span={3} offset={4}>
            内容字数:{editorstate.toText().length}
          </Col>
          <Col span={2} offset={6}>
            <Button onClick={addArticle} danger style={{ borderRadius: 10 }}>
              发布文章
            </Button>
          </Col>
          <Col span={3} offset={6}>
            <BackTop visibilityHeight={300} style={{ width: 80, bottom: 15 }}>
              <Button
                shape="round"
                type="primary"
                icon={<VerticalAlignTopOutlined />}
              >
                返回顶部
              </Button>
            </BackTop>
          </Col>
        </Row>
      </div>
    );
  }
  message.info("请先登录")
  return <Navigate to="/index/login"/>
}

export default AddArticle;
