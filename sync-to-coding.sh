#!/bin/bash
# sync GitHub to Coding for jekyll blog
export  LANG=en_US.UTF-8
export LANG=zh_CN.UTF-8

mygithub="git@github.com:jxnu-liguobin/cs-summary-reflection.git"
mycoding="git@e.coding.net:scala-chat/coding_upgrade_date_IxzKa/cs-summary-reflection.git"
project="cs-summary-reflection"
#open ! shell
shopt -s  extglob
starttime=`date +'%Y-%m-%d %H:%M:%S'`
echo "start at "$starttime
if [ ! -d "./tmp" ];then
	echo "在`pwd`/tmp下新建镜像库"
	mkdir ./tmp
	cd ./tmp
	git clone $mygithub
	cd $project
	git remote remove origin
	git remote add origin $mycoding
	git rm -rf --cached !(docs)
	rm -rf !(docs)
	cp -r docs/ ./
	git add .
	git rm -rf --cached docs
	git commit -m "upload only docs' files"
	git push origin master --force
else
	echo "镜像库已经存在"
	cd ./tmp
	cd $project
	git remote remove origin
	git remote add origin $mygithub
	git pull origin master
	git remote remove origin
	git remote add origin $mycoding
	git rm -rf --cached !(docs)
	rm -rf !(docs)
	cp -r docs/ ./
    git add .
	git rm -rf --cached docs
	git commit -m "upload only docs' files"
	git push origin master --force
fi
endtime=`date +'%Y-%m-%d %H:%M:%S'`
echo "end at "endtime
#rm -rf ../../tmp

