class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[] present = new boolean[MAX];
        for (int x : nums) {
            present[x] = true;
        }

        boolean[] pairXor = new boolean[MAX];

        // All possible XORs of two present values
        for (int a = 0; a < MAX; a++) {
            if (!present[a]) continue;
            for (int b = 0; b < MAX; b++) {
                if (present[b]) {
                    pairXor[a ^ b] = true;
                }
            }
        }

        boolean[] ans = new boolean[MAX];

        // XOR the pair result with a third present value
        for (int x = 0; x < MAX; x++) {
            if (!pairXor[x]) continue;
            for (int c = 0; c < MAX; c++) {
                if (present[c]) {
                    ans[x ^ c] = true;
                }
            }
        }

        int count = 0;
        for (boolean v : ans) {
            if (v) count++;
        }

        return count;
    }
}