package cn.edu.jxnu.examples.design.strategy;

import java.math.BigDecimal;

/**
 * 老人，2折
 * 
 * @author 梦境迷离.
 * @time 2018年6月13日
 * @version v1.0
 */
public class ElderMoreThen60YearsOld implements Strategy {

	/**
	 * 外界可以提供来的人的年龄
	 */
	private Integer age;

	/*
	 * 如果老人年龄需要在此判断则使用带2个参数的重载方法，而不是此方法
	 */
	@Override
	public BigDecimal strategyInterface(BigDecimal price) {
		// TODO Auto-generated method stub
		return price;
	}

	@Override
	public BigDecimal strategyInterface(BigDecimal price, boolean isCan) {
		if (isCan) {
			if (age > 60) {
				return price.multiply(new BigDecimal(0.2));
			} else {
				return this.strategyInterface(price);
			}
		}
		return this.strategyInterface(price);
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
