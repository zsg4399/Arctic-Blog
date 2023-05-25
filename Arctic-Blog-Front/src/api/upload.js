import axios from "axios";
import request from "./reuqest";

const upload = axios.create({
  baseURL: "/smms",
  timeout: 15000,
  headers: { "Content-Type": "multipart/form-data" },
});

upload.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("Access_Token");
    if (token !== undefined && token !== "" && token !== null) {
      config.headers.Authorization = token;
    }
    return config;
  },
  (err) => {
    return err;
  }
);

upload.interceptors.response.use(
  (res) => {
    return res;
  },
  (err) => {
    return err;
  }
);

const getUploadToken = () => {
  return request.get("/images/token");
};

const UploadImg = (file) => {
  let formdata = new FormData();
  formdata.append("smfile", file);
  return upload.post("/upload",formdata)
};

export { getUploadToken, UploadImg };
export default upload;
