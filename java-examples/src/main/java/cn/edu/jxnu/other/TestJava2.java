package cn.edu.jxnu.other;

/**
 * 语法测试
 *
 * @author 梦境迷离
 * @time 2018-09-28
 */
public class TestJava2 {


    static String result = "";

    public static void main(String[] args) {

        test(1);
        System.out.println(result);
    }

    public static void test(int n) {
        try {
            if (n == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            result += "CATCH" + "\n";
        } finally {
            result += "FINALLY" + "\n";//有return 时，n=1输出FINALLY   n=0输出CATCH FINALLY
            // return;// 有return 时  result += "END" + "\n"; 报错
        }
        result += "END" + "\n";//无return时，n=0输出 CATCH FINALLY END，n=1输出 FINALLY END
    }

}
