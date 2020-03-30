#!/bin/bash
# 本脚本用来同步GitHub与Coding
export  LANG=en_US.UTF-8
export LANG=zh_CN.UTF-8

starttime=`date +'%Y-%m-%d %H:%M:%S'`
echo "start at "$starttime

if [ ! -d "./tmp" ];then
	echo "在`pwd`/tmp下新建镜像库"
	mkdir ./tmp
	cd ./tmp
	# --bare 创建的克隆版本库都不包含工作区，直接就是版本库的内容，这样的版本库称为裸版本库
	git clone --bare git@github.com:jxnu-liguobin/cs-summary-reflection.git
	cd cs-summary-reflection.git
	git push --mirror git@e.coding.net:scala-chat/coding_upgrade_date_IxzKa/cs-summary-reflection.git
else
	echo "镜像库已经存在"
	cd ./tmp
	cd cs-summary-reflection.git
	git fetch git@github.com:jxnu-liguobin/cs-summary-reflection.git +refs/heads/*:refs/heads/*
	git push --mirror git@e.coding.net:scala-chat/coding_upgrade_date_IxzKa/cs-summary-reflection.git
fi
endtime=`date +'%Y-%m-%d %H:%M:%S'`
echo "end at "endtime
#rm -rf ../../tmp

