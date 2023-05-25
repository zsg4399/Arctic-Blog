import { PlusOutlined } from "@ant-design/icons";
import { Button, Col, Input, Menu, Popover, Row, Tag, Tooltip } from "antd";
import React, { useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import tagscss from "./Tag.module.scss";
import { setTags, addTag, removeTag } from "../../features/tag/tagSlice";
const Java = [
  "java",
  "tomcat",
  "spring",
  "maven",
  "springboot",
  "springmvc",
  "springcloud",
  "junit",
  "zookeeper",
  "hadoop",
  "idea",
  "jvm",
];
const TagComponent = () => {
  const tags = useSelector((state) => state.tags.value);
  const dispatch = useDispatch();
  const [inputValue, setInputValue] = useState("");
  const [editInputIndex, setEditInputIndex] = useState(-1);
  const [editInputValue, setEditInputValue] = useState("");
  const inputRef = useRef(null);
  const editInputRef = useRef(null);
  const [selectTags] = useState(Java);
  useEffect(() => {}, []);

  useEffect(() => {
    editInputRef.current?.focus();
  }, [inputValue]);
  //移除标签
  const handleClose = (removedtag) => {
    dispatch(removeTag(removedtag.tagName));
  };

  //添加标签输入框内容变化时候的回调
  const handleInputChange = (e) => {
    setInputValue(e.target.value);
  };
  //新增标签框输入回车
  const handleInputConfirm = () => {
    if (inputValue && tags.indexOf(inputValue) === -1) {
      dispatch(addTag(inputValue));
    }
    setInputValue("");
  };
  //编辑框内容变化时的回调
  const handleEditInputChange = (e) => {
    setEditInputValue(e.target.value);
  };
  //按下回车的回调
  const handleEditInputConfirm = (e) => {
    let flag = true;
    console.log(e.target.value);
    tags.forEach((tag) => {
      if (tag.tagName === e.target.value) flag = false;
    });
    if (!flag) {
      dispatch(removeTag(tags[editInputIndex].tagName));
      setEditInputIndex(-1);
      setInputValue("");
      return;
    }
    const newTags = [...tags];
    console.log(newTags);
    newTags[editInputIndex] = { tagName: editInputValue };
    dispatch(setTags(newTags));
    setEditInputIndex(-1);
    setInputValue("");
  };
  const tagmenuitems = [{ label: "java", key: "tagitem-1" }];
  const tile = <div style={{ textAlign: "center", fontWeight: 700 }}>标签</div>;
  const content = (
    <>
      <Input
        ref={inputRef}
        type="text"
        size="large"
        className={tagscss.inputContainer}
        value={inputValue}
        onChange={handleInputChange}
        onBlur={handleInputConfirm}
        onPressEnter={handleInputConfirm}
        placeholder="请选择一个标签，或者自定义输入一个标签后按下Enter键"
      />
      <Row className={tagscss.tagSelectorContainer}>
        <Col span={4} className={tagscss.leftmenuContainer}>
          <Menu items={tagmenuitems} />
        </Col>
        <Col span={19} offset={1} className={tagscss.rightTagsContainer}>
          {selectTags.map((selectTag, index) => {
            return (
              <Button
                onClick={() => dispatch(addTag(selectTag))}
                className={tagscss.selectTag}
                key={index}
              >
                {selectTag}
              </Button>
            );
          })}
        </Col>
      </Row>
    </>
  );

  return (
    <>
      {tags.map((tag, index) => {
        //if渲染的是双击后正在编辑的标签，展示出输入框
        if (editInputIndex === index) {
          return (
            <Input
              ref={editInputRef}
              key={tag.tagName}
              size="small"
              className={tagscss.changeinputContainer}
              value={editInputValue}
              onChange={handleEditInputChange}
              onBlur={handleEditInputConfirm}
              onPressEnter={handleEditInputConfirm}
            />
          );
        }
        const isLongTag = tag.tagName.length > 10; //长标签
        const tagElem = (
          <Tag
            className={tagscss.tagContainer}
            key={tag.tagName}
            closable
            color="processing"
            onClose={() => handleClose(tag)}
          >
            <span
              onDoubleClick={(e) => {
                setEditInputIndex(index);
                setEditInputValue(tag.tagName);
                e.preventDefault();
              }}
            >
              {isLongTag ? `${tag.tagName.slice(0, 10)}...` : tag.tagName}
            </span>
          </Tag>
        );
        return isLongTag ? (
          <Tooltip title={tag.tagName} key={tag.tagName}>
            {tagElem}
          </Tooltip>
        ) : (
          tagElem
        );
      })}
      {
        <Popover
          overlayClassName="TagPopOver"
          title={tile}
          content={content}
          placement="bottom"
          trigger="click"
        >
          <Tag className={tagscss.tagContainer} color="processing">
            <PlusOutlined /> 添加文章标签
          </Tag>
        </Popover>
      }
    </>
  );
};
export default TagComponent;
