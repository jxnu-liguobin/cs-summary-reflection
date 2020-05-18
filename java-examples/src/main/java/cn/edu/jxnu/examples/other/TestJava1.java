package cn.edu.jxnu.examples.other;

/**
 * 语法测试
 *
 * @author 梦境迷离
 * @time 2018-09-28
 */
public class TestJava1 {

    public static void main(String[] args) {
        BB B = new BB();
        // 输出
        // static A
        // static B
        // A
        // B
    }
}

class AA {
    static {
        System.out.println("static A");
    }

    AA() {
        System.out.println("A");
    }
}

class BB extends AA {

    static {
        System.out.println("static B");
    }

    BB() {
        System.out.println("B");
    }
}
