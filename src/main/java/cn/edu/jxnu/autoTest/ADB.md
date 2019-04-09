### ADB命令：稳定性测试、UI自动化测试

经常需要使用的命令记录下。

```//后面是追加注释```


#### adb 命令

adb 命令是 adb 这个程序自带的一些命令


* 显示都有哪些设备连接
```adb devices```

* 清空日志
```adb logcat  -c```

* 将日志从手机实时推出到电脑文件中
```adb logcat  -v threadtime >> test.log```

* 将电脑文件推到手机文件中
```adb push monkey.jar /sdcard```

* 从手机拉取到本地
```adb pull sdcard/test.txt /User/xx/xx/xx/xx``` //例如复制 Sdcard 下的 pull.txt 文件到 D 盘：

* 进入adb模式，操作手机
```adb shell```

* sdcard路径
`````/mnt/sdcard`````

* 设定指定的device
```adb -s deviceSerial shell```

PS:远程设备 adb -s ip:port shell

*  结束 adb 服务、启动 adb 服务
```adb kill-server、adb start-server```

* 安装app
```adb install``` //覆盖安装是使用 -r 选项，目标 apk 存放于 PC 端，请用 adb install 安装

```pm install``` //adb shell 命令，目标 apk 存放于 Android 设备上，请用 pm install 安装

* 卸载应用
```adb uninstall``` //后面跟的参数是应用的包名（特别注意），-k 选项，卸载时保存数据和缓存目录

```pm uninstall``` //同上安装

* 重启 Android 设备
```adb reboot```

* 进入 fastboot 模式
```bootloader、adb reboot-bootloader``` //进入 fastboot 模式

```recovery``` //进入 recovery 模式

* 将宿主机上的某个端口重定向到设备的某个端口
```adb forward tcp:1314 tcp:8888``` //执行该命令后所有发往宿主机 1314 端口的消息、数据都会转发到 Android 设备的 8888 端口上，因此可以通过远程的方式控制 Android 设备。
                                
* 远程连接 Android 设备
```adb connect ip:port``` //一般5555


#### adb shell 命令

```adb shell``` 命令则是调用的 Android 系统中的命令，这些 Android 特有的命令都放在了 Android 设备的 system/bin 目录下


*  列出安装在设备上的应用
```adb shell pm list package``` //显示的是 package:包名
    
    ```
    -s：列出系统应用
    -3：列出第三方应用
    -f：列出应用包名及对应的apk名及存放位置
    -i：列出应用包名及其安装来源
    ```
    
```adb shell pm list package -f -3 -i qutest``` //过滤关键字，可以很方便地查找自己想要的应用

* 列出对应包名的 .apk 位置
```adb shell pm path com.qu.test```

* 实时查看当前正在运行的Activity
```adb shell logcat | grep ActivityManager```

* 查看当前activity
```adb shell "dumpsys window | grep mCurrentFocus"```

* 列出含有单元测试 case 的应用
```pm list instrumentation```

* 获取当前安卓系统版本，并赋值给变量
```osVersion=$(adb devices shell getpropro.build.version.release)```

* 查看运行在 Android 设备上的 adb 后台进程
```adb shell ps | grep adbd```

* 列出指定应用的 dump 信息
```adb shell pm dump com.qu.test```

* 清除应用数据
```pm clear```

* 设置应用安装位置、获取应用安装位置
```pm set-install-location、pm get-install-location```

* 截图
```adb shell screencap -p /sdcard/screen.png``` //截屏，保存至 sdcard 目录
                                            
* 发送按键事件
```adb shell input keyevent KEYCODE_HOME```

* 显示所有输入法
```adb shell ime list -s``` //禁用的不显示

* 禁用输入法
```adb shell ime desable com.sohu.inputmethod.sogouoem/.SogouIME``` //com.sohu.inputmethod.sogouoem/.SogouIME是输入法id，由前面命令得到

* 将ADBKeyBoard输入法设置为默认输入法
```adb shell ime set com.android.adbkeyboard/.AdbIME```

* uiautomator传多参数
```adb shell uiautomator runtest jar包 -c 含监听方法的全类名 -e key1 value1 -e key2 value2```

* 关闭app
```adb shell am force-stop $packageName``` //app包名

* 输入
```adb shell input keyevent 3``` //3是键对应的值

* 设置代理
```adb shell settings put global http_proxy ip:port```

* 移除代理
```
adb shell settings delete global http_proxy
adb shell settings delete global global_http_proxy_host
adb shell settings delete global global_http_proxy_port 
```
//移除必须在重启后生效

仅供参考。后续遇到还会补充。。。
















