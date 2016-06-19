package com.amchacon.sudoku.gui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amchacon.sudoku.R;
import com.amchacon.sudoku.logic.Sudoku;

public class SudokuPlay extends Activity {

    private static final String TAG = "SudokuPlay";
    private Sudoku sudoku;
    private SudokuView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"onCreate");
        sudoku = new Sudoku();
        view = new SudokuView(this);
        setContentView(view);
    }

    public Sudoku getSudoku()
    {
        return sudoku;
    }

    public void setSelectedTitleTo(int value)
    {
        view.setSelectedTitleTo(value);
    }
}
