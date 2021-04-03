package com.example.tygx.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Abstract.class}, version = 1)
public abstract class AbstractsDb extends RoomDatabase {
    public abstract AbstractsDao abstractsDao();
}
