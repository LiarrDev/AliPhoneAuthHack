# AliPhoneAuthHack

[绕过阿里云本机号码一键登录包名检测机制](https://liarrdev.github.io/post/Bypassing-the-Package-Name-Detection-of-Phone-Number-Verification-Service-Provided-by-Alibaba-Cloud/)

## 原理

一键登录 SDK 调用时需要传入 `Context`，SDK 会通过这个 `Context` 获取包名，所以只需要自定义一个 `Context`，让它在获取包名的时候返回自定义的包名，就可以欺骗 SDK。

同时还需要修改 `phoneNumber-L-AuthSDK.arr` 库文件，来适配我们的逻辑。主要是修改授权页 `LoginAuthActivity` 和呼起授权页的逻辑 `d` 两个文件。

## 注意

- 需先参考阿里云一键登录官方文档及 Demo。
- 包内使用的是阿里云一键登录官方 Demo 中的参数，包括签名、包名和密钥，实际使用替换为自己的参数即可。
- 该版本去掉了默认的授权页样式，在 `LoginAuthActivity` 自定义即可。
- 理论上也可以绕过签名检测机制，但是我没有这个需求，所以没有写。
