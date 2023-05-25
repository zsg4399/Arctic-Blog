# Arctic-Blog

本仓库是博客系统的前端系统，页面主体基于`antd 4.24.2`+`react 18.2.0`完成搭建

## 技术介绍：
* 基础框架React `18.2.0`
* 状态管理:React-Redux `8.0.5`
* 网络请求：axios `1.1.3`
* UI框架：antd `4.24.2`
* 富文本编辑器: braft-editor `2.3.9` (后续会考虑增加Markdown编辑，从而能支持Markdown编辑文章)
* 样式框架: `scss`
## 项目介绍请看[Arctic-Blog-backend](https://github.com/zsg4399/Arctic-blog-backend)

## 安装本地运行
在安装前请确认系统是否安装node.js，yarn是基于node.js来进行包管理的

首先克隆本仓库到本地
`git clone https://github.com/zsg4399/Arctic-Blog-Front.git`

然后cmd进入项目目录
执行指令 `yarn` 
等待依赖安装完成后即可安装下述操作本地运行此项目了

## Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

### Available Scripts

In the project directory, you can run:

### `yarn start`
开发模式运行项目，执行后项目便会开始构建并打开浏览器，首次加载速度比较慢，大概可能要个30s，这也是客户端渲染的一值得诟病的地方，那就是首屏加载性能很低
Runs the app in the development mode.\
Open [http://localhost](http://localhost) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `yarn test`
测试模式运行，没有深入了解前端测试故不再赘述
Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `yarn build`
打包构建生产环境的项目
Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `yarn eject`
这个指令是一次性的，执行完毕完会将webpack的配置文件等重要文件暴露出来，而且亲测会导致一些意料之外的bug出现，我之前的项目里面执行后就出现了无法解决的bug，回滚到这条指令执行前又能正常运行了

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

