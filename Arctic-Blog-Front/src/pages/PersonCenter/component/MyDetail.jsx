import {
  Avatar,
  Button,
  Cascader,
  Col,
  DatePicker,
  Input,
  Radio,
  Row,
} from "antd";
import React, { useEffect, useState } from "react";
import detailStyle from "../PersonCenter.module.scss";
import { editUserinfo, getUserInfo } from "../../../api/user";
import { EditOutlined, UserOutlined } from "@ant-design/icons";
import cascaderOptions, { DivisionUtil } from "@pansy/china-division";
import moment from "moment";
import options from "../../../utils/areaOptions";
const divisionUtil = new DivisionUtil(cascaderOptions);
const MyDetail = () => {
  const [userinfo, setUserinfo] = useState({
    id: "121",
    username: `用户${parseInt(Math.random() * 10000000)}`,
    birthday: null,
    description: null,
    address: null,
    sex: "男",
    avatar: "",
  });
  const [username, setUsername] = useState(userinfo.username);
  const [birthday, setBirthday] = useState(
    userinfo.birthday ? moment(userinfo.birthday) : null
  );
  const [sex, setSex] = useState(userinfo.sex);
  const [description, setDescription] = useState(userinfo.description);
  const [address, setAddress] = useState(userinfo.address);

  const [editor1, setEditor1] = useState(true);
  const [confirm1, setConfirm1] = useState(false);
  const [editor2, setEditor2] = useState(true);
  const [editor3, setEditor3] = useState(true);
  const [confirm3, setConfirm3] = useState(false);
  const [editor4, setEditor4] = useState(true);
  const [confirm4, setConfirm4] = useState(false);
  const [editor5, setEditor5] = useState(true);
  const [confirm5, setConfirm5] = useState(false);

  const switchEditor = (key) => {
    switch (key) {
      case "username":
        setEditor1(false);
        break;
      case "sex":
        setEditor2(false);
        break;
      case "address":
        setEditor5(false);
        break;
      case "birthday":
        setEditor3(false);
        break;
      case "description":
        setEditor4(false);
        break;

      default:
    }
  };
  useEffect(() => {
    getUserInfo().then((res) => {
      setUserinfo(res.data.data)   
    });
  }, []);

  useEffect(() => {setUsername(userinfo.username);}, [userinfo.username]);
  useEffect(()=>{
    console.log(userinfo.address);
    setAddress(userinfo.address);},[userinfo.address])
  useEffect(()=>{setBirthday(userinfo.birthday ? moment(userinfo.birthday) : null);},[userinfo.birthday])
  useEffect(()=>{setDescription(userinfo.description);},[userinfo.description])
  useEffect(()=>{setSex(userinfo.sex);},[userinfo.sex])
  return (
    <div className={detailStyle.detailContainer}>
      <Row className={detailStyle.TopContainer}>
        <Col offset={2} span={2}>
          <Avatar
            src={`/avatar/${userinfo ? userinfo.avatar : ""}`}
            size={60}
            shape="circle"
            icon={<UserOutlined />}
          />
        </Col>
        <Col offset={0} span={20} className={detailStyle.personName}>
          {userinfo.username}
        </Col>
      </Row>

      <div className={detailStyle.basicInfo}>
        <Row className={detailStyle.basicInfohead}>基本信息</Row>
        <div className={detailStyle.basicInfoContent}>
          <Row className={detailStyle.basicInfoContentLine}>
            <Col className={detailStyle.basicInfoContentL}>用户ID:</Col>
            <Col className={detailStyle.basicInfoContentR}>
              <Col className={detailStyle.basicInfoContentRL}>
                {userinfo.id}
              </Col>
            </Col>
          </Row>
          <Row className={detailStyle.basicInfoContentLine}>
            <Col className={detailStyle.basicInfoContentL}>用户昵称:</Col>
            <Col className={detailStyle.basicInfoContentR}>
              {editor1 && (
                <Col className={detailStyle.basicInfoContentRL}>{username}</Col>
              )}
              {editor1 && (
                <Col
                  className={detailStyle.editorIcon}
                  onClick={() => {
                    switchEditor("username");
                  }}
                >
                  <EditOutlined />
                  <div>编辑</div>
                </Col>
              )}
              {!editor1 && (
                <Input
                  className={detailStyle.usernameInput}
                  onChange={(e) => {
                    setUsername(e.target.value);
                  }}
                  onFocus={() => {
                    setConfirm1(true);
                  }}
                  value={username}
                  placeholder="请输入用户昵称"
                />
              )}
              {confirm1 && (
                <Button
                  className={detailStyle.antConfirm}
                  type="primary"
                  danger
                  onClick={() => {
                    editUserinfo("username", username);
                    setConfirm1(false);
                    setEditor1(true);
                  }}
                >
                  提交
                </Button>
              )}
              {confirm1 && (
                <Button
                  className={detailStyle.antCancel}
                  type="default"
                  danger
                  onClick={() => {
                    setConfirm1(false);
                    setEditor1(true);
                    setUsername(userinfo.username);
                  }}
                >
                  取消
                </Button>
              )}
            </Col>
          </Row>
          <Row className={detailStyle.basicInfoContentLine}>
            <Col className={detailStyle.basicInfoContentL}>性别:</Col>
            <Col className={detailStyle.basicInfoContentR}>
              {editor2 && (
                <Col className={detailStyle.basicInfoContentRL}>{sex}</Col>
              )}
              {editor2 && (
                <Col
                  className={detailStyle.editorIcon}
                  onClick={() => {
                    switchEditor("sex");
                  }}
                >
                  <EditOutlined />
                  <div>编辑</div>
                </Col>
              )}
              {!editor2 && (
                <Radio.Group
                  value={sex}
                  onChange={(e) => {
                    setSex(e.target.value);
                  }}
                >
                  <Radio value="男">男</Radio>
                  <Radio value="女">女</Radio>
                </Radio.Group>
              )}
              {!editor2 && (
                <Button
                  className={detailStyle.antConfirm}
                  type="primary"
                  danger
                  onClick={() => {
                    editUserinfo("sex", sex);
                    setEditor2(true);
                  }}
                >
                  提交
                </Button>
              )}
              {!editor2 && (
                <Button
                  className={detailStyle.antCancel}
                  type="default"
                  danger
                  onClick={() => {
                    setEditor2(true);
                    setSex(userinfo.sex);
                  }}
                >
                  取消
                </Button>
              )}
            </Col>
          </Row>
          <Row className={detailStyle.basicInfoContentLine}>
            <Col className={detailStyle.basicInfoContentL}>出生日期:</Col>
            <Col className={detailStyle.basicInfoContentR}>
              {editor3 && (
                <Col className={detailStyle.basicInfoContentRL}>
                  {birthday === null ? "未选择" : birthday.format("YYYY-MM-DD")}
                </Col>
              )}
              {editor3 && (
                <Col
                  className={detailStyle.editorIcon}
                  onClick={() => {
                    switchEditor("birthday");
                  }}
                >
                  <EditOutlined />
                  <div>编辑</div>
                </Col>
              )}
              {!editor3 && (
                <DatePicker
                  className={detailStyle.birthdayInput}
                  onFocus={() => {
                    setConfirm3(true);
                  }}
                  format="YYYY-MM-DD"
                  onChange={(date, datestring) => {
                    setBirthday(date);
                  }}
                  placeholder="请选择的你的出生日期"
                  value={birthday}
                />
              )}

              {confirm3 && (
                <Button
                  className={detailStyle.antConfirm}
                  type="primary"
                  danger
                  onClick={() => {
                    editUserinfo("birthday", birthday.format("YYYY-MM-DD"));
                    setConfirm3(false);
                    setEditor3(true);
                  }}
                >
                  提交
                </Button>
              )}
              {confirm3 && (
                <Button
                  className={detailStyle.antCancel}
                  type="default"
                  danger
                  onClick={() => {
                    setConfirm3(false);
                    setEditor3(true);
                    setBirthday(
                      userinfo.birthday ? moment(userinfo.birthday) : null
                    );
                  }}
                >
                  取消
                </Button>
              )}
            </Col>
          </Row>
          <Row className={detailStyle.basicInfoContentLine}>
            <Col className={detailStyle.basicInfoContentL}>个人简介:</Col>
            <Col className={detailStyle.basicInfoContentR}>
              {editor4 && (
                <Col className={detailStyle.basicInfoContentRL}>
                  {description === null || description === ""
                    ? "未填写"
                    : description}
                </Col>
              )}
              {editor4 && (
                <Col
                  className={detailStyle.editorIcon}
                  onClick={() => {
                    switchEditor("description");
                  }}
                >
                  <EditOutlined />
                  <div>编辑</div>
                </Col>
              )}
              {!editor4 && (
                <Input.TextArea
                  className={detailStyle.descritptionTextArea}
                  onChange={(e) => {
                    setDescription(e.target.value);
                  }}
                  onFocus={() => {
                    setConfirm4(true);
                  }}
                  value={description}
                  placeholder={"这个人很懒，暂时没有简介"}
                />
              )}
              {confirm4 && (
                <Button
                  className={detailStyle.antConfirm}
                  type="primary"
                  danger
                  onClick={() => {
                    editUserinfo("description", description);
                    setConfirm4(false);
                    setEditor4(true);
                  }}
                >
                  提交
                </Button>
              )}
              {confirm4 && (
                <Button
                  className={detailStyle.antCancel}
                  type="default"
                  danger
                  onClick={() => {
                    setConfirm4(false);
                    setEditor4(true);
                    setDescription(userinfo.description);
                  }}
                >
                  取消
                </Button>
              )}
            </Col>
          </Row>
          <Row className={detailStyle.basicInfoContentLine}>
            <Col className={detailStyle.basicInfoContentL}>所在地区:</Col>
            <Col className={detailStyle.basicInfoContentR}>
              {editor5 && (
                <>
                  <Col className={detailStyle.basicInfoContentRL}>
                    {address === null
                      ? "未选择"
                      : divisionUtil.getNameByCode(address[0]) +
                        "/" +
                        divisionUtil.getNameByCode(address[1]) +
                        "/" +
                        divisionUtil.getNameByCode(address[2])}
                  </Col>
                  <Col
                    className={detailStyle.editorIcon}
                    onClick={() => {
                      switchEditor("address");
                    }}
                  >
                    <EditOutlined />
                    <div>编辑</div>
                  </Col>
                </>
              )}
              {!editor5 && (
                <Cascader
                  options={options}
                  className={detailStyle.areaSelect}
                  value={address}
                  placeholder="请选择所在地区"
                  onFocus={() => {
                    setConfirm5(true);
                  }}
                  showSearch
                  onChange={(e) => {
                    if (e === null || typeof e === "undefined") {
                      return;
                    }
                    setAddress([...e]);
                  }}
                />
              )}

              {confirm5 && (
                <Button
                  className={detailStyle.antConfirm}
                  type="primary"
                  danger
                  onClick={() => {
                    editUserinfo("address", address);
                    setConfirm5(false);
                    setEditor5(true);
                  }}
                >
                  提交
                </Button>
              )}
              {confirm5 && (
                <Button
                  className={detailStyle.antCancel}
                  type="default"
                  danger
                  onClick={() => {
                    setConfirm5(false);
                    setEditor5(true);
                    setAddress(userinfo.address);
                  }}
                >
                  取消
                </Button>
              )}
            </Col>
          </Row>
        </div>
      </div>
    </div>
  );
};

export default MyDetail;
