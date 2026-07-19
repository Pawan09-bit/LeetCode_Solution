import java.util.*;

class Solution {
    public int minimumDifference(int[] nums) {
        int n = nums.length / 2;
        int total = 0;
        for (int x : nums) total += x;

        // Generate subset sums for left and right halves
        List<List<Integer>> left = generate(nums, 0, n);
        List<List<Integer>> right = generate(nums, n, nums.length);

        int ans = Integer.MAX_VALUE;

        // For each subset size k
        for (int k = 0; k <= n; k++) {
            List<Integer> L = left.get(k);
            List<Integer> R = right.get(n - k);
            Collections.sort(R);

            for (int x : L) {
                // target sum we want from R
                int target = total / 2 - x;
                int idx = Collections.binarySearch(R, target);
                if (idx < 0) idx = -idx - 1;

                // check candidate at idx
                if (idx < R.size()) {
                    int sum1 = x + R.get(idx);
                    int sum2 = total - sum1;
                    ans = Math.min(ans, Math.abs(sum1 - sum2));
                }
                // check candidate before idx
                if (idx > 0) {
                    int sum1 = x + R.get(idx - 1);
                    int sum2 = total - sum1;
                    ans = Math.min(ans, Math.abs(sum1 - sum2));
                }
            }
        }
        return ans;
    }

    // Generate all subset sums grouped by subset size
    private List<List<Integer>> generate(int[] nums, int start, int end) {
        int len = end - start;
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i <= len; i++) res.add(new ArrayList<>());

        for (int mask = 0; mask < (1 << len); mask++) {
            int sum = 0, bits = 0;
            for (int j = 0; j < len; j++) {
                if ((mask & (1 << j)) != 0) {
                    sum += nums[start + j];
                    bits++;
                }
            }
            res.get(bits).add(sum);
        }
        return res;
    }
}
