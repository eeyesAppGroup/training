package com.wx.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //数据操作模式
    public static final String KEY_MODE_METHOD = "mode_method";
    //编辑模式
    public static final String KEY_MODE_EDIT = "mode_edit";
    //数据内容
    public static final String KEY_DATA = "data";
    public static final String KEY_DATA_ID = "data_id";
    public static final String KEY_DATA_NAME = "data_name";
    public static final String KEY_DATA_AGE = "data_age";
    public static final String KEY_DATA_PHONE = "data_phone";
    //自己实现数据库模式
    public static final int MODE_METHOD_SQL = 0;
    //采用ORM模式
    public static final int MODE_METHOD_ORM = 1;
    //增加数据
    public static final int MODE_EDIT_ADD = 2;
    //修改数据
    public static final int MODE_EDIT_UPDATE = 3;

    Button btnSQL, btnSet, btnORM, btnAdd;
    ListView lv;
    SimpleAdapter adapter;
    //默认模式为数据库模式
    int methodMode = MODE_METHOD_ORM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSQL = (Button) findViewById(R.id.btnSQL);
        btnSet = (Button) findViewById(R.id.btnSet);
        btnORM = (Button) findViewById(R.id.btnORM);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        lv = (ListView) findViewById(R.id.list);
        switchMode(MODE_METHOD_SQL);
        init();

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至设置页面
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至编辑页面
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(KEY_MODE_METHOD, methodMode);
                intent.putExtra(KEY_MODE_EDIT, MODE_EDIT_ADD);
                startActivityForResult(intent,0);
            }
        });
        btnSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode(MODE_METHOD_SQL);
                init();
            }
        });
        btnORM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode(MODE_METHOD_ORM);
                init();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map= (HashMap<String,String>) adapter.getItem(position);
                User user=new User(Integer.parseInt(map.get(KEY_DATA_ID)),map.get(KEY_DATA_NAME),Integer.parseInt(map.get(KEY_DATA_AGE)),map.get(KEY_DATA_PHONE));
                //跳转至编辑页面
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(KEY_MODE_METHOD, methodMode);
                intent.putExtra(KEY_MODE_EDIT, MODE_EDIT_UPDATE);
                //装载数据
                Bundle data=new Bundle();
                data.putInt(KEY_DATA_ID,user.getId());
                data.putString(KEY_DATA_NAME,user.getName());
                data.putInt(KEY_DATA_AGE,user.getAge());
                data.putString(KEY_DATA_PHONE,user.getPhone());
                intent.putExtra(KEY_DATA,data);
                startActivityForResult(intent,0);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map= (HashMap<String,String>) adapter.getItem(position);
                final User user=new User(Integer.parseInt(map.get(KEY_DATA_ID)),map.get(KEY_DATA_NAME),Integer.parseInt(map.get(KEY_DATA_AGE)),map.get(KEY_DATA_PHONE));
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setMessage("删除该条数据吗？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(methodMode){
                                    case MODE_METHOD_SQL:
                                        deleteBySQL(user);
                                        break;
                                    case MODE_METHOD_ORM:
                                        deleteByORM(user);
                                        break;
                                }
                                //删完数据后重新加载数据
                                init();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                return true;
            }
        });
    }

    //orm需要的对象
    DBHelperORM ormHelper;
    Dao<User, Integer> dao;
    //sql需要的对象
    DBHelperSQL sqlHelper;
    SQLiteDatabase dbRead;
    SQLiteDatabase dbWrite;

    //初始化
    void init() {
        btnSet.setBackgroundColor(Color.LTGRAY);
        //orm初始化
        ormHelper = new DBHelperORM(this);
        try {
            dao = ormHelper.getUserDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //sql初始化
        //1.创建工具类对象
        sqlHelper =new DBHelperSQL(this);
        //2.取得可读写的数据库对象
        dbRead= sqlHelper.getReadableDatabase();
        dbWrite=sqlHelper.getWritableDatabase();

        //取得数据库中所有数据
        List<User> userList = getUserList();
        ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
        if (userList != null) {
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_DATA_ID, user.getId()+"");
                map.put(KEY_DATA_NAME, user.getName());
                map.put(KEY_DATA_AGE, user.getAge() + "");
                map.put(KEY_DATA_PHONE, user.getPhone());
                items.add(map);
            }
        }
        adapter = new SimpleAdapter(this, items,
                R.layout.list_item, new String[]{KEY_DATA_NAME,KEY_DATA_AGE,KEY_DATA_PHONE},
                new int[]{R.id.tvName,R.id.tvAge,R.id.tvPhone});
        lv.setAdapter(adapter);
    }

    //从数据库中取得数据
    public List<User> getUserList() {
        switch (methodMode) {
            case MODE_METHOD_SQL:
                List<User> userList=new ArrayList<User>();
                //sql实现
                Cursor cursor=dbRead.query(DBHelperSQL.TABLE_NAME,null,null,null,null,null,null);
                while(cursor.moveToNext())
                {
                    int id=cursor.getInt(cursor.getColumnIndex(DBHelperSQL.FIELD_ID));
                    String name=cursor.getString(cursor.getColumnIndex(DBHelperSQL.FIELD_NAME));
                    int age=cursor.getInt(cursor.getColumnIndex(DBHelperSQL.FIELD_AGE));
                    String phone=cursor.getString(cursor.getColumnIndex(DBHelperSQL.FIELD_PHONE));
                    User user=new User(id,name,age,phone);
                    //Log.e("wanxu1", "name=" + name + "  gender=" + gender + "  age=" + age);
                    userList.add(user);
                }
                return userList;
            case MODE_METHOD_ORM:
                //orm实现
                try {
                    return dao.queryForAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
        }
        return null;
    }

    //删除某条数据SQL实现
    public void deleteBySQL(User user)
    {
        dbWrite.delete(DBHelperSQL.TABLE_NAME,"id=?",new String[]{""+user.getId()});
    }
    //删除某条数据ORM实现
    public void deleteByORM(User user){
        try {
            dao.delete(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //切换模式
    public void switchMode(int mode) {
        this.methodMode = mode;
        if (mode == MODE_METHOD_SQL) {
            btnSQL.setBackgroundColor(Color.GREEN);
            btnORM.setBackgroundColor(Color.LTGRAY);
        } else if (mode == MODE_METHOD_ORM) {
            btnSQL.setBackgroundColor(Color.LTGRAY);
            btnORM.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //重新加载数据
        init();
    }
}
