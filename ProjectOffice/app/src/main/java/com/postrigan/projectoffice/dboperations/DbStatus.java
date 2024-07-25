package com.postrigan.projectoffice.dboperations;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.postrigan.projectoffice.data.Status;

import java.util.ArrayList;
public class DbStatus {
    public static @Nullable Status getById(SQLiteDatabase db, int _id) {
        Cursor result = db.rawQuery(
                "select * from status where _id = " + _id, null);

        if (!result.moveToNext()) {
            return null;
        }

        return new Status(result.getInt(0),
                result.getString(1));
    }

    public static @Nullable ArrayList<Status> getAll(SQLiteDatabase db) {
        Cursor result = db.rawQuery(
                "select * from status", null);

        ArrayList<Status> all = new ArrayList<>();

        if (result.getCount() == 0) {
            return null;
        }

        while (result.moveToNext()) {
            all.add(new Status(result.getInt(0),
                    result.getString(1)));
        }

        return all;
    }
}
