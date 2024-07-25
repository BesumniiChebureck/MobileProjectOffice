package com.postrigan.projectoffice.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.postrigan.projectoffice.R;
import com.postrigan.projectoffice.RuntimeManager;
import com.postrigan.projectoffice.data.DBHelper;
import com.postrigan.projectoffice.data.Employee;
import com.postrigan.projectoffice.dboperations.DbEmployee;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        try {
            DBHelper helper = new DBHelper(getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();
            EditText login = findViewById(R.id.etLogin);
            EditText pass = findViewById(R.id.etPass);

            Employee employee = DbEmployee.getByCredentials(db,
                    login.getText().toString(),
                    pass.getText().toString());

            if (employee == null) {
                Toast.makeText(this,
                        "Неверный логин или пароль, пожалуйста, повторите попытку",
                        Toast.LENGTH_LONG).show();
            } else {
                RuntimeManager.employee = employee;
                Toast.makeText(this,
                        "Добро пожаловать, " + employee.getLastName() + " " + employee.getFirstName() + "!",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, TaskActivity.class);
                startActivity(intent);
            }
        } catch (Exception ex) {
            Toast.makeText(this,
                    ex.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}