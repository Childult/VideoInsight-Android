package com.example.tygx.data.repository;

import android.content.Context;
import androidx.room.Room;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class AbstractsManager {
    private static AbstractsDb mDb;
    public static AbstractsDb getIntance(Context context) {
        if (mDb == null) {
            mDb = Room.databaseBuilder(context, AbstractsDb.class, "abstracts")
                    .allowMainThreadQueries()
                    .build();
        }
        return mDb;
    }
}
