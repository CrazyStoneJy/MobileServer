# MobileServer
这是一个以手机端作为服务器的例子

因为我手上两个旧手机，就想着用这个两个旧手机来搞点事情，又由于最近在学flask，就想着能不能把android手机作为服务器，整个局域网环境下的小型服务器.无意间看到洪洋大神的推送，[搭建Android上的服务器 实现隔空取物](http://www.wanandroid.com/blog/show/2020),这是使用一个android的库实现的，感觉挺有意思，便先研究一番，等有时间了再研究在android手机上跑python代码，Server库:https://github.com/koush/AndroidAsync

实现效果如下图所示：

url:http://192.168.1.103:8000/

![http://192.168.1.103:8000/](http://or5n6ccgu.bkt.clouddn.com/18-1-18/46200213.jpg)

url:http://192.168.1.103:8000/files

![http://192.168.1.103:8000/files](http://or5n6ccgu.bkt.clouddn.com/18-1-18/89741794.jpg)

url:http://192.168.1.103:8000/file

![http://192.168.1.103:8000/file](http://or5n6ccgu.bkt.clouddn.com/18-1-18/48415299.jpg)


参考：

[搭建Android上的服务器 实现隔空取物](http://www.wanandroid.com/blog/show/2020)

[Android下WIFI 隔空APK安装](https://www.jianshu.com/p/e0c172c4e3bf)

https://github.com/MZCretin/WifiTransfer-master

Server库：https://github.com/koush/AndroidAsync