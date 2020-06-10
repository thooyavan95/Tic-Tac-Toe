package com.myapplication.tictactoe.Utils;

import android.content.Context;
import android.widget.Button;

import com.myapplication.tictactoe.MainActivity;

public class GameUtils {


    public static void setUpButton(Context context, Button[][] buttons)
    {
        for(int row=0; row<3; row++)
        {
            for(int column=0; column<3; column++)
            {
                int resId = context.getResources().getIdentifier("button_" + row + column, "id", context.getPackageName());
                buttons[row][column] = ((MainActivity)context).findViewById(resId);
                buttons[row][column].setOnClickListener((MainActivity)context);
            }
        }
    }

    public static boolean isRoundWon(Button[][] buttons)
    {
        for(int row=0; row<3; row++)
        {
            if(buttons[row][0].getText().equals(buttons[row][1].getText()) && buttons[row][0].getText().equals(buttons[row][2].getText())
                    && !(buttons[row][0].getText().equals("")))
            {
                return true;
            }
        }

        for(int column=0; column<3; column++)
        {
            if(buttons[0][column].getText().equals(buttons[1][column].getText()) && buttons[0][column].getText().equals(buttons[2][column].getText())
                    && !(buttons[0][column].getText().equals("")))
            {
                return true;
            }
        }

        if(buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[0][0].getText().equals(buttons[2][2].getText())
                && !(buttons[0][0].getText().equals("")))
        {
            return true;
        }

        if(buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[0][2].getText().equals(buttons[2][0].getText())
                && !(buttons[0][2].getText().equals("")))
        {
            return true;
        }

        return false;
    }

    public static void resetBoard(Button[][] buttons)
    {
        for(int row=0; row<3; row++)
        {
            for(int column=0; column<3; column++)
            {
                buttons[row][column].setText("");
            }
        }

    }
}
