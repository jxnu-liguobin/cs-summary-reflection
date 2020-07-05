/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.design;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 *
 * @description:代理：定义：为其他对象提供一种代理以控制对这个对象的访问。 @Package: cn.jxnu.edu.designpattern
 * @author: 梦境迷离
 * @date: 2018年3月20日 上午10:19:49
 */
/**
 * ● Subject抽象主题角色 抽象主题类可以是抽象类也可以是接口，是一个最普通的业务类型定义，无特殊要求。 ● RealSubject具体主题角色
 * 也叫做被委托角色、被代理角色。它才是冤大头，是业务逻辑的具体执行者。 ● Proxy代理主题角色
 * 也叫做委托类、代理类。它负责对真实角色的应用，把所有抽象主题类定义的方法限制委托给真实主题角色实现，并且在真实主题角色处理完毕前后做预处理和善后处理工作。 普通代理和强制代理：
 * 普通代理就是我们要知道代理的存在，也就是类似的GamePlayerProxy这个类的存在，然后才能访问；
 * 强制代理则是调用者直接调用真实角色，而不用关心代理是否存在，其代理的产生是由真实角色决定的。 普通代理：
 * 在该模式下，调用者只知代理而不用知道真实的角色是谁，屏蔽了真实角色的变更对高层模块的影响，真实的主题角色想怎么修改就怎么修改，对高层次的模块没有任何的影响，只要你实现了接口所对应的方法，该模式非常适合对扩展性要求较高的场合。
 * 强制代理：
 * 强制代理的概念就是要从真实角色查找到代理角色，不允许直接访问真实角色。高层模块只要调用getProxy就可以访问真实角色的所有方法，它根本就不需要产生一个代理出来，代理的管理已经由真实角色自己完成。
 * 动态代理： 根据被代理的接口生成所有的方法，也就是说给定一个接口，动态代理会宣称“我已经实现该接口下的所有方法了”。
 * 两条独立发展的线路。动态代理实现代理的职责，业务逻辑Subject实现相关的逻辑功能，两者之间没有必然的相互耦合的关系。通知Advice从另一个切面切入，最终在高层模块也就是Client进行耦合，完成逻辑的封装任务。
 * 动态代理调用过程示意图： 动态代理的意图：横切面编程，在不改变我们已有代码结构的情况下增强或控制对象的行为。 首要条件：被代理的类必须要实现一个接口。【动态代理可以通过CGlib】
 * 解决问题：防止直接访问目标对象给系统带来的不必要复杂性。
 */
public class Proxy implements Subject {

    @Override
    public void buyMac() {
        // 引用并创建真实对象实例，即”我“
        RealSubject realSubject = new RealSubject();

        // 调用真实对象的方法，进行代理购买Mac
        realSubject.buyMac();
        // 代理对象额外做的操作
        this.WrapMac();
    }

    private void WrapMac() {
        System.out.println("用盒子包装好Mac");
    }
}

/** 由此也可以看出被代理必须要实现了接口 */
interface Subject {
    public void buyMac();
}

class RealSubject implements Subject {
    @Override
    public void buyMac() {
        System.out.println("买一台Mac");
    }
}
// 调用
// Subject proxy = new Proxy();
// proxy.buyMac();
