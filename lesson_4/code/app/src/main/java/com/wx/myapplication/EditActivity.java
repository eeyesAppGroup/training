package com.wx.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class EditActivity extends AppCompatActivity {
    EditText etName, etAge, etPhone;
    Button btnSave;
    int methodMode;
    int editMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etPhone = (EditText) findViewById(R.id.etPhone);
        btnSave = (Button) findViewById(R.id.btnSave);
        init();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                int age = Integer.parseInt(etAge.getText().toString());
                String phone = etPhone.getText().toString();
                switch (methodMode) {
                    case MainActivity.MODE_METHOD_SQL:
                        saveDataBySQL(name, age, phone);
                        break;
                    case MainActivity.MODE_METHOD_ORM:
                        saveDataByORM(name, age, phone);
                        break;
                    default:
                        Toast.makeText(EditActivity.this, "error with method mode", Toast.LENGTH_SHORT).show();
                }
                EditActivity.this.finish();
            }
        });
    }

    //接收上个页面发送的数据并用于初始化本页面
    int userID;

    //初始化
    void init() {
        Intent intent = getIntent();
        methodMode = intent.getIntExtra(MainActivity.KEY_MODE_METHOD, -1);
        editMode = intent.getIntExtra(MainActivity.KEY_MODE_EDIT, -1);
        if (editMode == MainActivity.MODE_EDIT_UPDATE) {
            Bundle data = intent.getBundleExtra(MainActivity.KEY_DATA);
            userID = data.getInt(MainActivity.KEY_DATA_ID);
            String name = data.getString(MainActivity.KEY_DATA_NAME);
            int age = data.getInt(MainActivity.KEY_DATA_AGE);
            String phone = data.getString(MainActivity.KEY_DATA_PHONE);
            etName.setText(name);
            etAge.setText(age + "");
            etPhone.setText(phone);
        }
    }

    //插入与修改数据的SQL实现
    void saveDataBySQL(String name, int age, String phone) {
        //sql初始化
        //1.创建工具类对象
        DBHelperSQL sqlHelper =new DBHelperSQL(this);
        //2.取得可写的数据库对象
        SQLiteDatabase dbWrite= sqlHelper.getWritableDatabase();
        //3.1构造一条数据
        ContentValues cv=new ContentValues();
        cv.put(DBHelperSQL.FIELD_NAME,name);
        cv.put(DBHelperSQL.FIELD_AGE,age);
        cv.put(DBHelperSQL.FIELD_PHONE,phone);
        switch (editMode){
            case MainActivity.MODE_EDIT_ADD:
                //3.2调用insert方法写入数据库
                dbWrite.insert(DBHelperSQL.TABLE_NAME,null,cv);
                break;
            case MainActivity.MODE_EDIT_UPDATE:
                //3.3调用update方法更新数据
                dbWrite.update(DBHelperSQL.TABLE_NAME,cv,"id=?",new String[]{""+userID});
                break;
        }
        dbWrite.close();
        sqlHelper.close();
    }


    DBHelperORM helper;
    Dao<User, Integer> dao;

    //插入与修改数据的ORM实现
    void saveDataByORM(String name, int age, String phone) {
        helper = new DBHelperORM(this);
        try {
            dao = helper.getUserDao();
            User user = new User(name, age, phone);
            switch (editMode) {
                case MainActivity.MODE_EDIT_ADD:
                    dao.create(user);
                    break;
                case MainActivity.MODE_EDIT_UPDATE:
                    user.setId(userID);
                    dao.update(user);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("wanxu", "Fail to get UserDao");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null)
            helper = null;
        if (dao != null)
            dao = null;
    }
}
