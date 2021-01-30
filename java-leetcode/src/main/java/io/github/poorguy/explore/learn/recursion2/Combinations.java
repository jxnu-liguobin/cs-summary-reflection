package io.github.poorguy.explore.learn.recursion2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Combinations {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result=new ArrayList<>();
        List<Integer> nums=new ArrayList<>(n);
        for(int i=1;i<=n;i++){
            nums.add(i);
        }
        helper(nums,k,0,null,result);
        return result;
    }
    
    //to improve the performence, using array instead of list when remove and add in backtrace
    private void helper(List<Integer> nums,int k,int index,List<List<Integer>> middleResult,List<List<Integer>> result){
        for(int i=index;i<nums.size();i++){
            if(nums.size()-i+1<k){
                return;
            }
            int removed=nums.remove(i);
            if(middleResult==null){
                List<List<Integer>> newMiddleResult=new ArrayList<>();
                newMiddleResult.add(Arrays.asList(removed));
                if(k-1==0){
                    result.addAll(newMiddleResult);
                }else{
                    helper(nums,k-1,i,newMiddleResult,result);
                }
            }else{
                List<List<Integer>> newMiddleResult=new ArrayList<>(middleResult.size());
                for(List<Integer> list : middleResult){
                    List<Integer> newList=new ArrayList<>(list);
                    newList.add(removed);
                    newMiddleResult.add(newList);
                }
                 if(k-1==0){
                    result.addAll(newMiddleResult);
                }else{
                    helper(nums,k-1,i,newMiddleResult,result);
                }
            }
            nums.add(i,removed);
        }
    }
}