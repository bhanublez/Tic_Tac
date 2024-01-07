package files.tic_tac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void PlayButtonClick(View view) {
        try {
            Intent intent = new Intent(this, Player_Setup.class);
            this.startActivity(intent);
        } catch (Exception var3) {
            System.out.println("This is error" + var3);
        }

    }
}