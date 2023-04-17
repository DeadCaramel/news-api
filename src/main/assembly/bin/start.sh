#!/bin/bash
cd `dirname $0`

HOST_ID=1

help() {
    echo "sh start.sh -h HOST_ID"
}

while getopts ":h:" opt; do
  case $opt in
    h)
      HOST_ID=$OPTARG
      ;;
    \?)
      help
      exit 1
      ;;
  esac
done


#当前路径
BIN_DIR=`pwd`

#向上一层路径
cd ..
DEPLOY_DIR=`pwd`
echo $DEPLOY_DIR

#配置文件路径
CONF_DIR=$DEPLOY_DIR/config
#日志输出路径
LOGS_DIR=$DEPLOY_DIR/logs

# 如果JDK环境变量没有写到全局要添加如下几行
# JAVA_HOME=/opt/java/jdk1.6.0_45
# PATH=$JAVA_HOME/bin:$PATH
# export JAVA_HOME
# export PATH

#如果logs目录不存在，就创建一个
if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi

#控制台日志输出收集位置
STDOUT_FILE=$LOGS_DIR/stdout.log

#APP_PID不为空，说明应用已启动，直接退出
if [ -n "$APP_PID" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $APP_PID"
    exit 1
fi



#首先将java版本号信息输出到标准输出，然后查找’64-bit’信息，目的就是判断jdk版本是否为64位

JAVA_MEM_OPTS="-Dfile.encoding=utf8"
BITS=`java -version 2>&1 | grep -i 64-bit`

#JVM启动基本参数，这里根据应用自行调整
JAVA_MEM_SIZE_OPTS="-Xmx1024m -Xms1024m -Xmn256m -XX:PermSize=128m -XX:MaxPermSize=256M -Xss512k"

#根据32位和64位配置不同的启动java垃圾回收参数，根据应用自行调整
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server $JAVA_MEM_SIZE_OPTS -Xmx1024m -Xms1024m -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server $JAVA_MEM_SIZE_OPTS -Xmx1024m -Xms1024m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

echo -e "Starting the $SERVER_NAME ...\c"
echo "启动参数：java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS"

#通过java命令启动服务，同时将其作为后台任务执行。
nohup java $JAVA_OPTS $JAVA_MEM_OPTS -jar ./boot/cloud-consult-rest-1.0.0-SNAPSHOT.jar &

#睡眠一下再检查应用是否启动，下面这里注释打开的话，就把最下面的那一段注释掉
#sleep 1
#APP_PID=`ps -f | grep java | grep "mposPlus-integral" |awk '{print $2}'`

#if [ -z "$APP_PID" ]; then
    #echo "START APP FAIL!"
    #echo "STDOUT: $STDOUT_FILE"
    #exit 1
#fi

#echo "START  SUCCESSED APP_PID: $APP_PID"
#echo "STDOUT: $STDOUT_FILE"

echo "OK!"
#APP_PID=`ps -ef -ww | grep "java" | grep mposPlus-integral2 | awk '{print $2}'`
APP_PID=$!
echo "START  SUCCESSED APP_PID: $APP_PID"
echo "STDOUT: $STDOUT_FILE"
echo $APP_PID > service.pid