package com.amchacon.sudoku;

import android.provider.Settings;
import android.util.Log;

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
        generate();
    }

    public int[][] getSolution() {
        return solution;
    }

    public int[][] getCurrent()
    {
        return current;
    }

    private void generate()
    {
        fillMat();
        copy();
        deleteSomeNumbers();
    }

    private void fillMat() {
        clearMat();

        int root = (int)Math.sqrt(TAM);

        boolean values_tested_in_field[][][] = new boolean[TAM][TAM][TAM];
        boolean values_in_row[][] = new boolean[TAM][TAM];
        boolean values_in_column[][] = new boolean[TAM][TAM];
        boolean values_in_square[][] = new boolean[TAM][TAM];

        for (int i = 0;i < TAM;i++)
        {
            for (int j = 0;j < TAM;j++)
            {
                int value;
                boolean fallo;

                if (solution[i][j] >= 0) // Si ya contenía algo, quitamos sus valores de las tablas
                {
                    value = solution[i][j] - 1;

                    values_in_row[j][value] = false;
                    values_in_column[i][value] = false;
                    values_in_square[subSquare(i,j,root)][value] = false;
                    solution[i][j] = -1;
                }

                value = Random(TAM);

                int counter = 0;
                do {
                    value = (value+1) % TAM;
                    fallo = values_tested_in_field[i][j][value]
                            || values_in_row[j][value]
                            || values_in_column[i][value]
                            || values_in_square[subSquare(i,j,root)][value];

                    counter++;
                } while (fallo && counter < TAM);

                if (!fallo)
                {
                    solution[i][j] = value+1; // Nummbers in 0-8 format to 1-9 format
                    values_tested_in_field[i][j][value] = true;
                    values_in_row[j][value] = true;
                    values_in_column[i][value] = true;
                    values_in_square[subSquare(i,j,root)][value] = true;
                }
                else // Backtracking
                {
                    for (int k = 0;k < TAM;k++)
                    {
                        values_tested_in_field[i][j][k] = false;
                    }

                    j -= 2;

                    if (j < -1)
                    {
                        j =TAM-2;
                        i--;
                    }
                }
            }
        }

    }

    public static int subSquare(int i,int j,int root)
    {
        return (i/root+(j/root)*root);
    }

    private int Random(int TAM)
    {
        return (int)Math.floor(Math.random()*TAM)+1;
    }

    private void clearMat()
    {
        for (int i = 0;i < solution.length;i++)
            for (int j = 0;j < solution[i].length;j++)
                solution[i][j] = -1;
    }

    private void copy()
    {
        for (int i = 0;i < solution.length;i++)
            System.arraycopy(solution[i],0,current[i],0,solution[i].length);
    }

    class Position
    {
        int x,y;

        Position(int x,int y)
        {
            this.x = x;
            this.y = y;
        }
    }

    private void deleteSomeNumbers()
    {
        List<Position> positions = generateArrayPositions();

        Collections.shuffle(positions);

        while (!positions.isEmpty())
        {
            Position pos_current = positions.remove(0);

            int value = current[pos_current.x][pos_current.y];
            current[pos_current.x][pos_current.y] = -1;

            if (!SudokuSolver.uniqueSolution(current))
            {
                current[pos_current.x][pos_current.y] = value;
            }
        }
    }

    private List<Position> generateArrayPositions()
    {
        List<Position> positions = new ArrayList<Position>();

        for (int i = 0;i < TAM;i++)
        {
            for (int j = 0;j < TAM;j++)
            {
                positions.add(new Position(i,j));
            }
        }
        return positions;
    }
}
