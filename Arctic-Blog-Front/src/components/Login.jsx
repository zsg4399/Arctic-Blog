import React from "react";
import indexStyle from "../pages/index/index.module.scss";
import { Input, Form, Button, message } from "antd";
import { userLogin } from "../api/login";
import { Navigate, useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();
  const Login = (values) => {
    const { username, password } = values;
    userLogin(username, password).then((res) => {
      localStorage.setItem("token", res.data.data.access_token);
      localStorage.setItem("userinfo", JSON.stringify(res.data.data.userinfo));
      navigate("/index/homepage");
      window.location.reload();
    });
  };
  if (!localStorage.getItem("token")) {
    return (
      <div className={indexStyle.loginCard}>
        <div className={indexStyle.cardHeader}>
          <p className={indexStyle.headerfont}>登录</p>
          <div className={`${indexStyle.borderStyle}`}/>
        </div>
        <div className={indexStyle.cardMain}>
          <Form
            name="basic"
            labelCol={{
              sm: 6,
              md: 6,
            }}
            wrapperCol={{
              sm: 18,
              md: 18,
            }}
            initialValues={{
              remember: true,
            }}
            autoComplete="off"
            onFinish={Login}
            size="large"
          >
            <Form.Item
              label="账号"
              name="username"
              rules={[
                {
                  required: true,
                  message: "请输入用户名!",
                },
                {
                  max: 32,
                  message: "账号名最长32位",
                },
              ]}
            >
              <Input placeholder="请输入用户名/邮箱" />
            </Form.Item>

            <Form.Item
              label="密码"
              name="password"
              style={{ marginTop: 30 }}
              rules={[
                {
                  required: true,
                  message: "请输入密码!",
                },
              ]}
            >
              <Input.Password placeholder="请输入密码" />
            </Form.Item>

            <Form.Item
              wrapperCol={{
                sm: { span: 16, offset: 6 },
              }}
            >
              <Button
                className={indexStyle.submitButton}
                type="primary"
                htmlType="submit"
              >
                登录
              </Button>
            </Form.Item>
          </Form>
        </div>
      </div>
    );
  }

  message.info("您已经登录，请勿重复登录");
  return <Navigate to="/index/homepage" />;
}

export default Login;
