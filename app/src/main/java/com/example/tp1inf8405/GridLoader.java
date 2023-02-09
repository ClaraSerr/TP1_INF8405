package com.example.tp1inf8405;

import android.content.Context;
import android.util.Log;

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
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    String[] tokens = line.split(" ");
                    int row = Integer.parseInt(tokens[0]);
                    int col = Integer.parseInt(tokens[1]);
                    Block.Orientation orientation = tokens[2].equals("HORIZONTAL") ? Block.Orientation.HORIZONTAL : Block.Orientation.VERTICAL;
                    Block block = new RedBlock(row, col, orientation, false, context);
                    grid.addBlock(block);
                    Log.d("CODE_1_GridLoader", Integer.toString(row) + Integer.toString(col) + "RED");
                    isFirstLine = false;
                } else {
                    String[] tokens = line.split(" ");
                    int row = Integer.parseInt(tokens[0]);
                    int col = Integer.parseInt(tokens[1]);
                    Block.Orientation orientation = tokens[2].equals("HORIZONTAL") ? Block.Orientation.HORIZONTAL : Block.Orientation.VERTICAL;
                    Block block = new Block(row, col, orientation, context);
                    grid.addBlock(block);
                    Log.d("CODE_1_GridLoader", Integer.toString(row) + Integer.toString(col) + "BLUE");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return grid;
    }
}

