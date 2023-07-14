const { createProxyMiddleware } = require("http-proxy-middleware");

//配置代理
module.exports = function (app) {
  app.use(
    //开发环境下配置代理跨域
    createProxyMiddleware("/api1", {
      target: "http://localhost:8001",
      changeOrigin: true,
      pathRewrite: { api1: "" },
    }),
  );
};
