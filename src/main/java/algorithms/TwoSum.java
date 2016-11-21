package algorithms;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by yanghao-012 on 2016/11/3.
 *
 *
 */

/*
Given an array of integers, return indices of the two numbers such that they add up to a specific target.
You may assume that each input would have exactly one solution.

Example:
Given nums = [2, 7, 11, 15], target = 9,
Because nums[0] + nums[1] = 2 + 7 = 9,
return [0, 1].
*/

public class TwoSum {
    //time o(n^2)
    //space o(1)
    public static int[] twoSum(int[] nums, int target) {
        int sum;
        for(int i = 0;i<nums.length;i++){
            for(int j = i+1;j<nums.length-1;j++){
                sum = nums[i]+nums[j];
                if(sum==target){
                   /* return new int[]{nums[i],nums[j]};*/
                    return new int[]{i,j};
                }
            }

        }
        return null;
    }

    //time o(n)
    //space o(n)
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }


    public static void main(String args[]){
        Scanner input = new Scanner(System.in);
        int number;
        int[] nums = new int[5];
        int i = 0;
        System.out.println("请输入目标值：");
        int target = input.nextInt();
        System.out.println("请输入数组元素（输入999999关闭控制台）：");
        while (input.hasNext()){
            number = input.nextInt();
            if(number == 999999){
                input.close();
                break;
            }
            if(i>=nums.length){
                //扩容数组
                int[] newnums = new int[nums.length*2];
                System.arraycopy(nums,0,newnums,0,nums.length);
                nums = newnums;
            }
            nums[i] = number;
            i++;
        }
        nums = twoSum(nums,target);
        //控制台输出
        for(int j = 0;j<nums.length;j++){
            if(j==nums.length-1){
                System.out.print(","+nums[j]+" ]");
                break;
            }
            System.out.print("[ "+nums[j]);
        }
        System.exit(0);
    }
}
