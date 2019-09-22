#!/bin/sh


commitComment=$1

starttime=`date +'%Y-%m-%d %H:%M:%S'`


branch=`git branch -a | grep "*" | awk '{print $2}'`
echo "当前分支：$branch"
echo "当前时间：$starttime"


scalaFile=`echo -e $(find . -name "*.scala" | wc -l) | awk '{a+=$1}END{print a}'`
echo "当前总共有Scala文件：$scalaFile"
javaFile=`echo -e $(find . -name "*.java" | wc -l) | awk '{a+=$1}END{print a}'`
echo "当前总共有Java文件：$scalaFile"
mdFile=`echo -e $(find . -name "*.md" | wc -l) | awk '{a+=$1}END{print a}'`
echo "当前总共有markdown文件：$scalaFile"

#显示修改
modifyJavaFile=`git status | grep "modified:" | grep ".java" | wc -l | awk '{print $1}'`
echo "当前共修改Java文件数：$modifyJavaFile"
modifyScalaFile=`git status | grep "modified:" | grep ".scala" | grep ".java"| wc -l | awk '{print $1}'`
echo "当前共修改Scala文件数：$modifyJavaFile"
modifyMarkFile=`git status | grep "modified:" | grep ".md" | grep ".java"| wc -l | awk '{print $1}'`
echo "当前共修改markdown文件数：$modifyMarkFile"
#新增的不需显示
newFile=`git status | grep "new file:" | wc -l | awk '{print $1}'`
echo "当前共新增文件数：$newFile"

delJavaFile=`git status | grep "deleted:" | grep ".java" | grep ".java"| wc -l | awk '{print $1}'`
echo "当前共删除Java文件数：$delJavaFile"
delScalaFile=`git status | grep "deleted:" | grep ".scala" | grep ".java"| wc -l | awk '{print $1}'`
echo "当前共删除Scala文件数：$delScalaFile"
delMarkFile=`git status | grep "deleted:" | grep ".md" | grep ".java"| wc -l | awk '{print $1}'`
echo "当前共删除markdown文件数：$delMarkFile"

git commit -m "$commitComment，时间：$starttime"
git push origin master

# shellcheck disable=SC1073
endtime=`date + '%Y-%m-%d %H:%M:%S'`
start_seconds=$(date --date="$starttime" +%s);
end_seconds=$(date --date="$endtime" +%s);
echo "本次花费时间： "$((end_seconds-start_seconds))"s"