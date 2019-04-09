package cn.edu.jxnu.design.strategy;

import java.math.BigDecimal;

/**
 * 通用逻辑接口
 * 
 * 有必要时，可以对接口进行抽象进一步分层实现
 * 
 * @author 梦境迷离.
 * @time 2018年6月13日
 * @version v1.0
 */
public interface Strategy {

	/**
	 * 定义买票流程 策略方法 价格使用BigDecimal定义 此方法在子类中默认实现为原价购票
	 */
	public BigDecimal strategyInterface(BigDecimal price);

	/**
	 * 是否符合条件，默认使用该方法，即需要提供验证
	 * 
	 * isCan 是否需要在本接口中判断来的人是否需要购票要求验证
	 *
	 */
	public BigDecimal strategyInterface(BigDecimal price, boolean isCan);

}