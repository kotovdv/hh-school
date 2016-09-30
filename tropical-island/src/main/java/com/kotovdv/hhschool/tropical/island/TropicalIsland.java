package com.kotovdv.hhschool.tropical.island;

import com.kotovdv.hhschool.tropical.island.logic.FloodingSimulator;
import com.kotovdv.hhschool.tropical.island.model.Island;
import com.kotovdv.hhschool.tropical.island.service.IslandScanner;

import java.util.List;

/**
 * @author Dmitriy Kotov
 */
public class TropicalIsland {


    public static void main(String[] args) {
        IslandScanner islandScanner = new IslandScanner();
        List<Island> islands = islandScanner.read(System.in);

        FloodingSimulator floodingSimulator = new FloodingSimulator();
        islands.forEach(island -> {
            Island floodedIsland = floodingSimulator.flood(island);
            System.out.println(floodedIsland.sum() - island.sum());
        });


    }


}
