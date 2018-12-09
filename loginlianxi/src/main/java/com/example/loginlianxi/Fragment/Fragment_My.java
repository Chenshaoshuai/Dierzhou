package com.example.loginlianxi.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loginlianxi.Activity.ScanActivity;
import com.example.loginlianxi.Activity.SucceedActivity;
import com.example.loginlianxi.R;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class Fragment_My extends Fragment {
    private ImageView imageView;
    private Button btn_scan;
    private String getrequest;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        return inflater.inflate(R.layout.fragment_my,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
          imageView = view.findViewById(R.id.qrimage);
          btn_scan = view.findViewById(R.id.btn_scan);
         getrequest = ((SucceedActivity) getActivity()).getrequest();
        createQRCode();
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

    }

    private void createQRCode() {

        QRTask qrTask = new QRTask(getActivity(),imageView,getrequest);
        qrTask.execute(getrequest);

    }

    private void checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},100);
            }else{
                startActivity(new Intent(getActivity(), ScanActivity.class));
            }
        }else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},100);
            startActivity(new Intent(getActivity(), ScanActivity.class));
        }


    }


    private class QRTask extends AsyncTask<String,Void,Bitmap>{
        private WeakReference<Context> mContext;
        private WeakReference<ImageView> imageView;

        public QRTask(Context context, ImageView imageView1, String getrequest) {
            mContext = new WeakReference<>(context);
            imageView = new WeakReference<>(imageView1);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str = strings[0];
            if (TextUtils.isEmpty(str)){
                return null;
            }
            int size = mContext.get().getResources().getDimensionPixelSize(R.dimen.qr_code_size);
            return QRCodeEncoder.syncEncodeQRCode(str,size);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null){
                imageView.get().setImageBitmap(bitmap);
            }else{
                Toast.makeText(mContext.get(), "生成失败", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
