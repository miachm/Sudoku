package com.amchacon.sudoku.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by MiguelPC on 16/06/2016.
 */
public class SudokuSolver {

    public static final int VOID = SudokuGenerator.VOID;

    public static boolean uniqueSolution(int mat[][])
    {
        return countsSolutions(mat,0,0) == 1;
    }

    public static int countsSolutions(int mat[][],int x,int y)
    {
        if (y >= mat.length)
        {
            return 1;
        }
        else
        {
            while (!isEmpty(mat[y][x]))
            {
                x++;
                if (x >= mat[y].length)
                {
                    x = 0;
                    y++;

                    if (y >= mat.length)
                    {
                        return 1;
                    }
                }
            }

            List<Integer> possibilities = getPossibilities(mat,x,y);

            int solutions = 0;
            for (Integer value : possibilities)
            {
                mat[y][x] = value;
                solutions += countsSolutions(mat,x,y);
                mat[y][x] = VOID;
            }

            return solutions;
        }
    }

    private static List<Integer> getPossibilities(final int mat[][],int x,int y)
    {
        List<Integer> possibilities = new ArrayList<Integer>();
        for (int i = 0;i < mat.length;i++)
        {
            possibilities.add(i);
        }

        //Columns

        for (int i = 0;i <mat.length;i++)
        {
            possibilities.remove((Integer)mat[i][x]);
        }

        // Rows

        for (int i = 0;i < mat[0].length;i++)
        {
            possibilities.remove((Integer)mat[y][i]);
        }

        // Subsquares

        int root = (int) Math.sqrt(mat.length);

        for (int i = x/root;i< (x/root + root);i++)
        {
            for (int j = y/root;j < (y/root + root);j++)
            {
                possibilities.remove((Integer)mat[j][i]);
            }
        }

        return possibilities;
    }

    private static boolean isEmpty(int n)
    {
        return SudokuGenerator.isEmpty(n);
    }
}
