/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.design.observer;

import java.util.Vector;

// 被观察者
abstract class Subject {
    // 定义一个观察者数组（顺序 重复性？）
    private Vector<Observer> obsVector = new Vector<Observer>();

    // 增加一个观察者
    public void addObserver(Observer o) {
        this.obsVector.add(o);
    }

    // 删除一个观察者
    public void delObserver(Observer o) {
        this.obsVector.remove(o);
    }

    // 通知所有观察者
    public void notifyObservers() {
        for (Observer o : this.obsVector) {
            o.update();
        }
    }
}
