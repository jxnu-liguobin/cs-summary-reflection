package cn.edu.jxnu.examples.other;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author 梦境迷离
 * @time 2018-11-29
 */
public class JavaIntersectionList {
    public static void main(String[] args) {

        List<String> list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");

        List<String> list2 = new ArrayList();
        list2.add("3333");
        list2.add("4444");
        list2.add("5555");
        // 交集
        //List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(toList());
        //setA - (setA - setB) 需要实现equals ，暂时没有想到直接lambda filter不需要equals的实现方法
        list1.retainAll(list2);
        System.out.println("---得到交集 intersection---");
        list1.parallelStream().forEach(System.out::println);


        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(toList());
        System.out.println("---得到差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out::println);

        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(toList());
        System.out.println("---得到差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out::println);

        // 并集
        List<String> listAll = list1.parallelStream().collect(toList());
        List<String> listAll2 = list2.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        System.out.println("---得到并集 listAll---");
        listAll.parallelStream().forEach(System.out::println);

        // 去重并集
        List<String> listAllDistinct = listAll.stream().distinct().collect(toList());
        System.out.println("---得到去重并集 listAllDistinct---");
        listAllDistinct.parallelStream().forEach(System.out::println);

        System.out.println("---原来的List1---");
        list1.parallelStream().forEach(System.out::println);
        System.out.println("---原来的List2---");
        list2.parallelStream().forEach(System.out::println);
    }


}
