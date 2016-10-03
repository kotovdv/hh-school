package com.kotovdv.hhschool.tropical.island;

import com.kotovdv.hhschool.tropical.island.logic.FloodingSimulator;
import com.kotovdv.hhschool.tropical.island.model.Island;
import com.kotovdv.hhschool.tropical.island.util.IslandFactory;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * @author Dmitriy Kotov
 */
public class TropicalIsland {

    public static void main(String[] args) {
        TropicalIsland tropicalIsland = new TropicalIsland();
        tropicalIsland.solve(System.in, System.out);
    }

    public void solve(final InputStream inputStream, final PrintStream outputStream) {
        List<Island> islands = new IslandFactory().create(inputStream);

        FloodingSimulator floodingSimulator = new FloodingSimulator();
        islands.forEach(island -> {
            Island floodedIsland = floodingSimulator.flood(island);
            outputStream.println(floodedIsland.sum() - island.sum());
        });
    }


}
