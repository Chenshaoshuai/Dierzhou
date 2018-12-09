package com.example.loginlianxi.M;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.loginlianxi.CallBack.MyCallBack;
import com.example.loginlianxi.Utils.HttpUtils;
import com.google.gson.Gson;

public class IModelImpl implements IModel {

    private <T> T reQuest(String url,Class clazz) {
        return (T) new Gson().fromJson(HttpUtils.getRequest(url),clazz);

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getRequest(String url, final Class clazz, final MyCallBack callBack) {
         new AsyncTask<String,Void,Object>(){

             @Override
             protected Object doInBackground(String... strings) {
                 return reQuest(strings[0],clazz);
             }

             @Override
             protected void onPostExecute(Object o) {
                 super.onPostExecute(o);
                 callBack.onSuccess(o);
             }
         }.execute(url);
    }


}
