package com.kotovdv.hhschool.tropical.island.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.kotovdv.hhschool.tropical.island.model.Island;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Dmitriy Kotov
 */
public class IslandFactory {

    public List<Island> create(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);

        int numberOfIslands = scanner.nextInt();
        List<Island> islands = new ArrayList<>(numberOfIslands);

        for (int i = 0; i < numberOfIslands; i++) {
            short n = scanner.nextShort();
            short m = scanner.nextShort();

            List<Integer> islandDefinition = new ArrayList<>();
            for (int j = 0; j < n * m; j++) {
                islandDefinition.add(scanner.nextInt());
            }

            islands.add(createIsland(n, m, islandDefinition));
        }

        return islands;
    }

    private Island createIsland(int n, int m, List<Integer> values) {
        Table<Integer, Integer, Integer> islandTable = HashBasedTable.create();

        islandTable.putAll(createSeashore(n, m));

        for (int i = 1, k = 0; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                islandTable.put(i, j, values.get(k++));
            }
        }

        return new Island(islandTable);
    }

    private Table<? extends Integer, ? extends Integer, ? extends Integer> createSeashore(int n, int m) {
        Table<Integer, Integer, Integer> seashoreTable = HashBasedTable.create();

        for (int row : new int[]{0, n + 1}) {
            for (int j = 0; j <= m + 1; j++) {
                seashoreTable.put(row, j, 0);
            }
        }

        for (int column : new int[]{0, m + 1}) {
            for (int j = 0; j <= n + 1; j++) {
                seashoreTable.put(j, column, 0);
            }
        }

        return seashoreTable;
    }

}
