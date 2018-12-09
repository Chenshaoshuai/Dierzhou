package com.example.loginlianxi.M;

import com.example.loginlianxi.CallBack.MyCallBack;

public interface IModel{
    void getRequest(String url, Class clazz, MyCallBack callBack);
}
