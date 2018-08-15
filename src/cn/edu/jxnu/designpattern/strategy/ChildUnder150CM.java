package cn.edu.jxnu.designpattern.strategy;

import java.math.BigDecimal;

/**
 * 150cm以下的儿童，5折 100cm以下免费
 * 
 * @author 梦境迷离.
 * @time 2018年6月13日
 * @version v1.0
 */
public class ChildUnder150CM implements Strategy {

	/**
	 * 外界可以提供身高
	 */
	private Integer height;

	/*
	 * 如果学生的学生证需要在此判断，则使用带2个参数的重载方法
	 * 
	 * 默认实现原价购票，也可以在此直接使用条件判断购票，boolean是可选的，其他同
	 * 
	 * @see cn.edu.jxnu.designpattern.strategy.Strategy#strategyInterface(double)
	 */
	@Override
	public BigDecimal strategyInterface(BigDecimal price) {
		return price;

	}

	@Override
	public BigDecimal strategyInterface(BigDecimal price, boolean isCan) {

		if (isCan) { // 可以优惠
			if (height < 100) {
				return new BigDecimal(0);
			} else if (height > 100 && height < 150) {
				return price.multiply(new BigDecimal(0.5));
			} else {
				this.strategyInterface(price);
			}
		} else {
			this.strategyInterface(price);
		}
		return this.strategyInterface(price);
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

}