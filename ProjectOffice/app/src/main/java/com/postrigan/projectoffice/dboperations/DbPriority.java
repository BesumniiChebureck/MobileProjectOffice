package com.postrigan.projectoffice.dboperations;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.postrigan.projectoffice.data.Priority;

import java.util.ArrayList;

public class DbPriority {
    public static @Nullable Priority getById(SQLiteDatabase db, int _id) {
        Cursor result = db.rawQuery(
                "select * from priority where _id = " + _id, null);

        if (!result.moveToNext()) {
            return null;
        }

        return new Priority(result.getInt(0),
                result.getString(1));
    }

    public static @Nullable ArrayList<Priority> getAll(SQLiteDatabase db) {
        Cursor result = db.rawQuery(
                "select * from priority", null);

        ArrayList<Priority> all = new ArrayList<>();

        if (result.getCount() == 0) {
            return null;
        }

        while (result.moveToNext()) {
            all.add(new Priority(result.getInt(0),
                    result.getString(1)));
        }

        return all;
    }
}
