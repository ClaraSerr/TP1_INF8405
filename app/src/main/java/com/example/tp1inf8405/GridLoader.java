package com.example.tp1inf8405;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GridLoader {
    public static Grid loadFromFile(String fileName) {
        Grid grid = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int size = Integer.parseInt(br.readLine());
            grid = new Grid(size);

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                int row = Integer.parseInt(tokens[0]);
                int col = Integer.parseInt(tokens[1]);
                Block.Orientation orientation = tokens[2].equals("HORIZONTAL") ? Block.Orientation.HORIZONTAL : Block.Orientation.VERTICAL;
                Block block = new Block(orientation);
                grid.addBlock(block, row, col);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grid;
    }
}
