# Software-Innovation-Competition
Dev quick apps based on Software-Innovation-Competition


This is our Software-Innovation-Competition project, which is about to dev an quick app concerning automatic note system named **Auto-Notes**. Details are in the docx files below. Feel free to refer to but do not directly "control + c" 😄。

There are very few dictation knowledge-based software in the market, and few similar software relying on the fast application platform, which causes great social pain in the relevant industry field, and also lacks corresponding application to solve the relevant needs of users. 


According to the market, the existing mobile app only uses NLP technology to realize voice to text function, and only supports single person use and record, which brings problems such as poor recognition effect, poor user experience, and cumbersome use process. "Auto-Notes" not only converts voice into text, but also supports users to upload photos, extract and automatically organize the text in photos, sense the meaning of words, then extract keywords, and present the knowledge map in front of users.


"Auto-Notes" is mainly aimed at the user groups participating in meetings, lectures, interviews and classes. Users can carry out real-time classes or meetings by using the face-to-face group building function. It aims to help users record and supplement the missed meeting points and wonderful moments, record the contents of the meeting in real time, build a knowledge map to feed back to users, and realize intelligent summary generation, automatic word meaning search and pen writing Record summary and knowledge point summary, realize the secondary extraction of information with the help of deep learning technology, and build a complete intelligent summary integrated solution. 


At the same time, "Auto-Notes" supports multiple people to record at the same time. It can extract the best record results from multiple sample records, greatly improving the recognition rate and making it more user-friendly.


## 下面是项目的最终交稿作品

<video id="video" controls="" preload="none" poster="">
<source id="mp4" src="https://github.com/zhyChesterCheung/Software-Innovation-Competition/blob/master/movie.mp4" type="video/mp4">
</video>


## 项目目录

- [上手指南](#上手指南)
  - [开发前的配置要求](#开发前的配置要求)
  - [安装步骤](#安装步骤)
- [部署](#部署)
- [贡献者](#贡献者)
  - [如何参与开源项目](#如何参与开源项目)
- [版本控制](#版本控制)
- [作者](#作者)


**开发快应用真正用到的还是html(ux)、css和js的语法**

###### 开发前的配置要求

1. [https://doc.quickapp.cn](https://doc.quickapp.cn) 参照quickapp的官方文档进行入门教程的安装

2. 安装nodejs：官方建议不使用8.0.*版本．这个版本内部ZipStream实现与node-archive包不兼容，会引起报错；

3. 安装完node环境后建议安装cnpm，这样下载库时会走淘宝的node仓库，会更快：
```php
npm install -g cnpm --registry=https://registry.npm.taobao.org
```

4. 安装hap-toolkit
```php
cnpm install -g hap-toolkit
```

5. 创建quickapp project：
```php
hap init <ProjectName>
```

6. 根据package.json nodejs package.json详解安装库：
```php
cnpm install
```

7. 编译项目：
```php
npm run build
```

8. 如果希望每次修改源代码文件后，都自动重新编译项目，请使用如下命令：
```php
npm run watch
```

9. 安装debug APP
[DebugAPP下载](https://www.jianshu.com/go-wild?ac=2&url=https%3A%2F%2Fstatres.quickapp.cn%2Fquickapp%2Fquickapp%2F201803%2Ffile%2F201803221213415527241.apk)


###### **安装步骤**

eg.l.



### 部署

由于快应用容易上手的特性，可以进行简单部署：

1. 扫码安装-启动HTTP服务器:

在终端中新建一个窗口，进入项目的根目录运行如下命令，启动本地服务器（默认端口为12306）
```php
npm run server
```

在Debug APP上预览运行效果

配置HTTP服务器地址有两种方式，以下两者选其一即可：

打开调试器 --> 点击"扫码安装"，扫描终端窗口中的二维码即可完成配置（若扫描不成功，可在浏览器中打开页面：**http://localhost:port**，扫描页面中的二维码）

打开调试器 --> 点击右上角menu --> 设置，输入终端窗口中提示的HTTP服务器地址

配置完成后，若没有自动唤起平台运行rpk包，点击在线更新唤起平台运行rpk包，若提示安装失败，请检查执行npm run server的终端窗口是否正常运行。

2. 本地安装-复制rpk包到手机中

将<ProjectName>/dist目录下编译产出的rpk包通过USB数据线或其他方式，复制到手机文件系统中

本地安装rpk包：

打开调试器 --> 点击"本地安装"，选择手机文件系统中的rpk包，并自动唤起平台运行rpk包，查看效果


### 贡献者

请阅读**CONTRIBUTING.md** 查阅为该项目做出贡献的开发者。

#### 如何参与开源项目

贡献使开源社区成为一个学习、激励和创造的绝佳场所。你所作的任何贡献都是**非常感谢**的。


1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



### 版本控制

该项目使用Git进行版本管理。您可以在repository参看当前可用版本。

### 作者

email：cheung.zhy.csu@gmail.com

Blog：[https://zhychestercheung.github.io](https://zhychestercheung.github.io)

知乎:Chester  &ensp; qq: 2640617395

 *您也可以在贡献者名单中参看所有参与该项目的开发者。*
