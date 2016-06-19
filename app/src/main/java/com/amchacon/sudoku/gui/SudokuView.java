package com.amchacon.sudoku.gui;

import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.amchacon.sudoku.R;
import com.amchacon.sudoku.logic.Position;
import com.amchacon.sudoku.logic.Sudoku;

/**
 * Created by MiguelPC on 17/06/2016.
 */
public class SudokuView extends View {
    private SudokuPlay activity;
    private Paint highlightlines;
    private Paint normallines;
    private Paint numbers_style;
    private Paint numbers_user_style;
    private float width_cell,height_cell;
    private Position selected;

    private static final String TAG = "SudokuView";

    public SudokuView(SudokuPlay sudokuPlay) {
        super(sudokuPlay);
        activity = sudokuPlay;

        setFocusable(true);
        setFocusableInTouchMode(true);

        createPaints();

        selected = new Position(0,0);
    }

    private void createPaints()
    {
        highlightlines = new Paint();
        highlightlines.setColor(Color.BLACK);
        highlightlines.setStrokeWidth(2.0f);

        normallines = new Paint();
        normallines.setColor(ContextCompat.getColor(activity,R.color.hightlightlines));

        numbers_style = new Paint();
        numbers_style.setColor(Color.BLACK);

        numbers_user_style = new Paint();
        numbers_user_style.setColor(ContextCompat.getColor(activity,R.color.numbersUser));
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh)
    {
        width_cell = w / (float) Sudoku.TAM;
        height_cell = h / (float) Sudoku.TAM;

        float min = Math.min(width_cell,height_cell);
        numbers_style.setTextSize(min/2.0f);
        numbers_user_style.setTextSize(min/2.0f);

        super.onSizeChanged(w,h,oldw,oldh);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        Log.d(TAG,"onDraw");
        drawBoard(canvas);
        putNumbers(canvas);
    }

    private void drawBoard(Canvas canvas)
    {
        for (int i = 1;i <= Sudoku.TAM;i++) {
            if (i%3 != 0) {
                float pos_x = width_cell*i;
                float pos_y = height_cell*i;

                canvas.drawLine(0,pos_y, canvas.getWidth(), pos_y, normallines);
                canvas.drawLine(pos_x, 0, pos_x, canvas.getHeight(), normallines);
            }
        }

        for (int i = Sudoku.TAM_SQUARE;i <= Sudoku.TAM;i+=Sudoku.TAM_SQUARE) {
            float pos_x = width_cell*i;
            float pos_y = height_cell*i;

            canvas.drawLine(0, pos_y, canvas.getWidth(), pos_y, highlightlines);
            canvas.drawLine(pos_x,0,pos_x, canvas.getHeight(), highlightlines);
        }
    }

    private void putNumbers(Canvas canvas)
    {
        Sudoku sudoku = activity.getSudoku();
        int mat[][] = sudoku.getCurrent();

        Paint style;
        for (int i = 0;i < mat.length;i++)
        {
            for (int j = 0;j < mat[i].length;j++)
            {
                if (mat[i][j] == Sudoku.VOID) continue;
                if (sudoku.initiallyEmpty(i,j))
                    style = numbers_user_style;
                else
                    style = numbers_style;

                canvas.drawText(""+mat[i][j],i*width_cell+width_cell/3.0f,j*height_cell+2*height_cell/3.0f,style);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);

        int x = (int) (event.getX() / width_cell);
        int y = (int) (event.getY() / height_cell);

        selected.x = x;
        selected.y = y;

        if (activity.getSudoku().initiallyEmpty(selected))
            launchKeyboard();

        return true;
    }

    private void launchKeyboard() {
        Dialog k = new Keypad(activity);
        k.show();
    }

    public void setSelectedTitleTo(int value)
    {
        Log.d(TAG,"Put " + value + " in (" + selected.x + " , " + selected.y + ")");

        activity.getSudoku().setValueInPos(selected,value);
        redrawTitle(selected);

        if (activity.getSudoku().isTerminated())
            Toast.makeText(activity,"Finished! Good job ^^",Toast.LENGTH_LONG).show();
    }

    private void redrawTitle(Position pos)
    {
        int top = (int)(pos.y*height_cell);
        int bottom = (int)(top + height_cell);
        int left = (int) (pos.x * width_cell);
        int right = (int) (left + width_cell);

        invalidate(left,top,right,bottom);
    }
}
