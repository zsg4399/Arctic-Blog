import { configureStore } from "@reduxjs/toolkit";
import counterSlice from "../features/counter/counterSlice";
import tagSlice from "../features/tag/tagSlice";
import categorySlice from "../features/category/categorySlice";

export default configureStore({
  reducer: {
    counter: counterSlice,
    tags:tagSlice,
    categorys:categorySlice,
  },
});
