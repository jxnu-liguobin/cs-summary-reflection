#!/bin/bash

export  LANG=en_US.UTF-8
export LANG=zh_CN.UTF-8


# bash custom-git.sh "提交的注释"

starttime=`date +'%Y-%m-%d %H:%M:%S'`

branch=`git branch -a | grep "*" | awk '{print $2}'`
echo "当前分支：$branch"
echo "当前时间：$starttime"

scalaFile=`echo -e $(find . -name "*.scala" | wc -l) | awk '{a+=$1}END{print a}'`
javaFile=`echo -e $(find . -name "*.java" | wc -l) | awk '{a+=$1}END{print a}'`
mdFile=`echo -e $(find . -name "*.md" | wc -l) | awk '{a+=$1}END{print a}'`
echo "共有Scala文件：${scalaFile}，共有Java文件：${javaFile}，共有markdown文件：${mdFile}"


echo "==================更新文件列表====================="
echo `git status | grep "modified:" | awk '{print $2}'`

echo "==================新增文件列表====================="
echo `git status | grep "new file:" | awk '{print $2}'`

echo "==================删除文件列表====================="
echo `git status | grep "deleted:" | awk '{print $2}'`
echo "=================================================="

git add .
git commit -m "${1}，时间：$starttime"
git push origin master