### Docker基础命令

```//```是附加注释

* 1.安装docker

```jshelllanguage
sudo apt-get update
sudo apt-get install  docker
sudo apt-get install  docker.io
sudo apt-get install  docker-registry
```
  
* 2.安装linux 

```docker pull centos:6``` //6是版本号

* 3.查看所有的镜像，也包括tag在里面 

```docker images```

* 4.使用仓库的Dockerfile文件，创建一个镜像 

```docker build -t IMAGE```

* 5.运行镜像，并且映射主机端口12345 到容器端口 3128（默认）

```docker run -p 12345:3128 IMAGE```  //是镜像ID，下同

* 6.同上，但是此条命令是把镜像运行在后台进程中

```docker run -p 12345:3128 IMAGE```  //-d表示后台运行

* 7.查看正在运行的所有的容器的列表

```docker ps```

* 8.停止运行指定的容器

```docker stop IMAGE```

* 9.查看所有的容器列表，包括没有在运行的

```docker ps -a```

* 10.强行关闭指定的容器 

```docker kill CONTAINER_ID``` //CONTAINER_ID是容器ID

* 11.从这台机器上移除所有未运行的容器

```docker rm $(docker ps -a -q)```

* 12.显示这台机器上的所有镜像

```docker images -a```

* 13.从本机移除所有的镜像

```docker rmi $(docker images -q)```

* 14.使用Docker credentials 登录到CLI会话

```docker login```

* 15.给镜像打一个tag，为了可以上传到remote的registry

```docker tag <> username/repository:tag```

* 16.上传打完tag的镜像到remote的registry

```docker push username/repository:tag```

* 17.从registry运行指定的镜像

```docker run username/repository:tag```

如果你没有在这些命令中指定 ``` :tag ``` 部分，在你生成和运行镜像时，最新的tag ``` :latest ``` 会被默认使用。 
如果没有指定tag，Docker会使用最新的镜像版本，即latest（所以不指定会坑爹）

* 18.查看镜像的详细信息

```docker inspect IMAGE```

* 19.添加squid的证书

```sudo htpasswd -c squid_passwd  dreamylost```

* 20.push文件到docker的某个容器中

```sudo docker cp squid.conf  CONTAINER_ID:/etc/squid```

* 21.进入运行的容器

```sudo docker exec -it CONTAINER_ID /bin/bash``` //可能有镜像的多个实例，需要指定运行的容器ID，而不是镜像ID

* 22.查看docker的端口映射

```docker port  CONTAINER_ID```

* 23.给容器安装vim命令

1. ```apt-get update``` //同步 ```/etc/apt/sources.list``` 和 ```/etc/apt/sources.list.d```中列出的源的索引 
2. ```apt-get install vim``` //安装命令，其他类似（先登录进运行的容器，关闭容器则失效）

* 24.查看squid代理的链接访问日志

```tail -f /var/log/squid/access.log``` //根据版本可能是squid3

* 25.容器内的Linux安装Apache

```apt-get install apache2=2.4.29-1ubuntu4.5``` //后面是版本号


* 26.将本地目录挂载到docker容器上

```docker run -i -t -v /Users/xx:/etc/squid3 IMAGE /bin/bash``` 
//docker run -i -t -v 本地绝对路径:docker上绝对路径 镜像ID  /bin/bash

* 27.加端口映射

```docker run  -p 12345:3128 -i -t -v  /Users/xx:/etc/squid3 IMAGE /bin/bash```

* 28.拷贝docker的文件到本地

```docker cp CONTAINER_ID:/etc/squid3/squid.conf /Users/xx/xx```

* 29.本地文件拷贝到docker

docker cp 本地路径 容器ID:容器路径

* 30.使用本地配置文件启动squid代理

```docker run -p 3128:3128 -i -t -v /Users/xx:/etc/squid3 IMAGE```

* 31.进入正则运行的容器，并执行命令

```docker exec -it CONTAINER_ID /bin/bash-c "ls"``` //ls是需要执行的命令

* 32.推送到私服

```docker push 192.168.x.x:5000/docker-repo/squid:3.3.8-14```


* 33.docker的运行开启HTTP配置

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

* 34.查看私有库的镜像

```curl 192.168.x.x:5000/v2/_catalog```

* 35.搜索镜像

```docker search consul``` //consul 服务注册发现框架

* 36.一个已经终止的容器启动运行起来

```docker start```

* 37.提交容器修改

```docker commit CONTAINER_ID IMAGE``` //不提交下次启动就是新的，里面修改都没有

* 38.修改自己commit的容器tag

```docker tag IMAGE_ID userName/repositoryName:tag```


[最详细的 在Windows上 使用docker 搭建 consul 集群](https://blog.csdn.net/qq_34446485/article/details/90738092)

PS:镜像可以理解是已经打包的开放包，容器是包的一次执行，与运行时相关的一般都使用容器CONTAINER_ID
