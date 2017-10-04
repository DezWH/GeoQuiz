package com.bignerdranch.android.geoquiz;

/**
 * Created by danne on 9/27/2017.
 */


import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

public class CheatActivity extends Activity{
    //adding log TAG
    private static final String TAG = "CheatActivity";

    public static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";

    public static final String EXTRA_ANSWER_SHOWN=
            "com.bignerdranch.android.geoquiz.answer_shown";

    private static final String KEY_IS_CHEATER = "is_cheater";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private Button mShownAnswer;

    private  void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);

    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cheat);

            //get the saved cheater value
            //through screen changes
            if (savedInstanceState != null) {
                mAnswerIsTrue = savedInstanceState.getBoolean(KEY_IS_CHEATER);

            }

            //Answer will not be show until the user
            //press the button
            setAnswerShownResult(false);

            mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

            mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

            mShownAnswer = (Button) findViewById(R.id.show_answer_button);
            mShownAnswer.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mAnswerIsTrue) {
                        mAnswerTextView.setText(R.string.true_button);
                    } else {

                        mAnswerTextView.setText(R.string.false_button);

                    }

                    setAnswerShownResult(true);
                }
            });
        }

    //keep the index question and cheater value
    //through screen changes
    @Override
        public void onSaveInstanceState(Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            Log.i(TAG, "onSaveInstanceState");
            savedInstanceState.putBoolean(KEY_IS_CHEATER, mAnswerIsTrue);

    }

}

//https://github.com/gmunumel/GeoQuiz/blob/master/src/com/bignerdranch/android/geoquiz/CheatActivity.java