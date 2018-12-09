package com.example.loginlianxi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginlianxi.Bean.RegBean;
import com.example.loginlianxi.P.IPersenterImpl;
import com.example.loginlianxi.R;
import com.example.loginlianxi.V.IView;

public class RegActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private Button btn_register,btn_back;
    private EditText edit_regname,edit_regpass;
    private String name,pass;
    private IPersenterImpl persenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        btn_back = findViewById(R.id.btn_back);
        btn_register = findViewById(R.id.btn_register);
        edit_regname = findViewById(R.id.edit_regname);
        edit_regpass = findViewById(R.id.edit_regpass);
        persenter = new IPersenterImpl(this);
        btn_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btn_register:
              name = edit_regname.getText().toString();
              pass = edit_regpass.getText().toString();
              persenter.startRequest("http://120.27.23.105/user/reg?mobile="+name+"&password="+pass,RegBean.class);
              break;
          case R.id.btn_back:
              Intent intent = new Intent(RegActivity.this,MainActivity.class);
              startActivity(intent);
              break;
              default:
                  break;
      }
    }

    @Override
    public void onSuccess(Object data) {
      RegBean regBean = (RegBean) data;
      if(regBean.getCode().equals("0")){
          Toast.makeText(RegActivity.this,regBean.getMsg()+"",Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(RegActivity.this,MainActivity.class);
          startActivity(intent);
      }else{
          Toast.makeText(RegActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        persenter.detach();
    }
}
