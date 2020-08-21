#!/bin/bash

# TODO, generate this shell automatically
echo "Order by Join time" > countByAuthors.md

echo "####Scala" >> countByAuthors.md
echo "dreamylost:=> "`find  ./scala-leetcode/src/main/scala/io/github/dreamylost -name "Leetcode*.scala" | wc -l | awk '{print $1}'` >> countByAuthors.md
echo "" >> countByAuthosr.md
echo "sweeneycai:=> "`find  ./scala-leetcode/src/main/scala/io/github/sweeneycai -name "Leetcode*.scala" | wc -l | awk '{print $1}'` >> countByAuthors.md
echo  "" >> countByAuthors.md


echo "####Java (match *.Java)" >> countByAuthors.md
echo "dreamylost:=> "`find  ./java-leetcode/src/main/java/io/github/dreamylost -name "*.java" | wc -l | awk '{print $1}'` >> countByAuthors.md
echo  "" >> countByAuthors.md
echo "ccccmaster:=> "`find  ./java-leetcode/src/main/java/io/github/ccccmaster -name "*.java" | wc -l | awk '{print $1}'` >> countByAuthors.md
echo "" >> countByAuthors.md
echo "poorguy:=> "`find  ./java-leetcode/src/main/java/io/github/poorguy -name "*.java" | wc -l | awk '{print $1}'` >> countByAuthors.md
echo  "" >> countByAuthors.md
echo "wkk:=> "`find  ./java-leetcode/src/main/java/io/github/wkk -name "*.java" | wc -l | awk '{print $1}'` >> countByAuthors.md
echo  "" >> countByAuthors.md


echo "####Rust" >> countByAuthors.md
dreamylostLeetcode=`find  ./rust-leetcode/src -name "leetcode*.rs" | wc -l | awk '{print $1}'`
dreamylostInterview=`find  ./rust-leetcode/src -name "interview*.rs" | wc -l | awk '{print $1}'`
echo "dreamylost:=> "$(($dreamylostLeetcode + $dreamylostInterview)) >> countByAuthors.md
echo  "" >> countByAuthors.md



echo "####Kotlin" >> countByAuthors.md
echo "dreamylost:=> "`find  ./kotlin-leetcode/src/main/kotlin/io/github/dreamylost -name "Leetcode*.kt" | wc -l | awk '{print $1}'` >> countByAuthors.md
echo  "" >> countByAuthors.md


echo "####Python" >> countByAuthors.md
echo "laozhang:=> "`find  ./python-leetcode/laozhang -name "leetcode*.py" | wc -l | awk '{print $1}'` >> countByAuthors.md
echo "" >> countByAuthors.md

## append