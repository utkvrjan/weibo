# 简易版微博客系统的设计与实现
## 基本功能要求：
⽤户账号服务：⽤户注册、⽤户登录、⽤户退出 

微博查看：微博列表，⽀持按发布时间、评论数、点赞数等排序⽅式，微博列表可分⻚，未注册⽤户可以查看微博内容及评论。 

微博发布：注册⽤户可以发布微博，微博内容可包含图⽚ 

评论：注册⽤户可对微博发布评论，点赞 

关注：⽀持⽤户可以关注其他⽤户 

## 需求分析：

分析基本功能要求，得到该系统各⻚⾯功能及联系：

具体各⻚⾯需要实现的功能：
**主界⾯：**
* 1.根据登录情况显示个⼈信息： 
  * a)未登录时显示 登录/注册 按钮 
  * b)已登录时显示 头像、昵称、粉丝数、关注数、上次登录时间 
* 2.微博发送窗⼝，⽂本框和图⽚导⼊ 
* 3.微博展示：显示内容包括： 
  * 发送者头像，昵称，发布时间 
  * 微博内容图⽚，⽂本内容，点赞数，点赞/取消点赞 按钮
  * 评论界⾯，包括发送者头像，昵称，发布时间，评论⽂本内容 
  * 发布评论窗⼝，包括⽂本框和按钮 
  * ⻚码展示，包括当前⻚码，上⼀⻚/下⼀⻚跳转按钮 
  * 
**登录⻚⾯：** 
* 显示登录窗⼝，包括账号、密码及验证码 

**注册⻚⾯：** 
* 显示注册信息输⼊窗⼝：包括账号、密码、邮箱、性别、昵称 

**个⼈⻚⾯：**  
* 头像，昵称，粉丝数、关注数、关注/取消关注 按钮 
* 个⼈微博记录：类似主界⾯微博展示 

**修改个⼈信息⻚⾯：** 
* 提供修改账号、密码、邮箱、性别、昵称的界⾯



## 具体设计

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210327223931373.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE5NzEyMA==,size_16,color_FFFFFF,t_70)

注册时向user数据表中添加新⽤户信息，暂时不考虑注册失败的情况 
登录时⾸先核验验证码输⼊，然后匹配⽤户名和密码（密码采⽤MD5加密），当两个 验证都成功时登录操作成功，返回登录状态下的主⻚⾯；失败则返回登录界⾯ 
退出登录后返回登录界⾯

## 演示页面：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021032722414257.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE5NzEyMA==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210327224156588.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE5NzEyMA==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210327224209772.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE5NzEyMA==,size_16,color_FFFFFF,t_70)

## 问题分析

项⽬规划不到位，在最开始我计划加⼊⼀些额外功能，但是因为在实际开发中时间有限，这些功能被砍掉了，此外⼀些基础功能的完成情况也不甚理想，主要还是时间不够 

按计划从个⼈主⻚返回时应更改该变更主⻚的登录状态，时间仓促，未能实现 

前端界⾯不够美观，时间太过仓促，⽆法完善界⾯UI 

运行所需环境：

IntelliJ IDEA 2020.3.1 x64

Navicat 15 for MySQL

apache-tomcat-9.0.41

apache-maven-3.6.3

mysql-8.0.22-winx64
