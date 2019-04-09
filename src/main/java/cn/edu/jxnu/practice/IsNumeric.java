package cn.edu.jxnu.practice;

/**
 * @description 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100","5e2","-123",
 *              "3.1416"和"-1E-16"都表示数值。
 *              但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是
 * @author Mr.Li 
 * 1、对字符串中的每个字符进行判断分析 
 * 2、e（E）后面只能接数字，并且不能出现2次
 * 3、对于+、-号，只能出现在第一个字符或者是e的后一位 
 * 4、对于小数点，不能出现2次，e后面不能出现小数点
 */
public class IsNumeric {
	public boolean isNumeric(char[] str) {
		boolean sign = false;
		boolean decimal = false;
		boolean hasE = false;
		// ①对字符串中的每个字符进行判断分析
		for (int i = 0; i < str.length; i++) {
			if (str[i] == 'e' || str[i] == 'E') {
				if (i == str.length - 1)
					return false;
				if (hasE)
					return false;
				hasE = true; // 开始记录E
			} else if (str[i] == '+' || str[i] == '-') {
				// 有符号，且符号前面不是E
				if (sign && str[i - 1] != 'e' && str[i - 1] != 'E')
					return false;
				// 前面无符号,符号在中间，且不是e后面
				if (!sign && i > 0 && str[i - 1] != 'e' && str[i - 1] != 'E')
					return false;
				sign = true;
			} else if (str[i] == '.') {
				// 小数不能出现2次 且不能在e后面
				if (hasE || decimal)
					return false;
				decimal = true; // 开始记录小数点
			} else if (str[i] < '0' || str[i] > '9') {
				return false;
			}
		}
		return true;
	}

	/**
	 * @description  正则
	 * @param str
	 * @return
	 */
	public boolean isNumeric2(char[] str) {
		String s = new String(str);
		return s.matches("[\\+-]?[0-9]*(\\.[0-9]*)?([eE][\\+-]?[0-9]+)?");
	}
}