package com.amchacon.sudoku.gui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.amchacon.sudoku.R;
import com.amchacon.sudoku.logic.Sudoku;

/**
 * Created by MiguelPC on 19/06/2016.
 */
public class Keypad extends Dialog {

    private final View keys[] = new View[9];
    private View clear;
    private SudokuPlay sudoku;

    public Keypad(SudokuPlay sudoku)
    {
        super(sudoku);
        this.sudoku = sudoku;
    }

    @Override
    protected void onCreate(Bundle instance)
    {
        super.onCreate(instance);
        setContentView(R.layout.keypad);
        setTitle("Keypad");
        findButtons();
        setListeners();
    }

    private void findButtons()
    {
        keys[0] = findViewById(R.id.keypad_1);
        keys[1] = findViewById(R.id.keypad_2);
        keys[2] = findViewById(R.id.keypad_3);
        keys[3] = findViewById(R.id.keypad_4);
        keys[4] = findViewById(R.id.keypad_5);
        keys[5] = findViewById(R.id.keypad_6);
        keys[6] = findViewById(R.id.keypad_7);
        keys[7] = findViewById(R.id.keypad_8);
        keys[8] = findViewById(R.id.keypad_9);

        clear = findViewById(R.id.keypad_clear);
    }

    private void setListeners()
    {
        for (int i = 0;i < keys.length;i++)
        {
            final int value = i+1;
            keys[i].setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               sudoku.setSelectedTitleTo(value);
                                               dismiss();
                                           }
                                       }
            );
        }

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sudoku.setSelectedTitleTo(Sudoku.VOID);
            }
        });
    }
}
