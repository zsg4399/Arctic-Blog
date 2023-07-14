import React, { Suspense } from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import "./index.css";
import "swiper/css";
import "swiper/scss/navigation";
import "swiper/scss/pagination";
import store from "./app/store";
import AppRouter from "./router";
import { Provider } from "react-redux";
import { ConfigProvider } from "antd";
import "moment/locale/zh-cn";
import locale from "antd/es/locale/zh_CN";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Provider store={store}>
        <ConfigProvider locale={locale}>
          <Suspense>
          <AppRouter />
          </Suspense>
        </ConfigProvider>
      </Provider>
    </BrowserRouter>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
