#!/bin/bash


suffix=".md"
root_path=`pwd`
path=$root_path/docs/_posts/
for category in $(ls $path)
do
    echo -e "## $category\n"
    sub_path=$path$category
    cd $sub_path
    for article in $(ls ./)
    do
    	article_name=(${article//-/ })
    	art=${article_name[4]}
    	echo -e "[${art/$suffix/}](./docs/_posts/${category}/${article})\n"
    done
done