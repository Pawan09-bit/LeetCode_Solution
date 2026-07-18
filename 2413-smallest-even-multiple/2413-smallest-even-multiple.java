class Solution {
    public int smallestEvenMultiple(int n) {
        int ans=1;
        for(int i=1; i<=n*2;i++){
            if(i%2==0 && i%n==0){
                ans =i;
                break;
            }
        }
        return ans;
    }
}