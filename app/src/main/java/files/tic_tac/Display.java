package files.tic_tac;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import files.tic_tac.R.*;


public class Display extends AppCompatActivity {
    private Board ticTacBoard;
    public TextView homepage;

    public Display() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.game);
        TextView again = (TextView)this.findViewById(id.playAgainButton);
        this.homepage = (TextView)this.findViewById(id.homeButton);
        TextView playerTurn = (TextView)this.findViewById(id.displayTurn);
        again.setVisibility(8);
        this.homepage.setVisibility(8);
        this.ticTacBoard = (Board)this.findViewById(id.ticTacBoard);
        String[] playerNames = this.getIntent().getStringArrayExtra("PN");
        if (playerNames != null) {
            playerTurn.setText(Character.toUpperCase(playerNames[0].charAt(0)) + playerNames[0].substring(1).toLowerCase() + " Your Turn");
        }

        this.ticTacBoard.setUpGame(again, this.homepage, playerTurn, playerNames);
    }

    public void playAgainPress(View view) {
        this.ticTacBoard.resetGame();
        this.ticTacBoard.invalidate();
    }

    public void homePress(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
