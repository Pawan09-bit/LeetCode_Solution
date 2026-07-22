import java.util.*;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();

        // ---- Run-length encoding ----
        List<Integer> startsList = new ArrayList<>();
        List<Integer> endsList = new ArrayList<>();
        List<Integer> typesList = new ArrayList<>();

        int i = 0;
        while (i < n) {
            int j = i;
            while (j < n && s.charAt(j) == s.charAt(i)) j++;
            startsList.add(i);
            endsList.add(j - 1);
            typesList.add(s.charAt(i) == '1' ? 1 : 0);
            i = j;
        }

        int m = startsList.size();
        int[] starts = new int[m];
        int[] ends = new int[m];
        int[] types = new int[m];
        int[] runLen = new int[m];
        for (int k = 0; k < m; k++) {
            starts[k] = startsList.get(k);
            ends[k] = endsList.get(k);
            types[k] = typesList.get(k);
            runLen[k] = ends[k] - starts[k] + 1;
        }

        int[] posToRun = new int[n];
        for (int k = 0; k < m; k++) {
            for (int p = starts[k]; p <= ends[k]; p++) {
                posToRun[p] = k;
            }
        }

        int totalOnes = 0;
        for (int k = 0; k < n; k++) {
            if (s.charAt(k) == '1') totalOnes++;
        }

        final int NEG = Integer.MIN_VALUE / 2;
        int[] V = new int[m];
        Arrays.fill(V, NEG);
        for (int idx = 1; idx < m - 1; idx++) {
            if (types[idx] == 1) {
                V[idx] = runLen[idx - 1] + runLen[idx + 1];
            }
        }

        // ---- Sparse table for range max ----
        int[] LOG = new int[m + 1];
        for (int idx = 2; idx <= m; idx++) {
            LOG[idx] = LOG[idx / 2] + 1;
        }

        int maxLevel = (m > 0 ? LOG[m] : 0) + 1;
        int[][] sparse = new int[maxLevel][];
        sparse[0] = V.clone();
        int level = 1;
        while ((1 << level) <= m) {
            int half = 1 << (level - 1);
            int length = 1 << level;
            int size = m - length + 1;
            sparse[level] = new int[size];
            for (int idx = 0; idx < size; idx++) {
                sparse[level][idx] = Math.max(sparse[level - 1][idx], sparse[level - 1][idx + half]);
            }
            level++;
        }

        List<Integer> ans = new ArrayList<>();
        for (int[] q : queries) {
            int l = q[0], r = q[1];
            int a = posToRun[l];
            int b = posToRun[r];

            if (a == b || b == a + 1) {
                ans.add(totalOnes);
                continue;
            }

            int best = NEG;
            int count = b - a - 1;

            if (count == 1) {
                int idx = a + 1;
                if (types[idx] == 1) {
                    int L = ends[a] - l + 1;
                    int R = r - starts[b] + 1;
                    best = L + R;
                }
            } else {
                int idx = a + 1;
                if (types[idx] == 1) {
                    int L = ends[a] - l + 1;
                    int R = runLen[a + 2];
                    best = Math.max(best, L + R);
                }
                idx = b - 1;
                if (types[idx] == 1) {
                    int R = r - starts[b] + 1;
                    int L = runLen[b - 2];
                    best = Math.max(best, L + R);
                }
                if (a + 2 <= b - 2) {
                    int mid = queryMax(sparse, LOG, a + 2, b - 2, NEG);
                    best = Math.max(best, mid);
                }
            }

            int gain = Math.max(best, 0);
            ans.add(totalOnes + gain);
        }

        return ans;
    }

    private int queryMax(int[][] sparse, int[] LOG, int l, int r, int NEG) {
        if (l > r) return NEG;
        int k = LOG[r - l + 1];
        return Math.max(sparse[k][l], sparse[k][r - (1 << k) + 1]);
    }
}