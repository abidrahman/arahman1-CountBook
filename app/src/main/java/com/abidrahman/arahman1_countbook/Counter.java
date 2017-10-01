/**
 * Counter class
 *
 * Copyright 2017 Abid Rahman
 *
 * @author arahman1
 * @version 1.0
 * @created 2017-09-30
 */

package com.abidrahman.arahman1_countbook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Counter class is the object class representing a counter.
 * It holds data for a specific counter, such as name,
 * comment, date, initial value, and current value.
 * This class also implements Serializable in order to be
 * passed through Intents to other Activities.
 */
public class Counter implements Serializable {
    private String name;
    private Date date;
    private Integer initial_value;
    private Integer current_value;
    private String comment;

    /**
     * Constructor for the Counter. The Date is set
     * automatically and the current value is set to
     * equal the initial value.
     * @param name, the name of the Counter
     * @param initial_value, the initial value of the Counter
     * @param comment, the comment of the Counter
     */
    public Counter(String name, Integer initial_value, String comment) {
        this.initial_value = initial_value;
        this.current_value = initial_value;
        this.name = name;
        this.comment = comment;
        this.date = new Date();
    }

    /**
     * increment, increments the current value by 1.
     */
    public void increment() {
        this.current_value += 1;
    }

    /**
     * decrement, decrements the current value by 1.
     * The minimum is 0, current value cannot be negative.
     */
    public void decrement() {
        if (0 < this.current_value ) {
            this.current_value -= 1;
        }
    }

    /**
     * reset, set current value to initial value.
     */
    public void reset() {
        this.current_value = this.initial_value;
    }

    /**
     * getName, get name of Counter
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setName, set name of Counter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getDate, get date of Counter in yyyy-MM-dd format
     * @return date
     */
    public String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this.date);
    }

    /**
     * getInitial_value, get initial value of Counter
     * @return initial_value
     */
    public Integer getInitial_value() { return initial_value; }

    /**
     * setInitial_value, set initial value of Counter
     * @param initial_value
     */
    public void setInitial_value(Integer initial_value) { this.initial_value = initial_value; }

    /**
     * getCurrent_value, get current value of Counter
     * @return current_value
     */
    public Integer getCurrent_value() {
        return current_value;
    }

    /**
     * setCurrent_value, set current value of Counter
     * @param current_value
     */
    public void setCurrent_value(Integer current_value) {
        this.current_value = current_value;
    }

    /**
     * getComment, get comment of Counter
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * setComment, set comment of Counter
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * isEqual, checks if two counters have same values
     * @return true, if same, else returns false
     */
    public boolean isEqual(Counter counter) {
        if ((this.name.equals(counter.name)) &&
                (0 == this.date.compareTo(counter.date)) &&
                (0 == this.initial_value.compareTo(counter.initial_value)) &&
                (0 == this.current_value.compareTo(counter.current_value)) &&
                (this.comment.equals(counter.comment))) {
            return true;
        } else { return false; }
    }
}
