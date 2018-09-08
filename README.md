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

![首页](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/148c7bda5e6e94fec23c8513c8a226648289f22fcc68e9c33d39fcb0cb3e5e9013c5004816bfddae113c0e4d393a0bfefc0fe1570752c7768a398c9de5b348d9/bbs01.png?fname=bbs01.png&from=30113&version=3.3.3.3&uin=542600078)

![版块列表](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/f808e811205d438b85e337b0d271d986ee95c7d9840b27370e301ec1f28a8adb0233e8084938a69364fcb4a386be4729f07e2608fc97738f4a9bc36bfb81e77a/framelist.png?fname=framelist.png&from=30113&version=3.3.3.3&uin=542600078)

![发表贴子](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/13970ac8ca3813965ceae3a5b761a69559c53ec18a8638ea8deab3f8111c06cce35b9f0c4699942af70d4f7f8432a715c9632b48a258ea45c21e4d1dae33fa57/theme_post.png?fname=theme_post.png&from=30113&version=3.3.3.3&uin=542600078)


### 后台

![版块管理1](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/a0ccda91633f83637c955618fb99f133e434cafa0da43793df78a9ea8ba5792800a36406e5bfd62063b4f7c63d03cea4a4151231100c3521e7f7df7c89e50519/bbs02.png?fname=bbs02.png&from=30113&version=3.3.3.3&uin=542600078)
 
![版块管理2](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/89ad40476458402fe5ff2bf5e465e72a18aecf13d6587c85a6f37bfbd37570355d42d8c7a18752e83d8752749e3b706124663ce8b52c85bf9823a9284153064f/bbs03.png?fname=bbs03.png&from=30113&version=3.3.3.3&uin=542600078)

![角色管理1](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/1004402189917b27567c646abcad01477a58aebb3dd67ea157a33423c20791a7f2e8412aa9914fdb995b54d4bb7708a945d991d6c6654ec6f53b23390308c586/role1.png?fname=role1.png&from=30113&version=3.3.3.3&uin=542600078)

![角色管理2](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/bba8d5cfeff5e76920daab8da44ac56425ae1db250609554f8c5bef52c6c2eb60208a4c7b48698bb94511ee17b1b5b78d208d080f7119451035a8e4e46748882/role2.png?fname=role2.png&from=30113&version=3.3.3.3&uin=542600078)

