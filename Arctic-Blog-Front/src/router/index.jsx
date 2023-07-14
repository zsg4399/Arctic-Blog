import { lazy } from "react";
import { useRoutes } from "react-router-dom";
const WelCome = lazy(() => import("../pages/Welcome/Welcome"));
const PersonCenter = lazy(() => import("../pages/PersonCenter/PersonCenter"));
const MyDetail = lazy(() => import("../pages/PersonCenter/component/MyDetail"));
const AddArticle = lazy(() => import("../pages/addarticle/AddArticle"));
const Index = lazy(() => import("../pages/index/Index"));
const Homepage = lazy(() => import("../components/HomePage"));
const Register = lazy(() => import("../components/Register"));
const Login = lazy(() => import("../components/Login"));
const ArticleContent = lazy(() =>
  import("../components/articleContent/articleContent")
);
const About = lazy(() => import("../pages/about/About"));

//使用useRoutes注册路由必须在最外侧包裹一层hashroute或者browerroute
const AppRouter = () => {
  let route = useRoutes([
    {
      path: "/",
      element: <WelCome />,
    },
    {
      path: "/person/center",
      element: <PersonCenter />,
      children: [
        {
          path: "profile",
          element: <MyDetail />,
        },
      ],
    },
    {
      path: "/article/add",
      element: <AddArticle />,
    },
    {
      path: "/index",
      element: <Index />,
      children: [
        {
          path: "register",
          element: <Register />,
        },
        {
          path: "login",
          element: <Login />,
        },
        {
          path: "homepage",
          element: <Homepage />,
        },
        {
          path: "article/detail",
          element: <ArticleContent />,
        },
        {
          path: "about",
          element: <About />,
        },
      ],
    },
  ]);
  return route;
};

export default AppRouter;
