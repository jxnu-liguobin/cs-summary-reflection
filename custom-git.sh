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
echo "本次总共有Scala文件：$scalaFile，本次总共有Java文件：$javaFile，本次总共有markdown文件：$mdFile"

#显示修改
modifyJavaFile=`git status | grep "modified:" |  grep -G "\.java$" | wc -l | awk '{print $1}'`
modifyScalaFile=`git status | grep "modified:" | grep -G "\.scala$" | grep ".java"| wc -l | awk '{print $1}'`
modifyMarkFile=`git status | grep "modified:" | grep -G "\.md$" | grep ".java"| wc -l | awk '{print $1}'`
echo "本次共修改Java文件数：$modifyJavaFile，本次共修改Scala文件数：$modifyScalaFile，本次共修改markdown文件数：$modifyMarkFile"
delJavaFile=`git status | grep "deleted:" | grep -G "\.java$" | grep ".java"| wc -l | awk '{print $1}'`
delScalaFile=`git status | grep "deleted:" | grep "\.scala$" | grep ".java"| wc -l | awk '{print $1}'`
delMarkFile=`git status | grep "deleted:" | grep "\.md$" | grep ".java"| wc -l | awk '{print $1}'`
echo "本次共删除Java文件数：$delJavaFile，本次共删除Scala文件数：$delScalaFile，本次共删除markdown文件数：$delMarkFile"

#新增的不需显示
newFile=`git status | grep "new file:" | wc -l | awk '{print $1}'`
echo "本次共新增文件数：$newFile"

git add .
git commit -m "${1}，时间：$starttime"
git push origin master