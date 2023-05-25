import { PlusOutlined } from "@ant-design/icons";
import { Col, Input, Popover, Row, Tag, Tooltip } from "antd";
import React, { useEffect, useRef, useState } from "react";
import tagscss from "./category.module.scss";
import { getAllCategoryByUserId } from "../../api/category";
import { useDispatch, useSelector } from "react-redux";
import {
  setCategorys,
  addCategory,
  removeCategory,
} from "../../features/category/categorySlice";

const CategoryComponent = () => {
  const categorys = useSelector((state) => state.categorys.value);
  const dispatch = useDispatch();
  const [inputValue, setInputValue] = useState("");
  const [editInputIndex, setEditInputIndex] = useState(-1);
  const [editInputValue, setEditInputValue] = useState("");
  const [myCategorys, setMyCategorys] = useState([]);
  const inputRef = useRef(null);
  const editInputRef = useRef(null);

  useEffect(() => {
    editInputRef.current?.focus();
  }, [inputValue]);

  const getCategorys = () => {
    const userinfo = JSON.parse(localStorage.getItem("userinfo"));
    getAllCategoryByUserId(userinfo.id)
      .then((res) => {
        setMyCategorys(res.data.data.categorys);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  //移除标签
  const handleClose = (removedCategory) => {
    dispatch(removeCategory(removedCategory))
  };

  //添加标签输入框内容变化时候的回调
  const handleInputChange = (e) => {
    setInputValue(e.target.value);
  };
  //新增标签框输入回车
  const handleInputConfirm = () => {
    if (inputValue && categorys.indexOf(inputValue) === -1) {
      dispatch(addCategory(inputValue));
    }
    setInputValue("");
  };
  //输入框内容变化时的回调
  const handleEditInputChange = (e) => {
    setEditInputValue(e.target.value);
  };
  //按下回车的回调
  const handleEditInputConfirm = (e) => {
    let flag = true;
    categorys.forEach((category) => {
      if (category.categoryName === e.target.value) flag = false;
    });
    if (!flag) {
      dispatch(removeCategory(categorys[editInputIndex].categoryName));
      setEditInputIndex(-1);
      setInputValue("");
      return
    }
    const newCategorys = [...categorys];
    newCategorys[editInputIndex] = {categoryName:editInputValue};
    dispatch(setCategorys(newCategorys));
    setEditInputIndex(-1);
    setInputValue("");
  };
  const tile = (
    <div style={{ textAlign: "center", fontWeight: 700 }}>分类专栏</div>
  );
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
        placeholder="请选择一个已有分类，或者自定义输入一个分类后按下enter键"
      />
      <Row className={tagscss.myCategoryContainer}>
        {myCategorys.map((mycategory, index) => {
          return (
            <Col key={index}>
              <Tag
                color="orange"
                onClick={() => dispatch(addCategory(mycategory.categoryName))}
              >
                {mycategory.categoryName}
              </Tag>
            </Col>
          );
        })}
      </Row>
    </>
  );

  return (
    <>
      {categorys.map((category, index) => {
        //if渲染的是双击后正在编辑的标签，展示出输入框
        if (editInputIndex === index) {
          return (
            <Input
              ref={editInputRef}
              key={category.categoryName}
              size="small"
              className={tagscss.changeinputContainer}
              value={editInputValue}
              onChange={handleEditInputChange}
              onBlur={handleEditInputConfirm}
              onPressEnter={handleEditInputConfirm}
            />
          );
        }
        const isLongTag = category.categoryName.length > 10; //长标签
        const categoryElem = (
          <Tag
            className={tagscss.tagContainer}
            key={category.categoryName}
            closable
            color="orange"
            onClose={() => handleClose(category.categoryName)}
          >
            <span
              onDoubleClick={(e) => {
                setEditInputIndex(index);
                setEditInputValue(category.categoryName);
                e.preventDefault();
              }}
            >
              {isLongTag
                ? `${category.categoryName.slice(0, 10)}...`
                : category.categoryName}
            </span>
          </Tag>
        );
        return isLongTag ? (
          <Tooltip title={category.categoryName} key={category.categoryName}>
            {categoryElem}
          </Tooltip>
        ) : (
          categoryElem
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
          <Tag
            onClick={getCategorys}
            className={tagscss.tagContainer}
            color="orange"
          >
            <PlusOutlined /> 添加文章分类
          </Tag>
        </Popover>
      }
    </>
  );
};
export default CategoryComponent;
