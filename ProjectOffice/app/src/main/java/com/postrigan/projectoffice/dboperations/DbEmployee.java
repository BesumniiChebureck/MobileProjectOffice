package com.postrigan.projectoffice.dboperations;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.postrigan.projectoffice.data.Employee;
import com.postrigan.projectoffice.data.Priority;

import java.util.ArrayList;

public class DbEmployee {

    public static Employee getByLogin(SQLiteDatabase db, String login) {
        Cursor result = db.rawQuery(
                "select * from employee where login = '" + login + "'", null);

        if (!result.moveToNext()) {
            return null;
        }

        return new Employee(result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getString(3),
                result.getString(4));
    }

    public static Employee getById(SQLiteDatabase db, int _id) {
        Cursor result = db.rawQuery(
                "select * from employee where _id = " + _id, null);

        if (!result.moveToNext()) {
            return null;
        }

        return new Employee(result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getString(3),
                result.getString(4));
    }

    public static @Nullable ArrayList<Employee> getAll(SQLiteDatabase db) {
        Cursor result = db.rawQuery(
                "select * from employee", null);

        ArrayList<Employee> all = new ArrayList<>();

        if (result.getCount() == 0) {
            return null;
        }

        while (result.moveToNext()) {
            all.add(new Employee(result.getInt(0),
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)));
        }

        return all;
    }

    public static @Nullable Employee getByCredentials(SQLiteDatabase db, String login, String password) {
        Cursor result = db.rawQuery(
                "select * from employee where login = '" + login
                        + "' and password = '" + password + "'", null);

        if (!result.moveToNext()) {
            return null;
        }

        return new Employee(result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getString(3),
                result.getString(4));
    }

    public static int createNew(SQLiteDatabase dbWritable, SQLiteDatabase dbReadable,
                                String lastName, String firstName,
                                String phoneNum, String login, String password) {
        if (getByLogin(dbReadable, login) != null) {
            return 1;
        } else {
            dbWritable.execSQL("insert into employee (last_name, first_name, " +
                    "login, phone_num, password) values " +
                    "('" + lastName + "', '" + firstName + "', '" + login +
                    "', '" + phoneNum + "', '" + password + "')");

            return 0;
        }
    }
}
