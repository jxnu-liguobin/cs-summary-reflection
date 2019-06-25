PostgreSQL常用命令 
---
系统默认是（CentOS）

//后面是追加注释

* 安装

``` yum install postgresql*```  //版本

* 初始化数据库
  
``` postgresql-setup initdb```

* 设置为开机自启动
  
``` systemctl enable postgresql```  //需要权限

* 启动PostgreSQL

``` systemctl start postgresql``` 

* 进入数据库
  
``` su - postgres```  

* 创建角色
  
``` createuser admin（用户名）``` 

* 创建数据库实例

``` createdb -e -O admin（用户名） testdb（实例名）``` 

* 进入查询分析器

``` psql ``` 

* 设置密码
  
``` \password admin;（用户名，用分号结束）``` 

* 退出查询分析器
  
``` \q（不需要分号结束）``` 

* 退出数据库
  
```
exit 
````

* 修改监听
  
``` vim /var/lib/pgsql/data/postgresql.conf ```  //将这句注释打开

``` listen_addresses = '*' ``` //并修改

* 修改验证方式
  
``` vim /var/lib/pgsql/data/pg_hba.conf ```  //host  all  all  127.0.0.1/32（允许哪个IP访问，如果允许全部，则写成0.0.0.0/0）  md5（md5为密码验证）  

* 重启数据库
  
``` systemctl restart postgresql ``` 

* 使用密码登录数据库
  
``` psql -U admin（用户名） -d testdb（数据库） -h 127.0.0.1（登录哪个IP）``` 

* 创建数据库并指定所有者

``` createdb growing_test -O admin（用户名） -E UTF8 -e``` 

* 查看apps的所有表名 

``` select * from pg_tables where tableowner='apps' ```  //apps是用户

* 迁移数据库

dump数据库

``` pg_dump --file "/Users/userName/dump_backoup" --host "域名" --port "7531" --username "apps" testdb ```
          
    --file: dump文件生成到本地到文件地址
    --host: 要dump文件到服务器地址
    --port: 远程数据库的端口
    --username 数据库用户


使用dump文件

1.``` \i /Users/userName/dump_backoup ```  //需要先创建对应的数据库并登陆到pgsql
 
2.``` pg_restore -h localhost -p 5432 -U userName -W -d testdb -v "dump_backoup" ``` 

