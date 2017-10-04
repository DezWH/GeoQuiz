/*
Chapt. 2
Programmer: Dez Hunter
Date: September 13, 2017
Filename: GeoQuiz
Purpose: Locate different geographical places around the world
 */
package com.bignerdranch.android.geoquiz;

import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_IS_CHEATER_ARRAY = "is_cheater_array";


    //Buttons
    private Button mTrueButton;
    private Button mFalseButton;


    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mCheatButton;

    private TextView mQuestionTextView;

    //Adding variable and Question Bank
    private Question[] mQuestionBank = new Question[]{
        new Question(R.string.question_australia, true),
        new Question(R.string.question_oceans,true),
        new Question(R.string.question_mideast,false),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_americas,true),
        new Question(R.string.question_asia, true),

    };

    //Save Cheater Status" of questions. All value will default to false
    private boolean[] mCheaterStatus = new boolean[mQuestionBank.length];

    private int mCurrentIndex = 0;


    //Give Android Lint a help clue
    @TargetApi(11)
    //Wiring up the Text View and set its text question at current index
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);


        //Dummy Code for SDK lesson
       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            actionBar.setSubtitle("Bodies of Water");
        }
*/
        //Check to see if we are actually just redrwawing after a state chang
        if(savedInstanceState !=null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCheaterStatus = savedInstanceState.getBooleanArray(KEY_IS_CHEATER_ARRAY);

        }

        Log.d(TAG,"onCreate(): Pacific Ocean CheaterStates: " + mCheaterStatus[0]);

        //Link question from bank to view
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        updateQuestion();
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {

                                                 @Override
                                                 public void onClick(View v) {
                                                     mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                                                     updateQuestion();
                                                 }
                                             });

    //Wiring up the new button

        //Link to layout True Button and listen for click
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        checkAnswer(true);

                    }
                    });

        //Link to layout False Button and listen for click
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

             //   mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                  checkAnswer(false);

                //updateQuestion();

            }

    });
        //Link Next Button view and listen for click
        mNextButton = (ImageButton)findViewById(R.id.next_button); //Initiate the button
        mNextButton.setOnClickListener(new View.OnClickListener(){ //Place a listern on the button

                    @Override
                    public void onClick(View v){
                        mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;
                        updateQuestion();

                    }
        });

        //Link Previous Button to view and listen for click
        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
                                               updateQuestion();

                                           }
                                       });

        //Link Cheat Button to view and listen for click
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){

                                            @Override
                                            public void onClick(View v){
                                                Intent i = new Intent (QuizActivity.this, CheatActivity.class );
                                                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

                                                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);

                                                startActivityForResult(i,0);
                                            }
                                        });

    }

     @Override
     public void onSaveInstanceState(Bundle savedInstanceState){
         super.onSaveInstanceState(savedInstanceState);
         Log.i(TAG, "onSaveInstanceState");
         savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
         savedInstanceState.putBooleanArray(KEY_IS_CHEATER_ARRAY, mCheaterStatus);

         Log.d(TAG, "onSaveInstanceState: Pacific Ocean CheaterStats: " + mCheaterStatus[0]);
     }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume()called");

    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause()called");

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop()called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()called");

    }

  /*  @Override
           public boolean onCreateOptionsMenu(Menu mene) {
        Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflater().inflate(R.menu.quiz.menu);

        return true;
        }
*/


    //Check if they cheated
    @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data){
            if (data ==null)

        return;

        mCheaterStatus[mCurrentIndex] = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    //Update Next Question
            private void updateQuestion(){
        //Log.d(TAG, "Updating question text for question #" + mCurrentIndex, new Exception());
        int question=mQuestionBank[mCurrentIndex].getTextResId();

        mQuestionTextView.setText(question);
        }
        //Check answer
        private void checkAnswer(boolean userPressedTrue){

        int messageResId=0;

            boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

         //  if(mIsCheater){
        if(mCheaterStatus[mCurrentIndex]){
        messageResId=R.string.judgment_toast;
        }else{
        if(userPressedTrue==answerIsTrue){
        messageResId=R.string.correct_toast;
        }else{
        messageResId=R.string.incorrect_toast;
        }
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
        }
    }

