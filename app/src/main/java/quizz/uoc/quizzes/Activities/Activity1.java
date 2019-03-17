package quizz.uoc.quizzes.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quizz.uoc.quizzes.DBHelpers.SQLHelper;
import quizz.uoc.quizzes.Model.Question;
import quizz.uoc.quizzes.R;

public class Activity1 extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = "PAC2";
    private RadioGroup radioGroup;
    private Button btnSend;
    private TextView textViewNumberQuestion;
    private TextView textViewQuestion;
    private RadioButton radioButtonFirstAnswer;
    private RadioButton radioButtonSecondAnswer;
    private List<Question> listQuestions;
    private static int counterQuestion = 1;
    private int counterPlus,restart;


    //Create the instance of dbHelper
    private SQLHelper sqlHelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        //Set Views
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewNumberQuestion = findViewById(R.id.textViewNumberQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonFirstAnswer = findViewById(R.id.radioButtonFirstAnswer);
        radioButtonSecondAnswer = findViewById(R.id.radioButtonSecondAnswer);
        btnSend = findViewById(R.id.buttonSend);

        //Set listeners
        btnSend.setOnClickListener(this);

        //Creamos el objeto sqlHelper y la base de datos
        sqlHelper = new SQLHelper(this,"Quizzes.db", null, 1);
        db = sqlHelper.getWritableDatabase();

        //Load all the list of questions into the List from the SQLite
        if(getAllQuestions(db).size() == 0) {
            insertQuestion(db,  new Question(R.string.questionTitle,"10 y = 70", new String[]{ "y = 7","y = 23" },"y = 7"));
            insertQuestion(db,  new Question(R.string.questionTitle,"3 y = 15", new String[]{ "y = 7","y = 5" },"y = 5"));
            insertQuestion(db,  new Question(R.string.questionTitle,"2 y = 10", new String[]{ "y = 5","y = 3" },"y = 5"));
        }

        //Fill the array with the questions
        this.listQuestions = getAllQuestions(db);

        updateActivity();

    }

    //Close the db when the activity onDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    //Method to insert question in the db
    private void insertQuestion(SQLiteDatabase db, Question q){
        sqlHelper.insertQuestion(db, q);
    }

    //Method to load all questions from db
    private List<Question> getAllQuestions(SQLiteDatabase db){
        return sqlHelper.getAllQuestions(db);
    }

    /*Upadte the activity, load the question, answers and counter value*/
    public void updateActivity(){
        counterPlus = getIntent().getIntExtra("counterPlus",0);
        restart = getIntent().getIntExtra("restart",0);

        if(restart == 0){
            counterQuestion += counterPlus;
            createQuestion(listQuestions.get(counterQuestion - 1));
        }else{
            counterQuestion = 1;
            createQuestion(listQuestions.get(counterQuestion - 1));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateActivity();
    }

    /*When the activity2 is call, we transfer the state to detect correct o incorrect answer
    and counterQuestion to check if the user finished the game*/
    public void startActivity2(int state){
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("state", state);
        intent.putExtra("counterQuestion", counterQuestion);
        startActivity(intent);
    }

    //Set the values for the question into the view
    public void createQuestion(Question q){
        textViewNumberQuestion.setText(counterQuestion + "/" + listQuestions.size());
        textViewQuestion.setText(q.getQuestionMath());
        radioButtonFirstAnswer.setText(q.getListAnswers()[0]);
        radioButtonSecondAnswer.setText(q.getListAnswers()[1]);
    }

    /*Check if the answer selected is the correct answer for the question*/
    public void checkQuestion(CharSequence answer){
        if(listQuestions.get(counterQuestion - 1).getCorrectAnswer().equalsIgnoreCase(String.valueOf(answer))){
            startActivity2(0);
        }else{
            startActivity2(1);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnSend){
            if(radioGroup.getCheckedRadioButtonId() == -1){
                //The user not select any option
                Toast.makeText(getApplicationContext(),R.string.no_response,Toast.LENGTH_LONG).show();
            }else{
                //Check if the user choosed the correct answer
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                checkQuestion(radioButton.getText());
            }
        }

    }
}
