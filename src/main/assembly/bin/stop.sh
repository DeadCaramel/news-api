#!/bin/bash
cd `dirname $0`

#当前路径
BIN_DIR=`pwd`

#向上一层路径
cd ..
DEPLOY_DIR=`pwd`
echo $DEPLOY_DIR

APP_PID=$(cat service.pid)

#APP_PID不为空，说明应用已启动，直接退出
if [ -n "$APP_PID" ]; then
   kill $APP_PID
  
    COUNT=0
    while [ $COUNT -lt 1 ]; do    
        echo -e ".\c"   
        sleep 1
        if [ -n "$SERVER_PROTOCOL_PORT" ]; then
            if [ "$SERVER_PROTOCOL_NAME" == "dubbo" ]; then
                COUNT=`echo status | nc -i 1 127.0.0.1 $SERVER_PROTOCOL_PORT | grep -c OK`
            else
                COUNT=`netstat -an | grep $SERVER_PROTOCOL_PORT | wc -l`
            fi
        else
            COUNT=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}' | wc -l`
        fi
        if [ $COUNT -eq 0 ]; then
            break
        fi
    done
    echo "OK!"
    echo "STOP SUCCESSED APP_PID: $APP_PID"
fi
