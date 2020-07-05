/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.design.adapter;

public class Adapter extends Adaptee implements TargetInterface {

    @Override
    public void standardApiForCurrentSystem() {
        super.specificApiForCurrentSystem(); // 使用源类的特殊功能，加以包装
    }
    //// 适配器类，继承了被适配类，同时实现标准接口

}
