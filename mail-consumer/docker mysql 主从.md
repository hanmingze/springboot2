http://blog.csdn.net/sunlihuo/article/details/54018843

1)从Docker官方下拉MySQL的image 
打开https://hub.docker.com/ 
搜索mysql 
这里写图片描述
在docker中运行 默认tag为latest 
docker pull mysql/mysql-server 
也可以指定mysql版本 
docker pull mysql/mysql-server:5.7

2)设置目录 
为了使MySql的数据保持在宿主机上，我们先建立几个目录。 
mkdir -pv /mysql/data 
建立主服务器的配置目录 
mkdir -pv /mysql/101 
建立从服务器的配置目录 
mkdir -pv /mysql/102

3)设置主从服务器配置 
vi /mysql/101/101.cnf

[mysqld]
log-bin=mysql-bin
server-id=101

vi /mysql/102/102.cnf

[mysqld]
log-bin=mysql-bin
server-id=102

3)创建主从服务器容器

docker create --name mysqlsrv101 -v /home/docker/mysql/data/mysql101:/var/lib/mysql -v /home/docker/mysql/101:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 mysql:5.7
docker create --name mysqlsrv102 -v /home/docker/mysql/data/mysql102:/var/lib/mysql -v /home/docker/mysql/102:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=123456 -p 3316:3306 mysql:5.7


启动容器 
docker start mysqlsrv101 
docker start mysqlsrv102 
这里写图片描述

4)登录主服务器的mysql，查询master的状态 
这里写图片描述

主库创建用户

SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
GRANT REPLICATION SLAVE ON *.* to 'backup'@'%' identified by '123456';


5)登录从服务器的mysql，设置与主服务器相关的配置参数

SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
change master to master_host='192.168.56.101',master_user='backup',master_password='123456',master_log_file='mysql-bin.000003',master_log_pos=2500;

master_host为docker的地址不能写127.0.0.1
master_user是在主库创建的用户
master_log_pos是主库show master status;查询出的Position

启动服务

start slave;

查看服务状态

show slave status;

这里写图片描述
Waiting for master to send event 就是成功了 
Connecting to master 多半是连接不通

之后主库的修改都能同步到从库了