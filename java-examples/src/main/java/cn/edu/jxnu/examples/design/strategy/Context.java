package cn.edu.jxnu.examples.design.strategy;

import java.math.BigDecimal;

/** 策略环境 */
public class Context {

    // 持有一个具体策略的对象
    private Strategy strategy;

    /** 构造函数，传入一个具体策略对象 3 传入 具体策略对象 */
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    /** 策略方法 */
    public void contextInterface() {

        // 多态，调用实现了该接口的类的该方法
        // 传入原价
        strategy.strategyInterface(new BigDecimal(55), false);
    }
}
