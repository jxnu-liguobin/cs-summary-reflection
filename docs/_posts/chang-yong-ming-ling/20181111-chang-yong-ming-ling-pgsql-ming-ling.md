---
title: pgsql常用命令
categories:
  - 常用命令
---

# 2018-11-11-常用命令-pgsql命令

系统默认是（CentOS或Mac）

//后面是追加注释

* 安装

> yum install postgresql\* //版本

* 初始化数据库

> initdb /usr/local/var/postgres //Mac上使用brew安装，/usr/local/var/postgres是安装目录，下同

* 启动数据库

> pg\_ctl -D /usr/local/var/postgres -l logfile start

* 查看数据库状态

> pg\_ctl -D /usr/local/var/postgres -l /usr/local/var/postgres/server.log status

* 停止数据库

> pg\_ctl -D /usr/local/var/postgres -l /usr/local/var/postgres/server.log stop -s -m fast
>
> ps -ef \| grep postgres 或 ps auxwww \| grep postgres $$//查看进程

* 设置为开机自启动

> systemctl enable postgresql //需要权限

* 启动PostgreSQL

> systemctl start postgresql

* 进入数据库

> su - postgres

* 创建角色

> createuser admin（用户名）

* 创建数据库实例

> createdb -e -O admin（用户名） testdb（实例名）

* 进入查询分析器

> psql

* 设置密码

> \password admin;（用户名，用分号结束）

* 退出查询分析器

> \q（不需要分号结束）

* 退出数据库

> exit

* 修改监听

> vim /var/lib/pgsql/data/postgresql.conf //将这句注释打开
>
> listen\_addresses = '\*' //并修改

* 修改验证方式

> vim /var/lib/pgsql/data/pg\_hba.conf //host all all 127.0.0.1/32（允许哪个IP访问，如果允许全部，则写成0.0.0.0/0） md5（md5为密码验证）

* 重启数据库

> systemctl restart postgresql

* 使用密码登录数据库

> psql -U admin（用户名） -d testdb（数据库） -h 127.0.0.1（登录哪个IP）

* 创建数据库并指定所有者

> createdb growing\_test -O admin（用户名） -E UTF8 -e

* 查看apps的所有表名 

> select \* from pg\_tables where tableowner='apps' //apps是用户

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

> pg\_dump --file "/Users/userName/dump\_backoup" --host "域名" --port "7531" --username "apps" testdb

```text
--file: dump文件生成到本地到文件地址
--host: 要dump文件到服务器地址
--port: 远程数据库的端口
--username 数据库用户
```

使用dump文件

> 1. \i /Users/userName/dump\_backoup   //需要先创建对应的数据库并登陆到pgsql
> 2. pg\_restore -h localhost -p 5432 -U userName -W -d testdb -v "dump\_backoup"

* 查询表上的索引

```sql
select * from pg_statio_all_indexes where relname='table_name'
```

多列索引

```text
官方
https://www.postgresql.org/docs/9.6/indexes-multicolumn.html
https://wiki.postgresql.org/wiki/9.1%E7%AC%AC%E5%8D%81%E4%B8%80%E7%AB%A0#.E5.A4.9A.E5.88.97.E7.B4.A2.E5.BC.95.28Multicolumn_Indexes.29

pgsql 多列索引
https://github.com/digoal/blog/blob/master/201702/20170205_01.md

csdn
https://blog.csdn.net/jubaoquan/article/details/78850899

开源中国
https://www.oschina.net/question/126398_22063
```

* Json操作符

```sql
    -- 1. -> 表示获取一个JSON数组元素，支持下标值(下标从0开始)、Key获取   
    -- 得到 JSON对象:{"b":2}
    SELECT '[{"a":1},{"b":2},{"c":3}]'::JSON -> 1;
    -- 继续使用，获取value:2
    SELECT '[{"a":1},{"b":2},{"c":3}]'::JSON -> 1 -> 'b';

    -- 2. ->> 表示获取一个JSON对象字符串
    -- 得到JSON字符串:{"b":2}，此时已是字符串类型无法直接继续使用 ->、->>
    SELECT '[{"a":1},{"b":2},{"c":3}]'::JSON ->> 1;

    -- 3. #> 表示获取指定路径的一个JSON对象
    -- 得到JSON对象:{"ba":"b1","bb":"b2"}
    SELECT '{"a":1,"b":{"ba":"b1","bb":"b2"},"c":3}'::JSON #> '{b}’
    -- 继续获取，获取value:"b1"
    SELECT '{"a":1,"b":{"ba":"b1","bb":"b2"},"c":3}'::JSON #> '{b}' -> 'ba';

    -- 4. #>>表示获取指定路径的一个JSON对象的字符串
    -- 得到JSON字符串:{"ba":"b1","bb":"b2”}，无法继续使用 ->、->>
    SELECT '{"a":1,"b":{"ba":"b1","bb":"b2"},"c":3}'::JSON #>> '{b}'; —-这里{b}括号是必须的
```

