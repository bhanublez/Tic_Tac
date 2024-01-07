package files.tic_tac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import files.tic_tac.R.*;
import androidx.appcompat.app.AppCompatActivity;

public class Player_Setup extends AppCompatActivity {
    private EditText player1;
    private EditText player2;

    public Player_Setup() {
    }

    protected void onCreate(Bundle bun) {
        super.onCreate(bun);
        this.setContentView(layout.enterplayers);
        this.player1 = (EditText)this.findViewById(id.editTextText);
        this.player2 = (EditText)this.findViewById(id.editTextText2);
    }

    public void SubmitButtonClick(View view) {
        if (!this.player1.getText().toString().isEmpty() && !this.player2.getText().toString().isEmpty()) {
            String first = this.player1.getText().toString();
            String second = this.player2.getText().toString();
            Intent intent = new Intent(this, Display.class);
            intent.putExtra("PN", new String[]{first, second});
            this.startActivity(intent);
        } else {
            Toast.makeText(this, "Please Fill all Fields: ", 1).show();
        }

    }
}