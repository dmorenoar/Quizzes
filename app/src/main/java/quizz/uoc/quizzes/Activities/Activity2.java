package quizz.uoc.quizzes.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import quizz.uoc.quizzes.R;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private TextView textViewDescription;
    private int state;
    private int counterQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //Set Views
        textViewDescription = findViewById(R.id.textViewDescription);
        button = findViewById(R.id.button);

        //Set Listener
        button.setOnClickListener(this);

        state = getIntent().getIntExtra("state", 0);
        counterQuestion = getIntent().getIntExtra("counterQuestion", 0);


        /*State 0 - Correct answer
        State 1 - Incorrect answer*/

        //Check if user finished the game
        if (state == 0 && counterQuestion == 3) {
            textViewDescription.setText(R.string.finish_game);
            button.setText(R.string.button_finish);
        } else {
            switch (state) {
                case 0:
                    textViewDescription.setText(R.string.question_ok);
                    button.setText(R.string.button_success);
                    break;
                case 1:
                    textViewDescription.setText(R.string.question_fail);
                    button.setText(R.string.button_fail);
                    break;
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkAction();
    }

    /*Check the action to do in the differents cases, and update the points*/
    public void checkAction(){
        if (state == 0 && counterQuestion == 3) {
            startActivity1(0,1);
        } else {
            switch (state) {
                case 0:
                    startActivity1(1,0);
                    break;
                case 1:
                    startActivity1(0,0);
                    break;
            }
        }
    }

    /*Recieve the value for the counter and if the flag restart is activated*/
    public void startActivity1(int value, int restart) {
        Intent intent = new Intent(this, Activity1.class);
        intent.putExtra("counterPlus", value);
        intent.putExtra("restart", restart);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        checkAction();
    }
}
