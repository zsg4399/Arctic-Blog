import { createSlice } from "@reduxjs/toolkit";
import { message } from "antd";

export const categorySlice = createSlice({
  name: "categorys",
  initialState: {
    value: [],
  },
  reducers: {
    setCategorys: (state, actions) => {
      state.value = [...actions.payload];
    },
    addCategory: (state, actions) => {
      if (state.value.length >= 3) {
        message.error({
          content: "最多只能选择三个分类专栏",
        });
        return;
      }
      let flag = true;
      state.value.forEach((category) => {
        if (category.categoryName === actions.payload) flag = false;
      });
      if (flag)
        state.value = [...state.value, { categoryName: actions.payload }];
    },
    /**
     * 根据分类名删除分类
     * @param {}} state
     * @param {*} actions
     */
    removeCategory: (state, actions) => {
      state.value = state.value.filter(
        (e) => e.categoryName !== actions.payload
      );
    },
  },
});

export const { setCategorys, addCategory, removeCategory } =
  categorySlice.actions;
export default categorySlice.reducer;
