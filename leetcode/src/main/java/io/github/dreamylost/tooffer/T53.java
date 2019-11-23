package io.github.dreamylost.tooffer;

/**
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 */
public class T53 {

    /**
     * 12e说明e的后面必须有数字，不能有两个e
     * +-5说明符号位要么出现一次在首位，要么出现一次在e的后一位，其他地方都不能有
     * 12e4.3说明e的后面不能有小数，1.2.3说明不能有两个小数点
     * 1a3.14说明不能有其他的非法字符，比如这里的a
     */
    public boolean isNumeric(char[] str) {
        String s = String.valueOf(str);
        return s.matches("[+-]?[0-9]*(\\.[0-9]*)?([eE][+-]?[0-9]+)?");
    }


    //剑指offer
    private int index = 0;

    public boolean isNumeric2(char[] str) {
        if (str.length < 1)
            return false;
        boolean flag = scanInteger(str);
        if (index < str.length && str[index] == '.') {
            index++;
            flag = scanUnsignedInteger(str) || flag;
        }
        if (index < str.length && (str[index] == 'E' || str[index] == 'e')) {
            index++;
            flag = flag && scanInteger(str);
        }
        return flag && index == str.length;
    }

    private boolean scanInteger(char[] str) {
        if (index < str.length && (str[index] == '+' || str[index] == '-'))
            index++;
        return scanUnsignedInteger(str);
    }

    private boolean scanUnsignedInteger(char[] str) {
        int start = index;
        while (index < str.length && str[index] >= '0' && str[index] <= '9')
            index++;
        return start < index; //是否存在整数
    }
}
