import React from "react";
import indexStyle from "../pages/index/index.module.scss";
import { Input, Form, Button } from "antd";
import { userLogin } from "../api/login";
import { useNavigate } from "react-router-dom";


function Login() {
  const navigate = useNavigate();
  const Login = (values) => {
    const { username, password } = values;
    userLogin(username, password).then((res) => {
      localStorage.setItem("token",res.data.data.access_token)
      localStorage.setItem("userinfo",JSON.stringify(res.data.data.userinfo))
      navigate("/index/homepage");
      window.location.reload();
    });
  };
  return (
    <div className={indexStyle.loginCard}>
      <div className={indexStyle.cardHeader}>
        <p className={indexStyle.headerfont}>登录</p>
        <hr className={indexStyle.hrStyle} />
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
              span: 16,
              offset: 6,
            }}
          >
            <Button
              style={{ marginLeft: 240 }}
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

export default Login;
