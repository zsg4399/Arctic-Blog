import request from "./reuqest";

const getAllCategoryByUserId = (id) => {
  return request.get("/categorys/" + id);
};

export { getAllCategoryByUserId };
