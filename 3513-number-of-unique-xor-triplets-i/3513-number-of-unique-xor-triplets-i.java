class Solution {
    public int uniqueXorTriplets(int[] nums) {
       
        int n = nums.length;
        if (n < 3) {
            return n;
        }
        // Find the smallest power of 2 that is strictly greater than n
        int ans = 1;
        while (ans <= n) {
            ans <<= 1; // Multiply by 2 using bitwise shift
        }
        
        return ans;
        
    }
}