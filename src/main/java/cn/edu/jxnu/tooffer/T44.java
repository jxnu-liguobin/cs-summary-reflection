package cn.edu.jxnu.tooffer;

/**
 * 牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，
 * 写些句子在本子上。同事Cat对Fish写的内容颇感兴趣，有一天他向Fish借来翻看，
 * 但却读不懂它的意思。例如，“student. a am I”。后来才意识到，
 * 这家伙原来把句子单词的顺序翻转了，正确的句子应该是“I am a student.”。
 * Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？
 */
public class T44 {

    public String ReverseSentence(String str) {
        if (str == null) {
            return null;
        }
        if (str.trim().equals("")) {
            return str;
        }
        String[] strings = str.split(" ");
        StringBuffer s = new StringBuffer();
        for (int i = strings.length - 1; i >= 0; i--) {
            s.append(strings[i]).append(" ");
        }
        return s.substring(0, s.length() - 1);
    }

    /**
     * 算法思想：先翻转整个句子，然后，依次翻转每个单词。
     * 依据空格来确定单词的起始和终止位置
     */
    public String ReverseSentence2(String str) {
        char[] chars = str.toCharArray();
        reverse(chars, 0, chars.length - 1);
        int blank = -1;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                int nextBlank = i;
                reverse(chars, blank + 1, nextBlank - 1);
                blank = nextBlank;
            }
        }
        reverse(chars, blank + 1, chars.length - 1);//最后一个单词单独进行反转
        return new String(chars);

    }

    public void reverse(char[] chars, int low, int high) {
        while (low < high) {
            char temp = chars[low];
            chars[low] = chars[high];
            chars[high] = temp;
            low++;
            high--;
        }
    }
}
