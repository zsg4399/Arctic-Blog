import React from "react";
import indexStyle from "../pages/index/index.module.scss";
import { Input, Form, Button } from "antd";
import { NavLink } from "react-router-dom";
import { userRegister, createVerifyCode } from "../api/login";
function Register() {
  const [form] = Form.useForm();
  const UserRegister = (values) => {
    const { username, password, email, verifyCode } = values;
    userRegister(username, password, email, verifyCode).then((res) => {
      console.log(res);
    });
  };
  const getVerifyCode = () => {
    const username = form.getFieldValue("username");
    const password = form.getFieldValue("password1");
    const email = form.getFieldValue("email");
    createVerifyCode(username, email, password);
  };
  return (
    <div className={indexStyle.registerCard}>
      <div className={indexStyle.cardHeader}>
        <p className={indexStyle.headerfont}>注册</p>
        <div className={indexStyle.borderStyle}/>
      </div>
      <div className={indexStyle.cardMain}>
        <Form
          name="basic"
          labelCol={{
            span: 6,
          }}
          wrapperCol={{
            span: 16,
          }}
          initialValues={{
            remember: true,
          }}
          autoComplete="off"
          onFinish={UserRegister}
          form={form}
          size="large"
        >
          <Form.Item
            label="E-mail邮箱"
            name="email"
            rules={[
              {
                required: true,
                message: "请输入邮箱地址完成账号注册!",
              },
              {
                max: 32,
                message: "邮箱地址最大不可超过32位",
              },
              {
                pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
                message: "邮箱格式错误，请检查邮箱格式",
              },
            ]}
          >
            <Input placeholder="请输入邮箱地址" />
          </Form.Item>
          <Form.Item
            label="用户名"
            name="username"
            rules={[
              {
                required: true,
                message: "请输入用户名!",
              },
              {
                pattern: /^[\u4e00-\u9fa5a-zA-Z]{1,12}$/,
                message: "用户名必须为1-12位字符，不含数字和特殊字符",
              },
            ]}
          >
            <Input placeholder="请输入用户名" />
          </Form.Item>

          <Form.Item
            label="密码"
            name="password1"
            rules={[
              {
                required: true,
                message: "请输入密码!",
              },
              {
                pattern: /^[A-Z][a-zA-Z0-9]{7,14}$/,
                message:
                  "密码长度必须为8-15位，仅包含大小写字母和数字且必须以大写字母开头",
              },
            ]}
          >
            <Input.Password placeholder="请输入密码" />
          </Form.Item>
          <Form.Item
            label="确认密码"
            name="password2"
            rules={[
              {
                required: true,
                message: "请再次输入密码!",
              },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue("password1") === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(new Error("两次输入的密码不符"));
                },
              }),
            ]}
          >
            <Input.Password placeholder="请再次输入密码确认无误" />
          </Form.Item>
          <Form.Item
            wrapperCol={{
              span: 24,
            }}
          >
            <Form.Item
              name="verifyCode"
              label="验证码"
              wrapperCol={{
                span: 14,
              }}
              labelCol={{
                span: 10,
              }}
              rules={[
                {
                  required: true,
                  message: "必须输入验证码",
                },
              ]}
              style={{ display: "inline-block" }}
            >
              <Input
                className={indexStyle.verifyInput}
                placeholder="请输入验证码"
              ></Input>
            </Form.Item>
            <Button
              type="primary"
              onClick={getVerifyCode}
              style={{ width: 100, marginLeft: 50, textAlign: "center" }}
            >
              获取验证码
            </Button>
          </Form.Item>

          <Form.Item
            wrapperCol={{
              offset: 2,
            }}
          >
            <NavLink to={"/index/login"}>已有账号登录</NavLink>
            <Button
              className={indexStyle.submitButton}
              htmlType="submit"
              type="primary"
            >
              注册
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
}

export default Register;
