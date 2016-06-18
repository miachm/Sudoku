package com.amchacon.sudoku.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.amchacon.sudoku.R;
import com.amchacon.sudoku.logic.Sudoku;

/**
 * Created by MiguelPC on 17/06/2016.
 */
public class SudokuView extends View {
    private SudokuPlay activity;
    private Paint highlightlines;
    private Paint normallines;
    private float width_cell,height_cell;

    public SudokuView(SudokuPlay sudokuPlay) {
        super(sudokuPlay);
        activity = sudokuPlay;

        setFocusable(true);
        setFocusableInTouchMode(true);

        highlightlines = new Paint();
        highlightlines.setColor(Color.BLACK);
        highlightlines.setStrokeWidth(2.0f);

        normallines = new Paint();
        normallines.setColor(ContextCompat.getColor(sudokuPlay,R.color.hightlightlines));
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh)
    {
        width_cell = w / (float) Sudoku.TAM;
        height_cell = h / (float) Sudoku.TAM;

        super.onSizeChanged(w,h,oldw,oldh);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        for (int i = 1;i <= 9;i++) {
            if (i%3 != 0) {
                float pos_x = width_cell*i;
                float pos_y = height_cell*i;

                canvas.drawLine(0,pos_y, canvas.getWidth(), pos_y, normallines);
                canvas.drawLine(pos_x, 0, pos_x, canvas.getHeight(), normallines);
            }
        }

        for (int i = 3;i <= 9;i+=3) {
            float pos_x = width_cell*i;
            float pos_y = height_cell*i;

            canvas.drawLine(0, pos_y, canvas.getWidth(), pos_y, highlightlines);
            canvas.drawLine(pos_x,0,pos_x, canvas.getHeight(), highlightlines);
        }
    }

}
