package com.amchacon.sudoku.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MiguelPC on 16/06/2016.
 */
public class SudokuGenerator {
    public static final int TAM = Sudoku.TAM;
    public static final int root = (int)Math.sqrt(TAM);
    public static final int VOID = Sudoku.VOID;

    public static void generate(int[][] sudoku,int[][] sudoku_solved)
    {
        fillMat(sudoku_solved);
        copy(sudoku_solved,sudoku);
        deleteSomeNumbers(sudoku);
    }

    private static void fillMat(int solution[][]) {
        clearMat(solution);

        boolean values_tested_in_field[][][] = new boolean[TAM][TAM][TAM];
        boolean values_in_row[][] = new boolean[TAM][TAM];
        boolean values_in_column[][] = new boolean[TAM][TAM];
        boolean values_in_square[][] = new boolean[TAM][TAM];

        for (int i = 0;i < TAM;i++)
        {
            for (int j = 0;j < TAM;j++)
            {
                int value;
                boolean failed;

                if (!isEmpty(solution[i][j]))
                {
                    value = solution[i][j] - 1;

                    values_in_row[j][value] = false;
                    values_in_column[i][value] = false;
                    values_in_square[subSquare(i,j,root)][value] = false;
                    solution[i][j] = VOID;
                }

                value = Random(TAM);

                int counter = 0;
                do {
                    value = (value+1) % TAM;
                    failed = values_tested_in_field[i][j][value]
                            || values_in_row[j][value]
                            || values_in_column[i][value]
                            || values_in_square[subSquare(i,j,root)][value];

                    counter++;
                } while (failed && counter < TAM);

                if (!failed)
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

    public static boolean isEmpty(int n)
    {
        return n == VOID;
    }

    public static int subSquare(int i,int j,int root)
    {
        return (i/root+(j/root)*root);
    }

    private static int Random(int TAM)
    {
        return (int)Math.floor(Math.random()*TAM)+1;
    }

    private static void clearMat(int solution[][])
    {
        for (int i = 0;i < solution.length;i++)
            for (int j = 0;j < solution[i].length;j++)
                solution[i][j] = VOID;
    }

    private static void copy(int src[][],int dest[][])
    {
        for (int i = 0;i < src.length;i++)
            System.arraycopy(src[i],0,dest[i],0,src[i].length);
    }

    private static void deleteSomeNumbers(int current[][])
    {
        List<Position> positions = generateArrayPositions();

        Collections.shuffle(positions);

        while (!positions.isEmpty())
        {
            Position pos_current = positions.remove(0);

            int value = current[pos_current.x][pos_current.y];
            current[pos_current.x][pos_current.y] = VOID;

            if (!SudokuSolver.uniqueSolution(current))
            {
                current[pos_current.x][pos_current.y] = value;
            }
        }
    }

    private static List<Position> generateArrayPositions()
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
