package cn.edu.jxnu.designpattern.strategy;

import java.math.BigDecimal;

/**
 * 学生，半价，需要学生证
 * 
 * @author 梦境迷离.
 * @time 2018年6月13日
 * @version v1.0
 */
public class Student implements Strategy {

	private Boolean hasIDCard;

	@Override
	public BigDecimal strategyInterface(BigDecimal price) {
		// TODO Auto-generated method stub
		return price;
	}

	@Override
	public BigDecimal strategyInterface(BigDecimal price, boolean isCan) {
		if (isCan) {
			// 需要验证
			if (hasIDCard) {
				// 有学生证
				return price.multiply(new BigDecimal(0.5));
			} else {
				return this.strategyInterface(price);
			}
		}
		return this.strategyInterface(price);
	}

	public Boolean getHasIDCard() {
		return hasIDCard;
	}

	public void setHasIDCard(Boolean hasIDCard) {
		this.hasIDCard = hasIDCard;
	}

}