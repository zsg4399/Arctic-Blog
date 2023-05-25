const { createProxyMiddleware } = require("http-proxy-middleware");

//配置代理
module.exports = function (app) {
  app.use(
    createProxyMiddleware("/api", {
      target: "http://localhost:8001",
      changeOrigin: true,
      pathRewrite: { api: "" },
    }),
    createProxyMiddleware("/smms", {
      target: "https://smms.app/api/v2/",
      changeOrigin: true,
      pathRewrite: { smms: "" },
      secure:true
    })
  );
};
