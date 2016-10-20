package com.wx.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SettingsActivity extends AppCompatActivity {
    CheckBox cbShow;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        cbShow= (CheckBox) findViewById(R.id.cbShow);
        //初始化checkBox的状态
        cbShow.setChecked(WelcomeActivity.isShowWelcome(this));

        btnSave= (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击按钮后读取checkBox的状态并保存
                saveShowState(cbShow.isChecked());
                SettingsActivity.this.finish();
            }
        });

    }
    //保存欢迎页面的展示状态
    public void saveShowState(boolean state)
    {
        //1.取得SP对象
        SharedPreferences sp=getSharedPreferences(WelcomeActivity.APP_NAME,MODE_PRIVATE);
        //2.通过SP对象的edit()取得Editor对象
        SharedPreferences.Editor editor=sp.edit();
        //3.按键值存储数据
        editor.putBoolean(WelcomeActivity.KEY_SHOW_WELCOME,state);
        //4.存储完后一定要记得提交
        editor.commit();
    }
}
