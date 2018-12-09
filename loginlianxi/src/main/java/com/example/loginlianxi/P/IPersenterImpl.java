package com.example.loginlianxi.P;

import com.example.loginlianxi.CallBack.MyCallBack;
import com.example.loginlianxi.M.IModelImpl;
import com.example.loginlianxi.V.IView;

public class IPersenterImpl implements IPersenter {
    private IView iView;
    private IModelImpl model;

    public IPersenterImpl(IView iView) {
        this.iView = iView;
        model = new IModelImpl();
    }
    public void detach(){
        iView = null;
        model = null;
    }

    @Override
    public void startRequest(String urlStr, Class clazz) {
        model.getRequest(urlStr, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object o) {
                iView.onSuccess(o);
            }
        });
    }
}
