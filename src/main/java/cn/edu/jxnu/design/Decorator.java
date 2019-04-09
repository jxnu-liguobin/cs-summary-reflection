package cn.edu.jxnu.design;

/**   
 * Copyright © 2018 梦境迷离. All rights reserved.
 * 
 * @description:装饰器：定义:动态地给一个对象添加一些额外的职责。就增加功能来说，装饰模式相比生成子类更为灵活。
 * @Package: cn.jxnu.edu.designpattern 
 * @author: 梦境迷离   
 * @date: 2018年3月20日 上午10:43:28 
 */
/**
 * ● Component抽象构件 Component是一个接口或者是抽象类，就是定义我们最核心的对象，也就是最原始的对象，如上面的成绩单。
 * 注意：在装饰模式中，必然有一个最基本、最核心、最原始的接口或抽象类充当Component抽象构件。 
 * ● ConcreteComponent 具体构件ConcreteComponent是最核心、最原始、最基本的接口或抽象类的实现，你要装饰的就是它。 
 * ● Decorator装饰角色一般是一个抽象类，做什么用呢？实现接口或者抽象方法，它里面可不一定有抽象的方法呀，
 * 在它的属性里必然有一个private变量指向Component抽象构件。
 * ● 具体装饰角色
 * ConcreteDecoratorA和ConcreteDecoratorB是两个具体的装饰类，你要把你最核心的、最原始的、最基本的东西装饰成其他东西，
 * 上面的例子就是把一个比较平庸的成绩单装饰成家长认可的成绩单。 使用场景：
 * ● 需要扩展一个类的功能，或给一个类增加附加功能。
 * ●需要动态地给一个对象增加功能，这些功能可以再动态地撤销。 
 * ● 需要为一批的兄弟类进行改装或加装功能，当然是首选装饰模式。
 *
 */
public class Decorator {

}
