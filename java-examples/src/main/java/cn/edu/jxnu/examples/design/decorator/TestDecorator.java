/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.design.decorator;

public class TestDecorator {

    public static void main(String[] args) {
        Component inst = new ConcreteComponent(); // 实例化一个具体组件
        inst.methodA(); // 使用原本的功能
        System.out.println("装饰后的功能。。。。。");
        // 仍是Component，所以接口没有改变
        Component instDecorator = new ConcreteDecorator(inst); // 经过装饰之后的功能
        instDecorator.methodA();
    }
}
