package com.example.tp1inf8405;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GridLoader {
    public static Grid loadFromFile(Context context, String fileName) {
        Grid grid = null;
        try (InputStream is = context.getAssets().open(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            int size = Integer.parseInt(br.readLine());
            grid = new Grid(size);

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                int row = Integer.parseInt(tokens[0]);
                int col = Integer.parseInt(tokens[1]);
                Block.Orientation orientation = tokens[2].equals("HORIZONTAL") ? Block.Orientation.HORIZONTAL : Block.Orientation.VERTICAL;
                Block block = new Block(row, col, orientation);
                grid.addBlock(block);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return grid;
    }
}

