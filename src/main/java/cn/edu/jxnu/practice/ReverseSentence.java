package cn.edu.jxnu.practice;

/**
 * @description 牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。同事Cat对Fish写的内容颇感兴趣，
 *              有一天他向Fish借来翻看，但却读不懂它的意思。例如，“student. a am
 *              I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，正确的句子应该是“I am a
 *              student.”。Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？
 * @author Mr.Li
 *
 */
public class ReverseSentence {
	public static void main(String[] args) {
		String string = new ReverseSentence().reverseSentence("student. a am I");
		System.out.println(string);
	}

	/**
	 * 
	 * @description  对每个单词进行逆转  再对整体进行一次逆转即可。
	 * @param str
	 * @return
	 */
	public String reverseSentence(String str) {
		if (str == null) {
			return null;
		}
		if (str.trim().equals("")) {
			return str;
		}
		String[] strings = str.split(" ");
		StringBuilder stringBuilder = new StringBuilder();
		char[] c = null;
		for (int i = 0; i < strings.length; i++) {
			c = strings[i].toCharArray();
			reverseArray(c, 0, c.length - 1);
			stringBuilder.append(c);
			stringBuilder.append(" ");
		}
		char s[] = stringBuilder.toString().toCharArray();
		reverseArray(s, 0, s.length - 1);
		return String.valueOf(s).substring(1);

	}

	/**
	 * @description 移位
	 * @param array
	 * @param b
	 * @param e
	 */
	private static void reverseArray(char[] array, int b, int e) {
		for (; b < e; b++, e--) {
			char temp = array[e];
			array[e] = array[b];
			array[b] = temp;

		}
	}
}