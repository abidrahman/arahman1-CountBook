/**
 * CounterAdapter class
 *
 * Copyright 2017 Abid Rahman
 *
 * @author arahman1
 * @version 1.0
 * @created 2017-09-30
 */

package com.abidrahman.arahman1_countbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * CounterAdapter is a custom ArrayAdapter for Counters.
 * It is responsible for attaching to a ListView and updating
 * the fields of the ItemViews within the ListView.
 * This class also handles the button clicks on the ItemViews
 * that it is attached to.
 */
public class CounterAdapter extends ArrayAdapter<Counter> {

    private final Context context;
    private final ArrayList<Counter> counterList;
    private final CountBookActivity activity;

    private HashMap<String, TextView> viewHolder;

    /**
     * Constructor for the CounterAdapter.
     * @param context, the parent context
     * @param counterList, the list of data used
     */
    public CounterAdapter(Context context, ArrayList<Counter> counterList) {
        super(context, 0, counterList);

        this.context = context;
        this.activity = (CountBookActivity) context;
        this.counterList = counterList;
        this.viewHolder = new HashMap<String, TextView>();
    }

    /**
     * getView attaches handlers for all buttons on the item_counter
     * view and updates each field according to the set of data
     * used in this adapter
     * @param position, the location of the view
     * @param converView, the view itself
     * @param parent, the parent viewgroup
     */
    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View counterView;

        //Get an instance of the item counter view elements
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        counterView = inflater.inflate(R.layout.item_counter, parent, false);

        viewHolder.put("name", (TextView) counterView.findViewById(R.id.counter_title));
        viewHolder.put("date", (TextView) counterView.findViewById(R.id.counter_date));
        viewHolder.put("value", (TextView) counterView.findViewById(R.id.counter_value));
        viewHolder.put("comment", (TextView) counterView.findViewById(R.id.counter_comment));

        //Handle ^, v, and EDIT buttons for individual counters
        Button upButton = (Button) counterView.findViewById(R.id.counter_up);
        Button downButton = (Button) counterView.findViewById(R.id.counter_down);
        Button editButton = (Button) counterView.findViewById(R.id.counter_edit);
        Button resetButton = (Button) counterView.findViewById(R.id.counter_reset);
        upButton.setTag(position);
        downButton.setTag(position);
        editButton.setTag(position);
        resetButton.setTag(position);

        //Up button
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Counter counter = getItem((int) view.getTag());
                counter.increment();
                notifyDataSetChanged();
            }
        });

        //Down button
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Counter counter = getItem((int) view.getTag());
                counter.decrement();
                notifyDataSetChanged();
            }
        });

        //Edit button
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Counter counter = getItem((int) view.getTag());
                Intent editCounterIntent = new Intent(context, ViewCounterActivity.class);
                editCounterIntent.putExtra("counter", counter);
                activity.startActivityForResult(editCounterIntent, CountBookActivity.MODIFY_COUNTER_CODE);
            }
        });

        //Reset Button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Counter counter = getItem((int) view.getTag());
                counter.reset();
                notifyDataSetChanged();
            }
        });

        //Update all fields
        Counter counter = counterList.get(position);
        viewHolder.get("name").setText(counter.getName());
        viewHolder.get("date").setText(counter.getDate());
        viewHolder.get("value").setText(String.format("%s", (int)counter.getCurrent_value()));
        viewHolder.get("comment").setText(counter.getComment());

        return counterView;
    }
}
