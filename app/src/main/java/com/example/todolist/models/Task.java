package com.example.todolist.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class Task implements Parcelable {

    private String description;
    private boolean isDone;
    private long date;

    public Task() {
    }

    public Task(String description, long date) {
        this.description = description;
        this.date = date;
    }

    protected Task(Parcel in) {
        description = in.readString();
        isDone = in.readByte() != 0;
        date = in.readLong();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeByte((byte) (isDone ? 1 : 0));
        parcel.writeLong(date);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj != null) {
            return ((Task) obj).getDate() == this.date;
        }
        return false;
    }
}
