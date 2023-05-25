import request from "./reuqest";
//用户注册功能
const userRegister = (username, password, email, verifyCode) => {
  return request.post("/users/register", {
    username: username,
    password: password,
    email: email,
    verifyCode: verifyCode,
  });
};

//向邮箱或者手机号发送验证码
const createVerifyCode = (username, email, password) => {
  return request.post("/users/createcode", {
    username: username,
    email: email,
    password: password,
  });
};

//用户登录功能
const userLogin = (username, password) => {
  return request.post("/users/login", {
    username: username,
    password: password,
  });
};

const getAvatarInfo = () => {
  return request.get("/users/getAvatar");
};
export { userRegister, userLogin, createVerifyCode, getAvatarInfo };
