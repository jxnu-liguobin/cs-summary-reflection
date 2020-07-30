package io.github.wkk.everyday.junly;

/**
 * @Time: 2020/7/30上午8:52
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class IntegerBreak_343 {
    public int integerBreak(int n) {
        int division = 3;
        if(n <=division){
            if(n <= 2){
                return 1;
            }else {
                return 2;
            }
        }
        if(n%division == 0){
            return (int)Math.pow(division, n/division);
        }else if(n%division == 1){
            return (int)Math.pow(division, n/division - 1) * 4;
        }else {
            return (int)Math.pow(division, n/division) * 2;
        }
    }
}
