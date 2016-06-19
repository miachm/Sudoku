package com.amchacon.sudoku.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by MiguelPC on 15/06/2016.
 */
public class Sudoku {
    public static final int TAM = 9;
    public static final int TAM_SQUARE = (int)Math.sqrt(TAM);
    public static final int VOID = -1;

    private int solution[][] = new int[TAM][TAM];
    private int current[][] = new int[TAM][TAM];
    private boolean initially_empty[][] = new boolean[TAM][TAM];

    public Sudoku()
    {
        SudokuGenerator.generate(solution,current);
        initialize();
    }

    private void initialize()
    {
        for (int i = 0;i < current.length;i++)
            for (int j = 0;j < current[0].length;j++)
                initially_empty[i][j] = (current[i][j] == VOID);
    }

    public final int[][] getSolution() {
        return solution;
    }

    public final int[][] getCurrent()
    {
        return current;
    }

    public void setValueInPos(Position pos,int value)
    {
        current[pos.x][pos.y] = value;
    }

    public boolean fieldIsCorrect(Position pos)
    {
        return current[pos.x][pos.y] == solution[pos.x][pos.y];
    }

    public boolean initiallyEmpty(Position pos)
    {
        return initiallyEmpty(pos.x,pos.y);
    }

    public boolean initiallyEmpty(int x,int y)
    {
        return initially_empty[x][y];
    }

    public List<Integer> getPossibilities(Position pos)
    {
        return SudokuSolver.getPossibilities(current,pos.x,pos.y);
    }

    public boolean isTerminated()
    {
        return Arrays.equals(current,solution);
    }
}
