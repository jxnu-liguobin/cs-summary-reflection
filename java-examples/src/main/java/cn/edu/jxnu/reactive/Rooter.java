package cn.edu.jxnu.reactive;

/**
 * Java中引用透明性的示例
 * <p>
 * 符合引用透明性的方法调用，将表达式替换为其求值后的结果，不会对程序的执行产生影响。
 * 若函数修改了内部状态，一般都是非引用透明的，而下列这种特殊情况却是引用透明的，因为其实际并无副作用。
 *
 * @author 梦境迷离
 * @version v1.0
 * @since 2019-12-07
 */
public class Rooter {

    private final double value;
    private Double root = null;//缓存计算值的可变字段，注意：引用类型不一样，引用不变但是引用的内容可能被改变时也要小心

    public Rooter(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    //一旦value有值，只会直接反回，不会重复计算
    public double getRoot() {
        //可变性不可能从外部被观察到
        if (root == null) {
            root = Math.sqrt(value);
        }
        return root;
    }
}
