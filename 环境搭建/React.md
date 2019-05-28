#React 搭建
  * 安装node,vscode
  * npm config set registry http://registry.npm.taobao.org/
  * 执行npm install create-react-app -g[create-react-app介绍](https://www.jianshu.com/p/77bf3944b0f4)
  * create-react-app demo1
  * cd demo1
  * Chrome插件React Developer Tools
  * 修改vscode设置
  "emmet.includeLanguages": {
     "javascript":"javascriptreact"
    },
    "emmet.triggerExpansionOnTab": true
  * npm start
  * localhost:3000
  * 修改启动端口
  ```
  "scripts": {
    "start": "set port=3001 && react-scripts start ",
    }
  ```
