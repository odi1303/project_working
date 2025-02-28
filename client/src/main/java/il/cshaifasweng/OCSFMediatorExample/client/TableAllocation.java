package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

public class TableAllocation {
    ArrayList<Timeframe> timeframes;

}

class Timeframe {
    int[][] free_tables = {{0,0,0}, {0,0,0}};
    int[] coins = {2,3,4};

    Optional<int[]> optimal_allocation(int ppl, int inside) {
        int[] available = Arrays.copyOf(free_tables[inside], 3);

        int max_capacity = IntStream.range(0, 3).map(i -> available[i] * coins[i]).sum();
        if (ppl > max_capacity) return Optional.empty();
        if (ppl == max_capacity) return Optional.of(available);

        int total_tables = Arrays.stream(available).sum();
        int[] tables = new int[total_tables];
        for (int i = 0, j = 0; j < 3; ++j) for (int k = available[j]; k > 0; --k) {
            tables[i++] = coins[j];
        }
        long[][] dp = new long[total_tables + 1][ppl + 1];
        int[][] opt = new int[total_tables + 1][ppl + 1];
        Arrays.stream(opt).forEach(t -> Arrays.fill(t, -1));
        Arrays.fill(dp[0], Integer.MAX_VALUE);
        for (int c = 1; c <= total_tables; ++c) {
            int table = tables[c - 1];
            for (int r = 1; r <= ppl; ++r) {
                if (table == r) {
                    dp[c][r] = 1;
                    opt[c][r] = table;
                } else if (table > r) dp[c][r] = dp[c - 1][r];
                else if (dp[c - 1][r] < dp[c - 1][r - table] + 1) dp[c][r] = dp[c - 1][r];
                else {
                    dp[c][r] = dp[c - 1][r - table] + 1;
                    opt[c][r] = table;
                }
            }
        }
        if (dp[total_tables][ppl] == Integer.MAX_VALUE) { return Optional.empty(); }
        // reconstruct optimal allocation from dp table
        int[] optimal = new int[3];
        for (int j = ppl, i = total_tables; i > 0; --i) if (opt[i][j] != -1) {
            ++optimal[opt[i][j] - 2];
            j -= opt[i][j];
        }
        return Optional.of(optimal);
    }
    public static void main(String[] args) {
        Timeframe timeframe = new Timeframe();
        timeframe.free_tables[1] = new int[]{3,2,2};
        timeframe.optimal_allocation(10, 1).ifPresentOrElse(arr -> Arrays.stream(arr).forEach(System.out::println), () -> System.out.println("damn"));
        System.out.println(timeframe.alloc(11, 1));
        System.out.println(Arrays.toString(timeframe.free_tables[1]));
        System.out.println(timeframe.alloc(9, 1));
        System.out.println(Arrays.toString(timeframe.free_tables[1]));
    }
    boolean can_alloc(int ppl, int inside) {
        return optimal_allocation(ppl, inside).isPresent();
    }
    boolean alloc(int ppl, int inside) {
        return optimal_allocation(ppl, inside)
                .map(a -> free_tables[inside] =
                        IntStream.range(0, 3)
                                .map(i -> free_tables[inside][i] - a[i])
                                .toArray())
                .isPresent();
    }
}