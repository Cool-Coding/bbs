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

![首页](https://picabstract-preview-ftn.weiyun.com/ftn_pic_abs_v3/1fd713fe9c6c4c80819b70692e29a4850f779ecbcc391a3545d5e800f6da49a1156504c786f9f3fb4a0113fa47c20e79?pictype=scale&from=30113&version=3.3.3.3&uin=542600078&fname=bbs01.png&size=750)

![版块列表](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/082baf50f152fb1a26e88bdab7571b2445a859f219e3efe58cf4c8fc4385ca6d9e4de93a3bc2d73320c7cb11a1d768ccb44dd84d451da16b804d51c3cd57bc69/frame.gif?fname=frame.gif&from=30113&version=3.3.3.3&uin=542600078)

![发表贴子](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/b22c21ff2b149191dcab618647d635c5aa838303238e49191e4a8faf60f1285045656779fdbf8ec43793de62e169714a09bf019182683de885c07db12d32a1ff/theme.gif?fname=theme.gif&from=30113&version=3.3.3.3&uin=542600078)

![发表回复](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/7ff274db7f9deb54135be64b2cc251be1899f689e66cb7500ddd7a89b3a6324b21274d576d08704a2374f631cb2c507897b5cd844ef6972d40241655cc7b7757/reply.gif?fname=reply.gif&from=30113&version=3.3.3.3&uin=542600078)

### 后台

![版块管理1](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/a0ccda91633f83637c955618fb99f133e434cafa0da43793df78a9ea8ba5792800a36406e5bfd62063b4f7c63d03cea4a4151231100c3521e7f7df7c89e50519/bbs02.png?fname=bbs02.png&from=30113&version=3.3.3.3&uin=542600078)
 
![版块管理2](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/fbbb756bf60515da038994a7a677b91e2e93525dca397cbaf4ca73818f048fffbe4d28b73b75e54115666e6db707bc9e89961b6e7b4a679f4584c763db883489/bbs03.png?fname=bbs03.png&from=30113&version=3.3.3.3&uin=542600078)

![角色管理](https://tj-cabbage-yun-ftn.weiyun.com/ftn_handler/1dd098ddbbfe9c9010213165ae280229f5b714904cfc60435d6bca1e10f3fa70cdb83f73f02793b7ac416062a1d78fefc37be7edb348a1cb4f0032b9c2ee6020/AttachmentRole.gif?fname=AttachmentRole.gif&from=30113&version=3.3.3.3&uin=542600078)
