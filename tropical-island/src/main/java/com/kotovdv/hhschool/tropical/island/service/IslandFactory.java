package com.kotovdv.hhschool.tropical.island.service;

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
        Table<Integer, Integer, Integer> table = HashBasedTable.create();

        for (int i = 0, k = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                table.put(i, j, values.get(k++));
            }
        }

        return new Island(table);
    }

}
