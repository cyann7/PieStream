#!/bin/bash

# 设置环境变量和输出目录
. ./env

#echo  $JAVA_CMD
OUT_DIR="out/processed_time"
mkdir -p $OUT_DIR



TIMESTAMP=$(date +"%m%d%H%M")  # 获取当前的月日时分
OUT_FILE="$OUT_DIR/processTime_$TIMESTAMP.out"  # 文件名中包含时间戳

#OUT_FILE=$OUT_DIR/full.txt
echo > $OUT_FILE  # 清空文件

# 定义参数范围
#cols=( 4 6 8 10 12 14 16 18 20 22 24 26 28 30 )

cols=(  6  8 10 )
# 4 6 8 10
#limits=( 1000 5000 10000 50000 100000 500000 1000000 5000000 10000000 )

limits=(     1000000   )
windSize=(    10000 )

#data_dir="/home/uzi/Code/TPSdata/"

# 写入表头
echo -n "col,limit,windSize" >> $OUT_FILE
echo ",output" >> $OUT_FILE

# 循环参数并调用 Java 程序
for col in "${cols[@]}"
do
    for limit in "${limits[@]}"
    do
        for third in "${windSize[@]}"
        do
            # 构建执行的 Java 命令
            EXEC="$JAVA_CMD  org.piestream.evaluation.ProcessedTime $col $limit $third $DATA_DIR"
            
            # 输出到文件
            echo -n "$col,$limit,$third" >> $OUT_FILE
            echo -n "," >> $OUT_FILE
            
            # 执行命令并将结果写入文件
            $EXEC >> $OUT_FILE
            
            # 输出每次执行的结果
            echo "Executed: $EXEC \n"
        done
    done
done

#java  -Xms12g -Xmx12g  -cp "target/classes:lib/*" org.piestream.evaluation.ProcessedTime 4 10000000 100000 /home/uzi/Code/TPSdata/

