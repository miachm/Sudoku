package com.amchacon.sudoku.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by MiguelPC on 15/06/2016.
 */
public class Sudoku {
    public static final int TAM = 9;

    private int solution[][] = new int[TAM][TAM];
    private int current[][] = new int[TAM][TAM];

    public Sudoku()
    {
        SudokuGenerator.generate(solution,current);
    }

    public int[][] getSolution() {
        return solution;
    }

    public int[][] getCurrent()
    {
        return current;
    }


}
