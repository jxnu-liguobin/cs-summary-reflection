/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.design.adapter.object;

import cn.edu.jxnu.examples.design.adapter.Adaptee;

// 不再使用继承,使用关联关系，避免多继承
public class Adapter implements TargetInterface {
    Adaptee adaptee; // 直接关联被适配类

    public Adapter(Adaptee adaptee) { // / 可以通过构造函数传入具体需要适配的被适配类对象
        this.adaptee = adaptee;
    }

    public void standardApiForCurrentSystem() { // 这里是使用委托的方式完成特殊功能
        this.adaptee.specificApiForCurrentSystem();
    }
}
