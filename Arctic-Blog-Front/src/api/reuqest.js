import axios from "axios";

//创建axios实例
const request = axios.create({
  baseURL: "/api",
  timeout: 10000,
  headers: {
    "Content-Type": "Application/json;charset=utf-8",
  },
});
//统一配置请求拦截器
request.interceptors.request.use(
  (config) => {
  const token=localStorage.getItem("token")
  if(token!==null&&token!==""){
    config.headers.Authorization=token
  }
    return config;
  },
  (err) => {
    return err;
  }
);
//统一配置响应拦截器
request.interceptors.response.use(
  (res) => {
    return res;
  },
  (err) => {
    return err;
  }
);


export default request