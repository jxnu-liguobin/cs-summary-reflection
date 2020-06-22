/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.design;

/**
 * 适配器：将一个接口转换成客户希望的另一个接口，使原本由于不兼容的而不能一起工作的类，可以一起工作
 *
 * @author 梦境迷离
 * @version V1.0
 * @time. 2018年4月14日
 */
public class Adapter {
    // 1、适配者【adapter】派生自原有类
    // 2、适配者【adapter】包含一个需要兼容的类【受改造者adaptee】
    // 3、适配者将请求传给适配器【用户特殊类】实现

}

/** 基类 */
abstract class Shape {}

/** 派生类，仿佛Circle和Shape的派生类一样，不知道XXCircle的存在 */
class Circle extends Shape {

    /** 使用组合，包含特有的、不兼容、受改造者 */
    private XXCircle myXxCircle;

    /**
     * @version V1.0
     * @time. 下午4:23:58
     */
    public Circle() {
        // 实例化Circle对象时，必须实现化一个XXCircle对象
        // 将发送给Circle的请求，转给myXxCircle来处理
        myXxCircle = new XXCircle();
    }

    public void display() {
        myXxCircle.display();
    }
}

class XXCircle {
    // 客户需要使用的接口
    public void display() {}
}
