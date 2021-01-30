package io.github.poorguy.explore.learn.recursion2;

import java.util.Arrays;

class SortAnArray {
    public int[] sortArray(int[] nums) {
        return mergeSort(nums);
    }
    
    private int[] mergeSort(int[] nums){
        
        if(nums==null||nums.length==0||nums.length==1){
            return nums;
        }else if(nums.length==2){
            if(nums[0]<=nums[1]){
                return nums;
            }else{
                return new int[]{nums[1],nums[0]};
            }
        }else{
            int[] left=mergeSort(Arrays.copyOfRange(nums,0,nums.length/2));
            int[] right=mergeSort(Arrays.copyOfRange(nums,nums.length/2,nums.length));
            int l=0;
            int r=0;
            for(int i=0;i<nums.length;i++){
                if(l==left.length&&r<right.length){
                    nums[i]=right[r];
                    r++;
                }else if(r==right.length&&l<left.length){
                    nums[i]=left[l];
                    l++;
                }else{
                    if(left[l]<right[r]){
                        nums[i]=left[l];
                        l++;
                    }else{
                        nums[i]=right[r];
                        r++;
                    }
                }
            }
            return nums;
        }
    }
}