package com.myapplication.tictactoe.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.myapplication.tictactoe.MainActivity;
import com.myapplication.tictactoe.R;

public class PreferenceUtils {


    public static void setUpPreferences(Context context, Button[][] buttons)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String currentColor = sharedPreferences.getString(context.getString(R.string.pref_colour_key), context.getString(R.string.pref_color_value_red));
        PreferenceUtils.setMarkerColour(context, buttons, currentColor);

//        gamePoint = sharedPreferences.getInt(getString(R.string.pref_game_key), getResources().getInteger(R.integer.pref_game_point_value_three));

        sharedPreferences.registerOnSharedPreferenceChangeListener((MainActivity)context);

    }

    public static void setMarkerColour(Context context, Button[][] buttons, String currentColor)
    {
        int colour = 0;
        if(currentColor.equals(context.getString(R.string.pref_color_value_red)))
        {
            colour = context.getResources().getColor(R.color.colorRed);
        }else if(currentColor.equals(context.getString(R.string.pref_color_value_blue)))
        {
            colour = ContextCompat.getColor(context, R.color.colorBlue);
        }
        else if(currentColor.equals(context.getString(R.string.pref_color_value_green)))
        {
            colour = ContextCompat.getColor(context,R.color.colorGreen);
        }

        for(int row=0; row<3; row++)
        {
            for(int column=0; column<3; column++)
            {
                buttons[row][column].setTextColor(colour);
            }
        }

    }

}
