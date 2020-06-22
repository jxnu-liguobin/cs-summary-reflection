/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.design.visitor;

/** 具体的元素类，它提供接受访问方法的具体实现，而这个具体的实现，通常情况下是使用访问者提供的访问该元素类的方法 */
public class ConcreteElement2 implements ElementNode {

    @Override
    public void doSomeThings() {
        System.out.println("this is an element2........");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
