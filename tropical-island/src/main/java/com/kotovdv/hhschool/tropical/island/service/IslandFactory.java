package com.kotovdv.hhschool.tropical.island.service;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.kotovdv.hhschool.tropical.island.model.Island;

import java.util.List;

/**
 * @author Dmitriy Kotov
 */
public class IslandFactory {

    public Island createIsland(int n, int m, List<Integer> values) {
        Table<Integer, Integer, Integer> table = HashBasedTable.create();

        for (int i = 0, k = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                table.put(i, j, values.get(k++));
            }
        }

        return new Island(table);
    }

}
