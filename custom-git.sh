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

gitStatus=`git status`

#显示修改
modifyJavaFile=`echo $gitStatus | grep "modified:" |  grep -G "\.java$" | wc -l | awk '{print $1}'`
modifyScalaFile=`echo $gitStatus | grep "modified:" | grep -G "\.scala$" |  wc -l | awk '{print $1}'`
modifyMarkFile=`echo $gitStatus | grep "modified:" | grep -G "\.md$" | wc -l | awk '{print $1}'`
echo "本次共修改Java文件数：${modifyJavaFile}，本次共修改Scala文件数：${modifyScalaFile}，本次共修改markdown文件数：${modifyMarkFile}"


echo "更新文件列表："
echo $gitStatus | grep "modified:" | awk '{print $2}'

delJavaFile=`echo $gitStatus | grep "deleted:" | grep -G "\.java$" |  wc -l | awk '{print $1}'`
delScalaFile=`echo $gitStatus | grep "deleted:" | grep -G "\.scala$" | wc -l | awk '{print $1}'`
delMarkFile=`echo $gitStatus | grep "deleted:" | grep -G "\.md$" |  wc -l | awk '{print $1}'`
echo "本次共删除Java文件数：${delJavaFile}，本次共删除Scala文件数：${delScalaFile}，本次共删除markdown文件数：${delMarkFile}"

#新增的不需显示
newFile=`echo $gitStatus | grep "new file:" | wc -l | awk '{print $1}'`
echo "本次共新增文件数：${newFile}"

echo "新增文件列表："
echo $gitStatus | grep "new file::" | awk '{print $2}'

git add .
git commit -m "${1}，时间：$starttime"
git push origin master