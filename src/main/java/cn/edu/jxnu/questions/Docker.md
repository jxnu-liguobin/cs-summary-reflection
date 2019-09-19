* 目录
{:toc}

### 说明

//是附加注释

//此处主要是使用过的一些命令，不是docker大全！

//Mac上操作

//docker命令未加sudo

//Docker命令分为镜像命令、容器命令、其他命令

//K8s命令 待补充

### Docker命令

* 安装docker

CentOS6
```
//EPEL库
sudo yum install http://mirrors.yun-idc.com/epel/6/i386/epel-release-6-8.noarch.rpm
sudo yum install docker-io
```
CentOS7
```
sudo yum install docker
``` 

注册为服务
```
sudo service docker start
sudo chkconfig docker on
```
  
#### 镜像相关命令

Docker镜像就是一个只读的模板

* 安装Linux 

``` docker pull centos:6  ``` //6是版本号

* 查看所有的镜像，也包括tag在里面 

``` docker images ```

* 使用仓库的Dockerfile文件，创建一个镜像 

```docker build -t IMAGE```

* 使用当前目录的Dockerfile创建镜像，标签为gio-test/test-project:test-tag
  
```docker build -t gio-test/test-project:test-tag .``` //后面有个点

* 显示这台机器上的所有镜像

```docker images -a```

* 从本机移除所有的镜像

```docker rmi $(docker images -q)```

* 移除本地test镜像

```sudo docker rmi test/test``` //docker rm 命令是移除容器

* 上传打完tag的镜像到remote的registry

```docker push username/repository:tag```

* 从registry运行指定的镜像

```docker run username/repository:tag```

如果你没有在这些命令中指定 ``` :tag ``` 部分，在你生成和运行镜像时，最新的tag ``` :latest ``` 会被默认使用。 
如果没有指定tag，Docker会使用最新的镜像版本，即latest（所以不指定会坑爹）

 查看镜像的详细信息

```docker inspect IMAGE```

* 查看私有库的镜像

```curl 192.168.x.x:5000/v2/_catalog```

* 搜索镜像

```docker search consul``` //consul 服务注册发现框架

* 导出镜像到本地文件

```docker save -o ubuntu_14.04.tar ubuntu:14.04``` //导出ubuntu到ubuntu_14.04.tar

* 从文件载入镜像

```
docker load --input ubuntu_14.04.tar
//或
docker load < ubuntu_14.04.tar //镜像存储文件将保存完整记录，体积也要大
```

#### 容器相关命令

Docker利用容器来运行应用，容器是从镜像创建的运行实例

* 查看正在运行的所有的容器的列表

```docker ps```

* 停止运行指定的容器

```docker stop CONTAINER_ID```

* 查看所有的容器列表，包括没有在运行的

```docker ps -a```

* 强行关闭指定的容器 

```docker kill CONTAINER_ID``` //CONTAINER_ID是容器ID

* 从这台机器上移除所有未运行的容器

```docker rm $(docker ps -a -q)```

* push文件到docker的某个容器中

```docker cp squid.conf  CONTAINER_ID:/etc/squid```

* 进入运行的容器

```docker exec -it CONTAINER_ID /bin/bash``` //可能有镜像的多个实例，需要指定运行的容器ID，而不是镜像ID

* 将本地目录挂载到docker容器上

```docker run -i -t -v /Users/xx:/etc/squid3 IMAGE /bin/bash``` 
//docker run -i -t -v 本地绝对路径:docker上绝对路径 镜像ID  /bin/bash

* 将本地目录挂载到docker容器上并加端口映射启动容器

```docker run  -p 12345:3128 -i -t -v  /Users/xx:/etc/squid3 IMAGE /bin/bash```

* 运行镜像，并且映射主机端口12345 到容器端口 3128（默认）

```docker run -p 12345:3128 IMAGE```  //是镜像ID，下同

* 同上，但是此条命令是把镜像运行在后台进程中

```docker run -p -d 12345:3128 IMAGE```  //-d表示后台运行

* 进入正则运行的容器，并执行命令

```docker exec -it CONTAINER_ID /bin/bash-c "ls"``` //ls是需要执行的命令

* 将一个已经终止的容器启动运行起来

```docker start```

