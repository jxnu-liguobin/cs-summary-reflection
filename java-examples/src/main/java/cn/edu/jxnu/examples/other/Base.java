package cn.edu.jxnu.examples.other;

public class Base implements Cloneable {

    @Override
    public Object clone() {
        Base o = null;
        try {
            o = (Base) super.clone();
            System.out.println("正在调用基类的拷贝"); // 1
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        Base base = new Children();
        // Object object = base.clone();// 打印1,2
        Children c1 = new Children();
        @SuppressWarnings("unused")
        Children c2 = new Children();
        c2 = c1;
    }
}

class Children extends Base {

    @Override
    public Object clone() {
        Children o = null;
        o = (Children) super.clone();
        System.out.println("正在调用子类的拷贝"); // 2
        return o;
    }
}
