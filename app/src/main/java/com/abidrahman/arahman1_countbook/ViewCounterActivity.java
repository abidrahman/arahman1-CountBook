/**
 * ViewCounterActivity class
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * ViewCounterActivity is the corresponding activity for the Edit Page.
 * It is responsible for displaying the details of a Counter to
 * the user and modifying any of its' fields. It is also responsible
 * for the deletion of a Counter.
 */
public class ViewCounterActivity extends AppCompatActivity {

    public static final int SAVE_CODE = 0;
    public static final int CANCEL_CODE = 1;
    public static final int DELETE_CODE = 2;

    private static final String TAG = "ViewCounterActivity";
    private Intent returnIntent = new Intent();

    private TextView name, initialValue, currentValue, comments;
    private Counter oldCounter, newCounter;

    /**
     * onCreate initializes the Views on the Edit Page
     * and sets up the click handlers for all buttons.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_counter);

        oldCounter = (Counter) getIntent().getSerializableExtra("counter");

        Button saveButton = (Button) findViewById(R.id.saveViewCounter);
        Button cancelButton = (Button) findViewById(R.id.cancelViewCounter);
        Button deleteButton = (Button) findViewById(R.id.deleteViewCounter);

        name = (TextView) findViewById(R.id.counterNameValue);
        initialValue = (TextView) findViewById(R.id.initialValue);
        currentValue = (TextView) findViewById(R.id.currentValue);
        comments = (TextView) findViewById(R.id.commentValue);

        //Update counter data fiels
        name.setText(oldCounter.getName());
        initialValue.setText(String.format("%d", oldCounter.getInitial_value()));
        currentValue.setText(String.format("%d", oldCounter.getCurrent_value()));
        comments.setText(oldCounter.getComment());

        //Setup Click Handling for Save Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Adding "" turns it into String objects
                newCounter = new Counter(name.getText() + "",
                        Integer.valueOf(initialValue.getText() + ""),
                        comments.getText() + "");

                newCounter.setCurrent_value(Integer.valueOf("" + initialValue.getText()));

                //Go back to the main screen with new and old counter
                returnIntent.putExtra("oldCounter", oldCounter);
                returnIntent.putExtra("counter", newCounter);
                setResult(SAVE_CODE,returnIntent);
                finish();
            }
        });

        //Setup Click Handling for Cancel Button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(CANCEL_CODE, returnIntent);
                finish();
            }
        });

        //Setup Click Handling for Delete Button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnIntent.putExtra("counter", oldCounter);
                setResult(DELETE_CODE, returnIntent);
                finish();

            }
        });
    }
}
