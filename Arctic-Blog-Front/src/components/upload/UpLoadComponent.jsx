import { LoadingOutlined, PlusOutlined } from "@ant-design/icons";
import { message, Upload } from "antd";
import React, { useState } from "react";
//获取图片的base64编码
const getBase64 = (img, callback) => {
  const reader = new FileReader();
  reader.addEventListener("load", () => callback(reader.result));
  reader.readAsDataURL(img);
};
//在每次上传前执行的回调
const beforeUpload = (file) => {
  const isJpgOrPng =
    file.type === "image/jpeg" ||
    file.type === "image/png" ||
    file.type === "image/jpg";
  if (!isJpgOrPng) {
    message.error("只支持jpg/jpeg和png格式的图片上传");
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error("图片大小不可大于 2MB!");
  }
  return isJpgOrPng && isLt2M;
};

const UploadComponet = (props) => {
  const [loading, setLoading] = useState(false);
  const [imageUrl, setImageUrl] = useState();
  
  const customRequest = (info) => {
    setLoading(true)
    props.setimage(info.file)
    getBase64(info.file,(url)=>{
      setImageUrl(url)
      setLoading(false)
    })

  };
  const uploadButton = (
    <div>
      {loading ? <LoadingOutlined /> : <PlusOutlined />}
      <div
        style={{
          marginTop: 8,
        }}
      >
        上传封面图
      </div>
    </div>
  );
  return (
    <Upload
      name="upload"
      listType="picture-card"
      customRequest={customRequest}
      showUploadList={false}
      beforeUpload={beforeUpload}
      maxCount={1}
    >
      {imageUrl ? (
        <img
          src={imageUrl}
          alt="avatar"
          style={{
            width:"100%",
            height:"100%"
          }}
        />
      ) : (
        uploadButton
      )}
    </Upload>
  );
};
export default UploadComponet;
