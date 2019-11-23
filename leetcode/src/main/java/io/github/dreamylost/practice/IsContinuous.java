package io.github.dreamylost.practice;

/**
 * @description LL今天心情特别好,因为他去买了一副扑克牌,发现里面居然有2个大王,2个小王(一副牌原本是54张^_^)...
 *              他随机从中抽出了5张牌,想测测自己的手气,看看能不能抽到顺子,如果抽到的话,他决定去买体育彩票,嘿嘿！！“红心A,黑桃3,小王,
 *              大王,方片5”,“Oh My God!”不是顺子.....LL不高兴了,他想了想,决定大\小
 *              王可以看成任何数字,并且A看作1,J为11,Q为12,K为13。
 *              上面的5张牌就可以变成“1,2,3,4,5”(大小王分别看作2和4),“So Lucky!”。LL决定去买体育彩票啦。
 *              现在,要求你使用这幅牌模拟上面的过程,然后告诉我们LL的运气如何。为了方便起见,你可以认为大小王是0。
 * @author Mr.Li
 *
 */
public class IsContinuous {
	/**
	 * @description 必须满足两个条件 1. 除0外没有重复的数 
	 * 						   2. max - min < 5
	 * @param numbers
	 * @return
	 */
	public boolean isContinuous(int[] numbers) {

		if (numbers == null || numbers.length < 1)
			return false;
		java.util.Arrays.sort(numbers);
		int numzero = 0;
		for (int i = 0; i < numbers.length; i++) {
			if (numbers[i] == 0)
				numzero++;
			else
				break;
		}
		/**
		 * 1、排序 
		 * 2、计算所有相邻数字间隔总数 
		 * 3、计算0的个数 
		 * 4、如果2、3相等，就是顺子 
		 * 5、如果出现对子，则不是顺子
		 */
		int gap = 0;
		for (int i = numzero; i < numbers.length - 1; i++) {
			if (numbers[i + 1] - numbers[i] == 0) {
				return false;
			}
			gap += numbers[i + 1] - numbers[i] - 1;
		}
		if (numzero >= gap)
			return true;
		else
			return false;
	}
}