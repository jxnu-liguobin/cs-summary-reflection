package io.github.dreamylost.practice;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，但不知道有几个数字是重复的。也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
 *     例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2。
 */
public class Duplicate {

    public static void main(String[] args) {
        int[] numbers = {2, 1, 3, 1, 4};
        int[] duplication = new int[1];
        boolean r = new Duplicate().duplicate(numbers, numbers.length, duplication);
        if (r) {
            System.out.println(duplication[0]);
        } else {
            System.out.println("不存在重复的数字！");
        }
    }

    // Parameters:
    // numbers: an array of integers
    // length: the length of array numbers
    // duplication: (Output) the duplicated number in the array number,length of
    // duplication array is 1,so using duplication[0] = ? in implementation;
    // Here duplication like pointor in C/C++, duplication[0] equal *duplication
    // in C/C++
    // 这里要特别注意~返回任意重复的一个，赋值duplication[0]
    // Return value: true if the input is valid, and there are some duplications
    // in the array number
    // otherwise false
    public boolean duplicate(int numbers[], int length, int[] duplication) {
        if (numbers == null || numbers.length < 1 || duplication.length < 1 || duplication == null)
            return false;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(length);
        for (int i = 0; i < length; i++) {
            if (map.containsKey(numbers[i])) {
                map.put(numbers[i], map.get(numbers[i]) + 1);
            } else {
                map.put(numbers[i], 1);
            }
        }
        for (int i : map.keySet()) {
            if (map.get(i) > 1) {
                duplication[0] = i;
                return true;
            }
        }
        return false;
    }

    public boolean duplicate2(int numbers[], int length, int[] duplication) {
        boolean[] k = new boolean[length];
        for (int i = 0; i < k.length; i++) {
            if (k[numbers[i]] == true) {
                duplication[0] = numbers[i];
                return true;
            }
            k[numbers[i]] = true;
        }
        return false;
    }
}
