package cn.edu.jxnu.design.strategy;

import java.math.BigDecimal;

/**
 * 超过三次，8折
 * 
 * @author 梦境迷离.
 * @time 2018年6月13日
 * @version v1.0
 */
public class PeopleComeTimeMoreThenThree implements Strategy {

	/**
	 * 外界可以提供来的次数
	 */
	private Integer time = 0;

	/*
	 * 不需要判断是否有三次，则使用此方法
	 */
	@Override
	public BigDecimal strategyInterface(BigDecimal price) {
		return price;
	}

	@Override
	public BigDecimal strategyInterface(BigDecimal price, boolean isCan) {

		if (isCan) {
			if (time > 3) {
				return price.multiply(new BigDecimal(0.8));
			} else {
				return this.strategyInterface(price);
			}
		}
		return this.strategyInterface(price);
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

}
