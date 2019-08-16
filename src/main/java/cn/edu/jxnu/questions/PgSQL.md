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
  
``` 
\q（不需要分号结束）
``` 

* 退出数据库
  
```
exit 
```

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

* 查询是否锁表

```sql
select oid from pg_class where relname='可能锁表了的表'
select pid from pg_locks where relation='上面查出的oid'
```

* 释放锁定表
  
```sql
select pg_cancel_backend(pg_locks.pid) 
```

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

* 查询表上的索引

``` select * from pg_statio_all_indexes where relname='table_name' ```

多列索引

    官方
    https://www.postgresql.org/docs/9.6/indexes-multicolumn.html
    https://wiki.postgresql.org/wiki/9.1%E7%AC%AC%E5%8D%81%E4%B8%80%E7%AB%A0#.E5.A4.9A.E5.88.97.E7.B4.A2.E5.BC.95.28Multicolumn_Indexes.29
    
    pgsql 多列索引
    https://github.com/digoal/blog/blob/master/201702/20170205_01.md
    
    csdn
    https://blog.csdn.net/jubaoquan/article/details/78850899
    
    开源中国
    https://www.oschina.net/question/126398_22063

* json操作符

```
    1. -> 表示获取一个JSON数组元素，支持下标值(下标从0开始)、Key获取   
    得到 JSON对象:{"b":2}
    SELECT '[{"a":1},{"b":2},{"c":3}]'::JSON -> 1;
    继续使用，获取value:2
    SELECT '[{"a":1},{"b":2},{"c":3}]'::JSON -> 1 -> 'b';

    2. ->> 表示获取一个JSON对象字符串
    得到JSON字符串:{"b":2}，此时已是字符串类型无法直接继续使用 ->、->>
    SELECT '[{"a":1},{"b":2},{"c":3}]'::JSON ->> 1;

    3. #> 表示获取指定路径的一个JSON对象
    得到JSON对象:{"ba":"b1","bb":"b2"}
    SELECT '{"a":1,"b":{"ba":"b1","bb":"b2"},"c":3}'::JSON #> '{b}’
    继续获取，获取value:"b1"
    SELECT '{"a":1,"b":{"ba":"b1","bb":"b2"},"c":3}'::JSON #> '{b}' -> 'ba';

    4. #>>表示获取指定路径的一个JSON对象的字符串
    得到JSON字符串:{"ba":"b1","bb":"b2”}，无法继续使用 ->、->>
    SELECT '{"a":1,"b":{"ba":"b1","bb":"b2"},"c":3}'::JSON #>> '{b}'; —-这里{b}括号是必须的
```
::JSON 表示声明前面的字符串为一个JSON字符串对象，而且PgSQL中的JSON、JSONB对象 Key的声明必须是字符串
在获取一个JSON对象时，除非是JSON数组中的下标，必须要要用{}将JSON对象的Key包裹起来，否则会抛出异常