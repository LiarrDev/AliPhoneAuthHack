package com.goh.aliphoneauthhack;

public class AliParameter {

    // 阿里提供的 Demo 里的包名，替换为申请参数时填写的包名
    public static String AUTH_PACKAGE_NAME = "com.aliqin.mytel";

    // 阿里提供的 Demo 里的密钥，替换为申请的密钥，理论上密钥应当放在服务端
    public static String AUTH_KEY = "7KHffk2Cn1j17+QVA2zbJfdDteDSUDspB/s+FUoAhyXmQ/wueAQBcpMDOVLrp5lt5BDIGxDrCuTBZk7TcR4CxAQvHnJUPIaCI5dscbBFqHgHVI8Yoy0nYwsFo8Gyd2RZ6MbUAZr3lsnPQsA+UW1MZY9EP94x0TrXmwEJkU5xJgmOJfCSekYWHP5xNc0as/aWkTmNrjFyb5//93cAMwQllH0FFEFF+GEd7XMvm6ap/g4BD8676+z29MbePXPjoY6u3VrNTMkksQHW1EolxJkw+9Sa5pDsdOrQjXBz056J79PpNAFlTvPMZw==";

    // 拉起 LoginAuthActivity 的请求 CODE
    public static int ACTIVITY_REQUEST_CODE = 7076;

    // 三大运营商的协议地址
    public static String PHONE_AUTH_YIDONG_CONTRACT = "https://wap.cmpassport.com/resources/html/contract.html";
    public static String PHONE_AUTH_LIANTONG_CONTRACT = "https://ms.zzx9.cn/html/oauth/protocol2.html";
    public static String PHONE_AUTH_DIANXIN_CONTRACT = "https://e.189.cn/sdk/agreement/content.do?type=main&appKey=&hidetop=true";
}
