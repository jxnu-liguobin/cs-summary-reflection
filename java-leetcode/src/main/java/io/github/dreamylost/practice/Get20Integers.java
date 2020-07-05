/* All Contributors (C) 2020 */
package io.github.dreamylost.practice;

import java.util.Comparator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * @description 随机生成20个不重复的小写字母并进行排序，排序方式为倒序。 97~122
 * @author Mr.Li
 */
public class Get20Integers {

    public static void main(String[] args) {
        Set<Character> set = new TreeSet<Character>(new MyComparator());
        while (set.size() < (char) 20) {
            int a = new Random().nextInt(26) + 97;
            set.add((char) a);
        }
        for (char c : set) {
            System.out.print(c);
        }
    }
}

class MyComparator implements Comparator<Character> {

    @Override
    public int compare(Character o1, Character o2) {
        if (o1 > o2) return -1;
        else if (o1 < o2) {
            return 1;
        } else {
            return 0;
        }
    }
}
