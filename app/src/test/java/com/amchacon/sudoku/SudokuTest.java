package com.amchacon.sudoku;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SudokuTest {
    @Test
    public void fillNumbers() throws Exception {
        // Test generate 3 sudoku
        for (int l = 0;l < 3;l++) {
            Sudoku sudoku = new Sudoku();

            int mat[][] = sudoku.getSolution();

            final int TAM = 9;

            boolean values_in_row[][] = new boolean[TAM][TAM];
            boolean values_in_column[][] = new boolean[TAM][TAM];
            boolean values_in_square[][] = new boolean[TAM][TAM];

            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat.length; j++) {
                    int value = mat[i][j] - 1;
                    //System.out.print(value + " ,");
                    assertEquals(false, values_in_row[j][value]);
                    assertEquals(false, values_in_column[i][value]);
                    assertEquals(false, values_in_square[Sudoku.subSquare(i, j, 3)][value]);

                    values_in_row[j][value] = true;
                    values_in_column[i][value] = true;
                    values_in_square[Sudoku.subSquare(i, j, 3)][value] = true;
                }
                //System.out.println();
            }
        }
    }
}