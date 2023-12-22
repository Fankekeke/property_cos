#### 基于SpringBoot + Vue的小区物业管理系统

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios
报表：Spread.js

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok

1、实现小区信息管理（包括业主信息，楼宇信息，房间信息的管理，业主数量统计，房间数量统计（包括无人居住房与居住））；                                                                                               
2、实现物业费用缴纳管理（水电煤气清洁电梯停车位物业管理等费用数据管理，能够生成物业缴费的信息报表，缴费记录管理）；
3、物业项管理（包括水，电，煤气，清洁，消防，停车位等信息的管理）；
4、物业抄表入库管理（包括根据不同物业项以及业主的统计生成物业报表并将报表入库）；
5、业主日常管理（包括业主投诉，维修上报，业主评论及建议）；                                                                         
6、物业抄表入库管理（包括根据不同物业项以及业主的统计生成物业报表并将报表入库）；              
7、防疫登记管理（包括体温，健康码情况，进入时间以及地点）；                                                                        
8、工作人员管理（包括管理员信息，管理人员权限，物业工作人员信息）；              
9、公告管理（管理人员对相关的物业信息给予公告，包括停电停水暖气等相关信息）。

还有就是人脸识别  百度人工智能云的sdk 
以及一个梯度上升算法，
支付的话用支付宝的沙箱，


#### 前台启动方式

安装所需文件 yarn install 
运行 yarn run dev

#### 后端启动方式

1.首先启动redis，进入redis目录终端。输入redis-server回车
2.导入sql文件，修改数据库与redis连接配置
3.idea中启动后端项目

#### 默认后台账户密码

[管理员]
admin
1234qwer

[业主]
fank
1234qwer

#### 项目截图

|  |  |
|---------------------|---------------------|
|![p92YPFU.png](https://s1.ax1x.com/2023/05/15/p92YPFU.png) | ![p92Y9oT.png](https://s1.ax1x.com/2023/05/15/p92Y9oT.png) |
|![p92YASJ.png](https://s1.ax1x.com/2023/05/15/p92YASJ.png) | ![p92YSe0.png](https://s1.ax1x.com/2023/05/15/p92YSe0.png) |
|![p92YpwV.png](https://s1.ax1x.com/2023/05/15/p92YpwV.png) | ![p92YMFO.png](https://s1.ax1x.com/2023/05/15/p92YMFO.png) |
|![p92YiYF.png](https://s1.ax1x.com/2023/05/15/p92YiYF.png) | ![p92YFW4.png](https://s1.ax1x.com/2023/05/15/p92YFW4.png) |
|![p92YVyR.png](https://s1.ax1x.com/2023/05/15/p92YVyR.png) | ![p92YZO1.png](https://s1.ax1x.com/2023/05/15/p92YZO1.png) |
|![p92YEl9.png](https://s1.ax1x.com/2023/05/15/p92YEl9.png) | ![p92Ymex.png](https://s1.ax1x.com/2023/05/15/p92Ymex.png) |
|![p92YuTK.png](https://s1.ax1x.com/2023/05/15/p92YuTK.png) | ![p92Ynw6.png](https://s1.ax1x.com/2023/05/15/p92Ynw6.png) |

#### 演示视频

[项目视频：基于SpringBoot的小区物业管理系统](https://www.bilibili.com/video/BV1yu411v7oZ/)

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解`

#### 其它资源

[2023年答辩顺利通过](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年答辩通过率100%](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)


#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

More info: [悲伤的橘子树](https://berserker287.github.io/)
