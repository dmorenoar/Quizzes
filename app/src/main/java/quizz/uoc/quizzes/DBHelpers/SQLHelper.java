package quizz.uoc.quizzes.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import quizz.uoc.quizzes.Model.Question;
import quizz.uoc.quizzes.R;

public class SQLHelper extends SQLiteOpenHelper {

    //String to creation of table Question
    String sqlCreateQuestion = "CREATE TABLE Question(" +
            "ID_Question INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "questionTitle INTEGER," +
            "questionMath TEXT," +
            "answerFirst TEXT," +
            "answerSecond TEXT," +
            "correctAnswer TEXT)";

    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //Creation of the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateQuestion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete the old version of the table
        db.execSQL("DROP TABLE IF EXISTS Question");

        //Create the new version of the table
        db.execSQL(sqlCreateQuestion);
    }


    public List<Question> getAllQuestions(SQLiteDatabase db){
        //Selection all registers from the table Question
        Cursor cursor = db.rawQuery("Select * from Question", null);
        List<Question> listQuestions = new ArrayList<>();

        if(cursor.moveToFirst()){
            //Iteration on the cursos results fill the array
            while(cursor.isAfterLast() == false){

                int questionTitle = cursor.getInt(cursor.getColumnIndex("questionTitle"));
                String questionMath = cursor.getString(cursor.getColumnIndex("questionMath"));
                String answerFirst = cursor.getString(cursor.getColumnIndex("answerFirst"));
                String answerSecond = cursor.getString(cursor.getColumnIndex("answerSecond"));
                String correctAnswer = cursor.getString(cursor.getColumnIndex("correctAnswer"));

                //Get the values from row in table
                Question q = new Question(questionTitle,questionMath, new String[]{ answerFirst,answerSecond },correctAnswer);
                listQuestions.add(q);

                cursor.moveToNext();
            }
        }
        cursor.close();

        return listQuestions;
    }

    public void insertQuestion(SQLiteDatabase db, Question q){
        //Check the bd is open
        if (db.isOpen()){
            //Creation of the register for insert object
            ContentValues insert = new ContentValues();

            //Insert the question getting all values
            insert.put("questionTitle", q.getQuestionTitle());
            insert.put("questionMath", q.getQuestionMath());
            insert.put("answerFirst", q.getListAnswers()[0]);
            insert.put("answerSecond", q.getListAnswers()[1]);
            insert.put("correctAnswer", q.getCorrectAnswer());

            System.out.println("El insert:" + insert);

            db.insert("Question", null, insert);
        }else{
            System.out.println("Base de datos no abierta");
        }
    }

    //Method to remove questions from db
    public void removeAllQuestions(SQLiteDatabase db){
        db.delete("Question","",null);
    }

}
