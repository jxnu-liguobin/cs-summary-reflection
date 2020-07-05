/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.design.visitor;

import java.util.List;

/**
 * 分别创建访问者和节点元素对象，调用访问者访问变量节点元素
 *
 * <p>（1）Visitor：接口或者抽象类，它定义了对每一个元素（Element）访问的行为，它的参数就是可以访问的元素，它的方法数理论上来讲与元素个数是一样的
 * 因此，访问者模式要求元素的类族要稳定，如果经常添加、移除元素类，必然会导致频繁地修改Visitor接口，如果这样则不适合使用访问者模式。
 *
 * <p>（2）ConcreteVisitor1、ConcreteVisitor2：具体的访问类，它需要给出对每一个元素类访问时所产生的具体行为。
 *
 * <p>（3）Element：元素接口或者抽象类，它定义了一个接受访问者的方法（Accept），其意义是指每一个元素都要可以被访问者访问。
 *
 * <p>（4）ConcreteElementA、ConcreteElementB：具体的元素类，它提供接受访问方法的具体实现，而这个具体的实现，通常情况下是使用访问者提供的访问该元素类的方法。
 *
 * <p>（5）ObjectStructure：定义当中所说的对象结构，对象结构是一个抽象表述，它内部管理了元素集合，并且可以迭代这些元素供访问者访问。
 *
 * @author 梦境迷离
 */
public class ClientTest {

    public static void main(String[] args) {
        List<ElementNode> list = ObjectStruture.getList(); // 所有可以访问的节点元素--------稳定的数据结构，不同的操作
        for (ElementNode elementNode : list) {
            elementNode.accept(new ConcreteVisitor()); // 使用访问者
        }
    }
}