* 重新启动

```docker restart```

* 提交容器修改

```docker commit CONTAINER_ID IMAGE``` //不提交下次启动就是新的，里面修改都没有

* 修改自己commit的容器tag

```docker tag IMAGE_ID userName/repositoryName:tag``` //坑，需要执行docker login，即时使用图形界面登陆过

* 使用本地配置文件启动squid代理

```docker run -p 3128:3128 -i -t -v /Users/xx:/etc/squid3 IMAGE```

* 导出容器快照到本地文件

```docker export CONTAINER_ID > fileName.tar```

* 从容器快照文件中再导入为镜像

```cat fileName.tar | sudo docker import - test/test:1.0```

* 通过指定URL或者某个目录来导入

```docker import http://example.com/exampleimage.tgz example/imagerepo``` //容器快照文件将丢弃所有的历史记录和元数据信息

#### 其他

* 推送到私服

```docker push 192.168.x.x:5000/docker-repo/squid:3.3.8-14```

* docker的运行开启HTTP配置

/Users/xx/.docker/daemon.json //使用安装包安装的

```
{
  "debug" : true,
  "insecure-registries" : [
    "192.168.x.x:5000"
  ],
  "experimental" : true
}
```

* 拷贝docker的文件到本地

```docker cp CONTAINER_ID:/etc/squid3/squid.conf /Users/xx/xx```

* 本地文件拷贝到docker

```docker cp 本地路径 容器ID:容器路径 ```

* 查看docker的端口映射

```docker port  CONTAINER_ID```

* 给容器安装vim命令

```apt-get update``` //同步 ```/etc/apt/sources.list``` 和 ```/etc/apt/sources.list.d```中列出的源的索引 

```apt-get install vim``` //安装命令，其他类似（先登录进运行的容器，关闭容器则失效）

* 查看squid代理的链接访问日志

```tail -f /var/log/squid/access.log``` //根据版本可能是squid3

* 容器内的Linux安装Apache

```apt-get install apache2=2.4.29-1ubuntu4.5``` //后面是版本号

* 使用Docker credentials 登录到CLI会话

```docker login```

* 添加squid的证书

```sudo htpasswd -c squid_passwd  dreamylost```

### K8s命令

* 端口映射
```
映射本地端口到远程k8s容器的pod上的端口，以k8s-ads-tracking的server-1为例
1.查询所有运行的pods并以k8s-ads-tracking开头的
kubectl -n k8s-ads-tracking get pods --kubeconfig=/Users/name/project-conf/k8s-config
k8s-config是k8s的验证配置文件，有秘钥
2.进入运行的pod 选择server-1
kubectl -n k8s-ads-tracking exec -ti k8s-ads-tracking-server-1-85f4ff5fcb-9b9bs bash --kubeconfig=/Users/name/project-conf/k8s-config
3.查看网络连接
netstat -antp | grep 1
4.映射端口
kubectl -n k8s-ads-tracking port-forward k8s-ads-tracking-server-1-85f4ff5fcb-9b9bs 19000:19000 --kubeconfig=/Users/name/project-conf/k8s-config
5.完成将本地19000端口映射到k8s环境的`k8s-ads-tracking-server-1-85f4ff5fcb-9b9bs`实例上的19000端口上
```

* 获取Pod的运行状态

```kubectl get pods``` //以nginx为例，下同

* 获取Pod中所有容器的运行状态

```kubectl  get pods nginx -o json```

* 获得Pod的更多详细信息

```kubectl describe pods nginx```

* 根据Pod的名称删除指定的Pod

```kubectl delete pods/nginx```

* 根据yaml配置文件删除Pod

```kubectl delete -f pod-nginx.yaml```

* 进入到容器内部

```kubectl exec -it nginx bash```

* 如果Pod中有多个容器，可以使用-c参数指定具体的容器

```kubectl exec -it nginx bash -c nginx```

* 观察容器的输出日志

```kubectl logs nginx```


待补充。。。


[最详细的 在Windows上 使用docker 搭建 consul 集群](https://blog.csdn.net/qq_34446485/article/details/90738092)

PS:镜像可以理解是已经打包的开放包，容器是包的一次执行，与运行时相关的一般都使用容器CONTAINER_ID
