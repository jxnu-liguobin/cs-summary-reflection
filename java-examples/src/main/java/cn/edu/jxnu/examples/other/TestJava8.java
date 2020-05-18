package cn.edu.jxnu.examples.other;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据注释得知：
 *
 * <p>1，该方法返回的是父list的一个视图，从fromIndex（包含），到toIndex（不包含）。fromIndex=toIndex 表示子list为空
 *
 * <p>2，父子list做的非结构性修改（non-structural
 * changes）都会影响到彼此：所谓的“非结构性修改”，是指不涉及到list的大小改变的修改。相反，结构性修改，指改变了list大小的修改。
 *
 * <p>3，对于结构性修改，子list的所有操作都会反映到父list上。但父list的修改将会导致返回的子list失效。
 *
 * <p>4，tips：如何删除list中的某段数据： list.subList(from, to).clear();
 *
 * @author 梦境迷离
 * @time 2018-08-15
 */
public class TestJava8 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("a");

        // 使用构造器创建一个包含list的列表list1
        List<String> list1 = new ArrayList<String>(list);
        // 使用subList生成与list相同的列表list2
        List<String> list2 = list.subList(0, list.size());
        list2.add("b");

        /** 可以发现，list2为list的子list，当list2发生结构性修改（list2.add("b")）后，list也发生相应改变，所以返回结果为false和true */
        System.out.println(list.equals(list1));
        System.out.println(list.equals(list2));
    }
}
