/* Licensed under Apache-2.0 @梦境迷离 */
package io.github.dreamylost.practice;

/**
 * @description 已知一个0到99999999之间的正整数，找到一个指定数字在该正整数中出现的次数。
 *     DigitOccurrence类的构造方法是findDigitOccurrence。该方法的输入应包含两个正整数， num1和num2，0<=num1<=9 and
 *     0<=num2<=99999999。 该方法应返回一个整数变量，变量值等于num1unm2中的出现次数。
 *     例如：如果num1=2，num2=123228，则此方法应返回3，即num1在num2中的出现次 数。 确保类和方法为public。不要从控制台接受任何输入。应将输入作为参数传递给该方法
 *     本身。 有用的命令： a%b返回a除以b的余数。
 * @author Mr.Li
 */
public class DigitOccurrence {

    public static void main(String[] args) {
        System.out.println("请分别输入num1 num2 ");
        // if (args.length < 2) {
        // System.out.println("参数错误，正在退出！");
        // System.exit(0);
        // }
        // int num1 = Integer.parseInt(args[0]);
        // int num2 = Integer.parseInt(args[1]);
        int result = new DigitOccurrence().findDigitOccurrence(2, 123228);
        System.out.println(result);
    }

    public int findDigitOccurrence(int num1, int num2) {

        String n1 = String.valueOf(num1);
        String[] n2 = String.valueOf(num2).split("");
        int count = 0;
        for (int i = 0; i < n2.length; i++) {
            if (n1.equals(n2[i])) {
                count++;
            } else {
                continue;
            }
        }
        return count;
    }
}
