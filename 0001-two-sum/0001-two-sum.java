class Solution {
    public int[] twoSum(int[] nums, int target) {
    
        ArrayList<Integer> list=new ArrayList<>();
        int arr[]=new int[2];
        int ans=0;
        for(int i=0 ;i<nums.length;i++){
            for(int j=i+1; j<nums.length;j++){
                ans=nums[i]+nums[j];
                if(target ==ans){
                    list.add(i);
                    list.add(j);
                    arr[0] =i;
                    arr[1]=j;
                }
            }
        }
        // int arr[]=list.stream().mapToInt(i -> i).toArray();
        return    arr;
    }
}