::JSON 表示声明前面的字符串为一个JSON字符串对象，而且PgSQL中的JSON、JSONB对象 Key的声明必须是字符串 在获取一个JSON对象时，除非是JSON数组中的下标，必须要要用{}将JSON对象的Key包裹起来，否则会抛出异常

* 计算在每分钟内插入数据的数量

```sql
SELECT tmp.count, tmp.date
FROM (
         SELECT count(id) count, date_trunc('minute', created_at) date
         FROM ads_tracking_campaigns
         WHERE creator_id = 0
         GROUP BY date
         HAVING count(id) > 1
     ) tmp
GROUP BY date, tmp.count
ORDER BY tmp.count DESC
LIMIT 1;
```

主要是抹掉时间中的秒，并以时间进行分组

* Hstore基本使用

最近用的的记录。

```sql
-- 根据key查询  存在key=_gio的数据
SELECT * FROM ads_tracking WHERE ios_params ? '_gio' OR android_params ? '_gio';

-- 查询一条数据，指定条件 k=_gio & value !='非'
SELECT * FROM ads_tracking WHERE ios_params -> '_gio' != '非';

-- 仅查询_gio字段值，指定条件 k=_gio  & value !=''
SELECT ios_params -> '_gio' as _gio FROM ads_tracking WHERE ios_params -> '_gio' != '';

-- 插入
INSERT INTO books (title, attr)
VALUES
   (
      'PostgreSQL Tutorial',
      '"paperback" => "243",
      "publisher" => "postgresqltutorial.com",
      "language"  => "English",
      "ISBN-13"   => "978-1449370000",
       "weight"   => "11.2 ounces"'
   );

-- 向已有Hstore记录中，新增一对kv

UPDATE ads_tracking
SET ios_params = ios_params || '"freeshipping"=>"测试"' :: hstore WHERE ios_params ? '_gio';

-- 最终ios_params值新增freeshipping => 测试：
-- _gio => 非,
-- freeshipping => 测试

-- 从已有Hstore记录中删除指定key
UPDATE ads_tracking
SET ios_params = delete(ios_params, 'freeshipping') WHERE ios_params ? '_gio';

-- 检查hstore列中特定key-value对
SELECT ios_params -> '_gio' as _gio FROM ads_tracking WHERE ios_params @> '"_gio" => "非"' :: hstore;

-- 查询包含多个特定key的记录(同时含有两个key)
SELECT * FROM ads_tracking WHERE android_params ?& ARRAY['_gio', 'gio'];

-- 返回Hstore的所有key
SELECT akeys(android_params) FROM ads_tracking WHERE android_params ?& ARRAY['_gio', 'gio'];

-- 返回Hstore的所有value
SELECT avals(android_params) FROM ads_tracking WHERE android_params ?& ARRAY['_gio', 'gio'];

-- 转换hstore为json
SELECT hstore_to_json(android_params) FROM ads_tracking WHERE android_params ?& ARRAY['_gio', 'gio'];

-- 转换hstore为集合
SELECT each(android_params) FROM ads_tracking WHERE android_params ?& ARRAY['_gio', 'gio'];
```

* 获取系统中秒级的时间

```sql
select floor(extract(epoch from(current_timestamp - timestamp '1970-01-01 00:00:00')));

-- 毫秒
select floor(extract(epoch from((current_timestamp - timestamp '1970-01-01 00:00:00')*1000)));
```

* 分析索引使用情况和查询时间

```sql
 explain (analyze,verbose,timing,costs,buffers)(
    SELECT * FROM ads_tracking_campaigns WHERE project_id = 35076 AND name IN ('PPSZ00014282/iOS','36663/iOS','100562/iOS')
    )
```

