/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.design.observer;

public class ObserverPatternSpec {

    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();

        new ConcreteObserver(subject);
        new Concrete2Observer(subject);

        //实际应该是subject的状态变更时再通知观察者们，这里省略了状态变更步骤
        subject.notifyObservers();
    }
}
