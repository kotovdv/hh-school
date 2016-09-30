package com.kotovdv.hhschool.tropical.island.service;

import com.kotovdv.hhschool.tropical.island.model.Island;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Dmitriy Kotov
 */
public class IslandScanner {

    private final IslandFactory islandFactory = new IslandFactory();

    public List<Island> read(InputStream inputStream) {
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

            islands.add(islandFactory.createIsland(n, m, islandDefinition));
        }

        return islands;
    }

}
