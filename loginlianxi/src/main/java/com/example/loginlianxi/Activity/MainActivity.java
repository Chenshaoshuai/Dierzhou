package com.example.loginlianxi.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginlianxi.Bean.LogBean;
import com.example.loginlianxi.P.IPersenterImpl;
import com.example.loginlianxi.R;
import com.example.loginlianxi.V.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private Button btn_log,btn_reg,btn_qq;
    private EditText edit_name,edit_pass;
    private String name,pwd;
    private IPersenterImpl persenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_log = findViewById(R.id.btn_log);
        btn_reg =findViewById(R.id.btn_reg);
        btn_qq= findViewById(R.id.btn_qq);
        edit_name = findViewById(R.id.edit_name);
        edit_pass = findViewById(R.id.edit_pwd);
        persenter = new IPersenterImpl(this);
        btn_reg.setOnClickListener(this);
        btn_log.setOnClickListener(this);
        btn_qq.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btn_log:
                 name = edit_name.getText().toString();
                 pwd = edit_pass.getText().toString();
                 persenter.startRequest("http://120.27.23.105/user/login?mobile="+name+"&password="+pwd,LogBean.class);
               break;
           case R.id.btn_reg:
                Intent intent = new Intent(MainActivity.this,RegActivity.class);
                startActivity(intent);
               break;
           case R.id.btn_qq:
               UMShareConfig config = new UMShareConfig();
               config.isNeedAuthOnGetUserInfo(true);
               UMShareAPI.get(MainActivity.this).setShareConfig(config);
               UMShareAPI umShareAPI =  UMShareAPI.get(MainActivity.this);
               umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                   @Override
                   public void onStart(SHARE_MEDIA share_media) {

                   }

                   @Override
                   public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                       Log.i("css", map+"");
                       String name = map.get("name");
                       Intent intent = new Intent(MainActivity.this, SucceedActivity.class);
                       intent.putExtra("name",name);
                       startActivity(intent);
                   }

                   @Override
                   public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                   }

                   @Override
                   public void onCancel(SHARE_MEDIA share_media, int i) {

                   }
               });

               break;
           default:
               break;
       }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onSuccess(Object data) {
            LogBean logBean = (LogBean) data;
            String code = logBean.getCode();
           if (code.equals("0")){
               Toast.makeText(MainActivity.this,logBean.getMsg()+"",Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(MainActivity.this, SucceedActivity.class);
               startActivity(intent);
           }else {
               Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
           }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        persenter.detach();
    }
}
