/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.design.observer;

// 具体的观察者
class ConcreteObserver extends Observer {

    public ConcreteObserver(Subject subject) {
        this.subject = subject;
        this.subject.addObserver(this);
    }

    @Override
    public void update() {
        System.out.println("ConcreteObserver println hello");
    }
}
