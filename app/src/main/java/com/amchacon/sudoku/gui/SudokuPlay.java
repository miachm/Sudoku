package com.amchacon.sudoku.gui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amchacon.sudoku.R;

public class SudokuPlay extends Activity {

    private static final String TAG = "SudokuPlay";

    private SudokuView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"onCreate");
        view = new SudokuView(this);
        setContentView(view);
    }
}
