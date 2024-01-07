package files.tic_tac;

import android.widget.TextView;

public class GameLogic {
    private final int[][] gameBoard = new int[3][3];
    private int[] winType = new int[]{-1, -1, -1};
    private int player = 1;
    String[] playerNames = new String[]{"Player 1", "Player 2"};
    private TextView playAgainBTN;
    private TextView homeBTN;
    private TextView playerTurn;

    GameLogic() {
        for(int r = 0; r < 3; ++r) {
            for(int c = 0; c < 3; ++c) {
                this.gameBoard[r][c] = 0;
            }
        }

    }

    public boolean updateGameBoard(int row, int col) {
        if (this.gameBoard[row - 1][col - 1] == 0) {
            this.gameBoard[row - 1][col - 1] = this.player;
            if (this.player == 1) {
                this.playerTurn.setText(Character.toUpperCase(this.playerNames[1].charAt(0)) + this.playerNames[1].substring(1).toLowerCase() + " Your Turn");
            } else {
                this.playerTurn.setText(Character.toUpperCase(this.playerNames[0].charAt(0)) + this.playerNames[0].substring(1).toLowerCase() + " Your Turn");
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean winnerCheck() {
        boolean isWinner = false;

        int boardFilled;
        for(boardFilled = 0; boardFilled < 3; ++boardFilled) {
            if (this.gameBoard[boardFilled][0] == this.gameBoard[boardFilled][1] && this.gameBoard[boardFilled][0] == this.gameBoard[boardFilled][2] && this.gameBoard[boardFilled][0] != 0) {
                this.winType = new int[]{boardFilled, 0, 1};
                isWinner = true;
                break;
            }
        }

        for(boardFilled = 0; boardFilled < 3; ++boardFilled) {
            if (this.gameBoard[0][boardFilled] == this.gameBoard[1][boardFilled] && this.gameBoard[2][boardFilled] == this.gameBoard[0][boardFilled] && this.gameBoard[0][boardFilled] != 0) {
                this.winType = new int[]{0, boardFilled, 2};
                isWinner = true;
                break;
            }
        }

        if (this.gameBoard[0][0] == this.gameBoard[1][1] && this.gameBoard[2][2] == this.gameBoard[0][0] && this.gameBoard[0][0] != 0) {
            this.winType = new int[]{0, 2, 3};
            isWinner = true;
        }

        if (this.gameBoard[2][0] == this.gameBoard[1][1] && this.gameBoard[0][2] == this.gameBoard[2][0] && this.gameBoard[2][0] != 0) {
            this.winType = new int[]{2, 2, 4};
            isWinner = true;
        }

        boardFilled = 0;

        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 3; ++col) {
                if (this.gameBoard[row][col] != 0) {
                    ++boardFilled;
                }
            }
        }

        if (isWinner) {
            this.playAgainBTN.setVisibility(0);
            this.homeBTN.setVisibility(0);
            this.playerTurn.setText(Character.toUpperCase(this.playerNames[this.player - 1].charAt(0)) + this.playerNames[this.player - 1].substring(1).toLowerCase() + " WON!!!");
            return true;
        } else if (boardFilled == 9) {
            this.playAgainBTN.setVisibility(0);
            this.homeBTN.setVisibility(0);
            this.playerTurn.setText("Game is Tied!!!");
            return true;
        } else {
            return false;
        }
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return this.player;
    }

    public int[][] getGameBoard() {
        return this.gameBoard;
    }

    public void setPlayAgainBTN(TextView playAgainBTN) {
        this.playAgainBTN = playAgainBTN;
    }

    public void setHomeBTN(TextView homeBTN) {
        this.homeBTN = homeBTN;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public int[] getWinType() {
        return this.winType;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public void resetGame() {
        for(int r = 0; r < 3; ++r) {
            for(int c = 0; c < 3; ++c) {
                this.gameBoard[r][c] = 0;
            }
        }

        this.winType[0] = -1;
        this.winType[1] = -1;
        this.winType[2] = -1;
        this.player = 1;
        this.playAgainBTN.setVisibility(8);
        this.homeBTN.setVisibility(8);
        this.playerTurn.setText(this.playerNames[0] + "'s turn");
    }
}
