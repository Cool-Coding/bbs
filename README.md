## 开发环境
- WindowsXp
- JDK1.6
## 开发工具
- MyEclipse
### WEB容器
- Tomcat
## 数据库
- MySQL
## 技术架构
- MVC
## 开发技术
### 后端
- spring容器
- structs1控制器
- JPA(Hibernate) ORM框架
### 前端
- html/css
- jstl标签语言
- JQuery框架
## 系统功能
   系统功能是从用户角色角度进行设计，主要有三类用户：普通用户、版主、管理员
### 普通用户
- 用户注册、登录
- 版块、贴子、回帖、附件浏览
- 发表帖子、回帖
- 上传附件
- 查询贴子
### 版主
- 普通用户拥有的权限
- 编辑帖子: 修改、删除、隐藏、移动
- 编辑回帖: 修改、删除、隐藏、登陆可见
### 管理员
- 版块管理
- 用户管理
- 贴子管理
- 角色管理
- 系统配置
### 系统
- 用户操作行为记录
## 源码下载使用
- 配置数据库连接、用户名、密码
- 运行cn.yang.init.InitPrivilegeAndRoleAndUser.java类中的init方法，初始化权限、角色及增加超级管理员用户
- 部署到Tomcat中运行

## 部分截图

### 前台

- 首页  
![首页](https://github.com/Cool-Coding/photos/blob/master/bbs/index.png)  

- 版块列表  
![版块列表](https://github.com/Cool-Coding/photos/blob/master/bbs/framelist.png)  

- 发表贴子  
![发表贴子](https://github.com/Cool-Coding/photos/blob/master/bbs/theme_post.png)  


### 后台  

- 版块管理  
![版块管理1](https://github.com/Cool-Coding/photos/blob/master/bbs/bbs02.png)  
 
![版块管理2](https://github.com/Cool-Coding/photos/blob/master/bbs/bbs03.png)  

- 角色管理
![角色管理1](https://github.com/Cool-Coding/photos/blob/master/bbs/role1.png)  

![角色管理2](https://github.com/Cool-Coding/photos/blob/master/bbs/role2.png)  

- 系统配置  
![系统配置](https://github.com/Cool-Coding/photos/blob/master/bbs/setting.png)  
