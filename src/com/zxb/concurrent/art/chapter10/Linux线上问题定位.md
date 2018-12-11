top 查看每个进程的情况

jstat 查看GC情况
jstat -gcutil pid 1000 5

dump 查看线程代码
sudo -u admin /opt/java/bin/jstack pid > /home/dump1

printf dump的线程ID是十六进制，top看的是十进制，可以用printf格式化
printf "%x\n" pid 

netstat 命令查询有多少台机器连接到这个端口上
netstat -nat | grep 3306 -c

ps -eLf | grep java -c 命令可以查看Java线程池数

查看网络流量：
cat /proc/net/dev 

查看系统平均负载：
cat /proc/loadavg

查看系统内存情况：
cat /proc/meminfo

查看CPU的利用率：
cat /proc/stat