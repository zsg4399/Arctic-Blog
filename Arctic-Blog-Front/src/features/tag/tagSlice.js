import { createSlice } from "@reduxjs/toolkit";
import { message } from "antd";

export const tagSlice = createSlice({
  name: "tags",
  initialState: {
    value: [],
  },
  reducers: {
    setTags: (state, actions) => {
      state.value = [...actions.payload];
    },
    addTag: (state, actions) => {
      console.log(state.value);
      if (state.value.length >= 5) {
        message.error({
          content: "最多只能选择5个作为文章标签",
        });
        return;
      }
      let k = true;
      state.value.forEach((e) => {
        if (e.tagName === actions.payload) k = false;
      });
      if (k === true)
        state.value = [...state.value, { tagName: actions.payload }];
    },
    /**
     * 根据标签名删除标签
     * @param {*} state 
     * @param {*} actions 
     */
    removeTag: (state, actions) => {
      state.value = state.value.filter(
        (tag) => tag.tagName !== actions.payload
      );
    },
  },
});

export const { setTags, addTag, removeTag } = tagSlice.actions;

export default tagSlice.reducer;
