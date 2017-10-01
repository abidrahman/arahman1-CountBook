/**
 * CountBookActivity class
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * CountBookActivity is the main activity for the Count Book app.
 * It is responsible for storing and updating a list
 * of Counters in a JSON file format. It also allows the user to view
 * the list of Counters on the screen and utilizes a CounterAdapter
 * to control the list of Counters.
 */
public class CountBookActivity extends AppCompatActivity {

    private static final String TAG = "CountBookActivity";
    private static final String FILENAME = "counterList.arahman.sav";
    static final int ADD_COUNTER_CODE = 1;
    static final int MODIFY_COUNTER_CODE = 2;

    private ArrayList<Counter> counters;
    private CounterAdapter adapter;
    private ListView counterListView;
    private TextView counterSummary;

    /**
     * onCreate initializes the Text and List Views displayed on
     * the main screen.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterSummary = (TextView) findViewById(R.id.counterSummary);
        counterListView = (ListView) findViewById(R.id.counterList);
    }

    /**
     * onStart loads the Counters from the saved file and
     * links up the adapters with the dataset.
     */
    @Override
    protected void onStart() {
        super.onStart();

        loadFromFile();
        adapter = new CounterAdapter(this, counters);
        counterListView.setAdapter(adapter);
        //Update Counter Count
        counterSummary.setText(String.format("Counter Count: %d", counters.size()));
    }

    /**
     * onPause makes sure to save the Counters to a file
     * before navigating away from the main activity.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile();
    }

    /**
     * onCreateOptionsMenu allows the display of the Menu
     * at the top of the screen.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * onOptionsItemSelected handles clicks on the Main Menu.
     * @param item, the item that was clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Handling for the Add counter button in the Menu
            case R.id.action_add_counter:
                Intent addCounterIntent = new Intent(this, AddCounterActivity.class);
                addCounterIntent.putExtra("counters", counters);
                startActivityForResult(addCounterIntent, ADD_COUNTER_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * onActivityResult handles the data received from other activities
     * @param requestCode, the code of the incoming Intent
     * @param resultCode, the result code of the incoming data
     * @param data, the incoming data from the intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            //Data from the AddCounterActivity class
            case ADD_COUNTER_CODE:
                //If new counter added, add to list
                if (RESULT_OK == resultCode) {
                    Counter counter = (Counter) data.getSerializableExtra("counter");
                    counters.add(counter);
                    adapter.notifyDataSetChanged();
                    saveToFile();
                }
                break;
            //Data from the ViewCounterActivity class
            case MODIFY_COUNTER_CODE:
                switch (resultCode) {
                    //If save, then replace the old counter in the list with the new one
                    case ViewCounterActivity.SAVE_CODE:
                        Counter oCounter = (Counter) data.getSerializableExtra("oldCounter");
                        Counter nCounter = (Counter) data.getSerializableExtra("counter");
                        for (Counter counter : counters) {
                            if (counter.isEqual(oCounter)) {
                                counters.set(counters.indexOf(counter), nCounter);
                                break;
                            }
                        };
                        adapter.notifyDataSetChanged();
                        saveToFile();
                        break;

                    //If delete, remove counter from list
                    case ViewCounterActivity.DELETE_CODE:
                        Counter counter = (Counter) data.getSerializableExtra("counter");
                        for (Counter oldCounter : counters) {
                            if (oldCounter.isEqual(counter)) {
                                counters.remove(oldCounter);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        saveToFile();
                        break;

                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    /**
     * saveToFile uses a Gson object to save Counters
     * to the file specified.
     */
    public void saveToFile() {
        try {
            FileOutputStream fos = this.openFileOutput(FILENAME, MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counters, writer);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    /**
     * loadFromFile uses a Gson object to load Counters
     * from the saved File. If no file is found,
     * initialize the list of counters.
     */
    public void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {}.getType();
            counters = gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            counters = new ArrayList<Counter>();
        }
    }

}
