import request from "./reuqest";
const getUserInfo = () => {
  return request({
    method: "GET",
    url: "/users/userinfo",
  });
};

const loginout = () => {
  return request({
    method: "POST",
    url: "/users/loginout",
  });
};

const editUserinfo = (field, value) => {
  if (field === "address") {
    value=value === null || typeof value === "undefined"?value=null:`${value[0]},${value[1]},${value[2]}`
  }
  return request({
    method: "POST",
    url: "/users/userinfo",
    data: {
      field,
      value,
    },
  });
};

const getAuthorInfo=()=>{
  return request({
    method:"GET",
    url:"/users/AuthorInfo"
  })
}
export { getUserInfo, loginout, editUserinfo,getAuthorInfo };
