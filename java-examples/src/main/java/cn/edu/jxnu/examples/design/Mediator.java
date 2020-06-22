/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.design;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 *
 * @description:中介者：定义：用一个中介对象封装一系列的对象交互，中介者使各对象不需要显示地相互作用，从而使其耦合松散，而且可以独立地改变它们之间的交互。 @Package:
 *     cn.jxnu.edu.designpattern
 * @author: 梦境迷离
 * @date: 2018年3月20日 上午10:35:46
 */
/**
 * ps：使用同事类注入而不使用抽象注入的原因是因为抽象类中不具有每个同事类必须要完成的方法。即每个同事类中的方法各不相同。
 *
 * <p>问：为什么同事类要使用构造函数注入中介者，而中介者使用getter/setter方式注入同事类呢？ 这是因为同事类必须有中介者，而中介者却可以只有部分同事类。
 *
 * <p>使用场景： 中介者模式适用于多个对象之间紧密耦合的情况，紧密耦合的标准是：在类图中出现了蜘蛛网状结构，即每个类都与其他的类有直接的联系。
 */
public abstract class Mediator {
    // 定义同事类
    protected ConcreteColleague1 c1;
    protected ConcreteColleague2 c2;

    // 通过getter/setter方法把同事类注入进来
    public ConcreteColleague1 getC1() {
        return c1;
    }

    public void setC1(ConcreteColleague1 c1) {
        this.c1 = c1;
    }

    public ConcreteColleague2 getC2() {
        return c2;
    }

    public void setC2(ConcreteColleague2 c2) {
        this.c2 = c2;
    }

    // 中介者模式的业务逻辑
    public abstract void doSomething1();

    public abstract void doSomething2();
}

class ConcreteColleague2 {}

class ConcreteColleague1 {}
