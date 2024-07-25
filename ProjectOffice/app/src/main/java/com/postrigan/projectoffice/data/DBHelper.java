package com.postrigan.projectoffice.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "projectoffice.db";
    private static final String CMD_PREFIX = "create table if not exists ";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Сотрудник (пользователь)
        db.execSQL(CMD_PREFIX + "employee (" +
                "_id integer primary key autoincrement," +
                "last_name text," +
                "first_name text," +
                "login text," +
                "phone_num text," +
                "password text);");

        //Приоритет выполнения задачи
        db.execSQL(CMD_PREFIX + "priority (" +
                "_id integer primary key autoincrement," +
                "name text);");

        //Статус задачи
        db.execSQL(CMD_PREFIX + "status (" +
                "_id integer primary key autoincrement," +
                "name text);");

        //Задача
        db.execSQL(CMD_PREFIX + "task (" +
                "_id integer primary key autoincrement," +
                "priority_id integer references priority(_id)," +
                "name text," +
                "start_date text," +
                "end_date text," +
                "employee_id integer references employee(_id)," +
                "status_id integer references status(_id));");

        //Заявка
        db.execSQL(CMD_PREFIX + "bid (" +
                "_id integer primary key autoincrement," +
                "task_id integer references task(_id)," +
                "bid_date text," +
                "employee_id integer references employee(_id));");

        //Тестовые данные сотрудников
        db.execSQL("insert into employee (last_name, first_name, " +
                "login, phone_num, password) values " +
                "('Иванов', 'Иван', 'ivanov', '89579162807', '1234')," +
                "('Сидоров', 'Николай', 'sidorov', '89213445712', '4321')");

        //Данные приоритетов
        db.execSQL("insert into priority (name) values " +
                "('Высокий')," +
                "('Средний')," +
                "('Низкий')");

        //Данные статусов
        db.execSQL("insert into status (name) values " +
                "('В работе')," +
                "('Не выполняется')," +
                "('На проверке')," +
                "('Выполнена')");

        //Тестовые данные задач
        db.execSQL("insert into task (" +
                "priority_id, name, start_date, " +
                "end_date, employee_id, status_id) values " +
                "(1, 'ГКПД', '2024-06-15', null, 1, 1)," +
                "(2, 'ДПЧР', null, null, 2, 2)," +
                "(2, 'ЗДЖО', '2024-06-16', null, 2, 1)," +
                "(3, 'СТБУХ', '2024-06-14', null, 1, 3)," +
                "(2, 'ОВРД', '2024-06-15', null, 1, 3)," +
                "(1, 'СТН', '2024-06-11', '2024-06-16', 2, 4)," +
                "(2, 'СТН2', '2024-06-14', '2024-06-18', 1, 4)," +
                "(1, 'ДВТ', '2024-06-06', '2024-06-12', 2, 4)," +
                "(3, 'КГТА', '2024-06-17', null, 2, 1)," +
                "(1, 'ЗБРХ', null, null, 1, 2)");

        //Тестовые данные заявок (по тестовым данным задач)
        db.execSQL("insert into bid (task_id, bid_date, employee_id) values " +
                "(4, '2024-06-17', 1)," +
                "(5, '2024-06-18', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
