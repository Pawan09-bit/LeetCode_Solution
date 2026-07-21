class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int n = s.length();
        int totalOnes = 0;
        int i = 0;
        
        int prevZeroBlock = Integer.MIN_VALUE; // Sentinel to indicate no previous zero block yet
        int maxZeroGain = 0;
        
        while (i < n) {
            int j = i;
            // Group contiguous matching characters
            while (j < n && s.charAt(j) == s.charAt(i)) {
                j++;
            }
            int blockSize = j - i;
            
            if (s.charAt(i) == '1') {
                totalOnes += blockSize;
            } else {
                // If we have seen a previous '0' block, they are separated by a real block of '1's!
                if (prevZeroBlock != Integer.MIN_VALUE) {
                    maxZeroGain = Math.max(maxZeroGain, prevZeroBlock + blockSize);
                }
                prevZeroBlock = blockSize;
            }
            
            i = j; // Move to the next segment
        }
        
        return totalOnes + maxZeroGain;
    }
}
