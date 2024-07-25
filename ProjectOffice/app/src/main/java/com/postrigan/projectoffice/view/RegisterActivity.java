package com.postrigan.projectoffice.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.postrigan.projectoffice.R;
import com.postrigan.projectoffice.data.DBHelper;
import com.postrigan.projectoffice.dboperations.DbEmployee;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        try{
            EditText lastName = findViewById(R.id.etLastName);
            EditText firstName = findViewById(R.id.etFirstName);
            EditText login = findViewById(R.id.etLogin);
            EditText phoneNum = findViewById(R.id.etPhoneNum);
            EditText password = findViewById(R.id.etPassword);
            DBHelper helper = new DBHelper(getApplicationContext());
            SQLiteDatabase dbWritable = helper.getWritableDatabase();
            SQLiteDatabase dbReadable = helper.getReadableDatabase();

            int status = DbEmployee.createNew(dbWritable, dbReadable,
                    lastName.getText().toString(),
                    firstName.getText().toString(), phoneNum.getText().toString(),
                    login.getText().toString(), password.getText().toString());

            if (status != 0) {
                Toast.makeText(this,
                        "Пользователь с таким логином уже существует",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        "Вы успешно зарегистрировались! Теперь войдите в аккаунт",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } catch (Exception ex){
            Toast.makeText(this,
                    ex.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
}