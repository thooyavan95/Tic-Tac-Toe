package com.myapplication.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.tictactoe.Utils.GameUtils;
import com.myapplication.tictactoe.Utils.PreferenceUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private boolean isPlayerOneTurn;

    private int gamePoint;

    private TextView playerOneDetails;
    private TextView playerTwoDetails;

    private GameViewModel viewModel;

    private static final String SAVE_MARKER_KEY = "save";

    private Button[][] buttons;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "on create");

        viewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        buttons = new Button[3][3];
        isPlayerOneTurn = true;
        gamePoint = 3;

        setUpPlayerDetails();
        GameUtils.setUpButton(this, buttons);

        PreferenceUtils.setUpPreferences(this, buttons);

    }

    private void setUpPlayerDetails()
    {
        playerOneDetails = findViewById(R.id.player_1);
        playerTwoDetails = findViewById(R.id.player_2);

        playerOneDetails.append(String.valueOf(viewModel.getPlayerOneScore()));
        playerTwoDetails.append(String.valueOf(viewModel.getPlayerTwoScore()));
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "on stop");
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "on destroy");
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {

        buttonClicked(view);

    }


    private void buttonClicked(View buttonClicked)
    {
      if(!((Button) buttonClicked).getText().equals(""))
      {
          return;
      }

      if(isPlayerOneTurn)
      {
          ((Button) buttonClicked).setText(getString(R.string.string_x));
      }
      else
      {
          ((Button) buttonClicked).setText(getString(R.string.string_o));
      }


      viewModel.setMoveCount(viewModel.getMoveCount()+1);

      if(GameUtils.isRoundWon(buttons))
      {
            if(isPlayerOneTurn)
            {
                viewModel.setPlayerOneScore(viewModel.getPlayerOneScore()+1);
                updatePlayer(getString(R.string.player_1));
            }
            else
            {
                viewModel.setPlayerTwoScore(viewModel.getPlayerTwoScore()+1);
                updatePlayer(getString(R.string.player_2));
            }

           GameUtils.resetBoard(buttons);
           viewModel.setMoveCount(0);
      }

      if(viewModel.getMoveCount() == 9)
      {
          displayToast("draw!");
          GameUtils.resetBoard(buttons);
          viewModel.setMoveCount(0);
      }

      isPlayerOneTurn = !isPlayerOneTurn;

    }

    private void updatePlayer(String player)
    {
        if(player.equals(getString(R.string.player_1)))
        {
            playerOneDetails.setText(getString(R.string.player_1));
            playerOneDetails.append(String.valueOf(viewModel.getPlayerOneScore()));

            if (isGameWon(viewModel.getPlayerOneScore())) {
                showWinnerDialog();
            } else {
                displayToast("player 1 won the game");
            }
        }
        else
        {
            playerTwoDetails.setText(getString(R.string.player_2));
            playerTwoDetails.append(String.valueOf(viewModel.getPlayerTwoScore()));


            if (isGameWon(viewModel.getPlayerTwoScore())) {
                showWinnerDialog();
            } else {
                displayToast("player 2 won the game");
            }

        }
    }


    private void showWinnerDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("player 1 wins the game");
        dialogBuilder.setPositiveButton("okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resetPlayers();
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private boolean isGameWon(int score)
    {
        return score == gamePoint;
    }

    private void displayToast(String displayText)
    {
        Toast.makeText(this, displayText, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int selectedItem = item.getItemId();

        switch (selectedItem)
        {
            case R.id.action_reset:
                GameUtils.resetBoard(buttons);
                resetPlayers();
                viewModel.setMoveCount(0);
                break;

            case R.id.action_settings:

                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals(getString(R.string.pref_colour_key)))
        {
            String currentColor = sharedPreferences.getString(key, getString(R.string.pref_color_value_red));
            PreferenceUtils.setMarkerColour(this, buttons, currentColor);
        }

        if(key.equals(getString(R.string.pref_game_key)))
        {
            gamePoint = Integer.parseInt(sharedPreferences.getString(key, "3"));
        }

    }

    private void resetPlayers()
    {
        playerOneDetails.setText(getString(R.string.player_1));
        playerTwoDetails.setText(getString(R.string.player_2));
        viewModel.setPlayerOneScore(0);
        viewModel.setPlayerTwoScore(0);
    }

}
