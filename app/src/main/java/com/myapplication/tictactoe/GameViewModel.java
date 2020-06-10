package com.myapplication.tictactoe;

import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private int PlayerOneScore;
    private int playerTwoScore;
    private int moveCount;

    public int getPlayerOneScore() {
        return PlayerOneScore;
    }

    public void setPlayerOneScore(int playerOneScore) {
        PlayerOneScore = playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
}
