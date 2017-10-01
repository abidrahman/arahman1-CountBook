/**
 * AddCounterActivity class
 *
 * Copyright 2017 Abid Rahman
 *
 * @author arahman1
 * @version 1.0
 * @created 2017-09-30
 */

package com.abidrahman.arahman1_countbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * AddCounterActivity is the corresponding activity to the Add Page.
 * It is responsible for allowing the user to enter a name, value,
 * and comment and store it as a new Counter. It also handles invalid
 * cases.
 */
public class AddCounterActivity extends AppCompatActivity {

    private static final String TAG = "AddCounterActivity";
    private Intent returnIntent = new Intent();

    /**
     * onCreate initializes the Views on the Add Page
     * and sets up the click handlers for all buttons.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_counter);

        Button doneButton = (Button) findViewById(R.id.addCounter);
        Button cancelButton = (Button) findViewById(R.id.cancelAddCounter);

        //Setup Click Handling for Done Button
        //Create a new counter with given data, if data is invalid, do nothing
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            TextView counterName = (TextView) findViewById(R.id.counterNameValue);
            TextView counterValue = (TextView) findViewById(R.id.initialValue);
            TextView counterComment = (TextView) findViewById(R.id.commentValue);

            String counterNameValue = counterName.getText().toString();
            String counterCommentValue = counterComment.getText().toString();
            int counterValueInt = Integer.parseInt(counterValue.getText().toString());


            if((counterNameValue != "Enter counter name") | (counterNameValue != "")) {
                Counter counter = new Counter(counterNameValue, counterValueInt, counterCommentValue);

                //Go back to the main screen
                returnIntent.putExtra("counter", counter);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
            }
        });

        //Setup Click Handling for Cancel Button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            setResult(RESULT_CANCELED,returnIntent);
            finish();
            }
        });
    }
}
