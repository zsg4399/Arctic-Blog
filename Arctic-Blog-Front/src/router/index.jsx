import { useRoutes } from "react-router-dom";
import Welcome from "../pages/Welcome/Welcome";
import Index from "../pages/index/Index";
import Register from "../components/Register";
import Login from "../components/Login";
import HomePage from "../components/HomePage";
import AddArticle from "../pages/addarticle/AddArticle";
import ArticleContent from "../components/articleContent/articleContent";
import PersonCenter from "../pages/PersonCenter/PersonCenter";
import MyDetail from "../pages/PersonCenter/component/MyDetail";
import About from "../pages/about/About";


//使用useRoutes注册路由必须在最外侧包裹一层hashroute或者browerroute
const AppRouter = () => {
  let route = useRoutes([
    {
      path: "/",
      element: <Welcome />,
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
          element: <HomePage />,
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
