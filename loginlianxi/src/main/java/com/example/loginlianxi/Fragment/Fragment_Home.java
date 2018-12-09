package com.example.loginlianxi.Fragment;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.loginlianxi.Adapter.UserAdapter;
import com.example.loginlianxi.Bean.BannerBean;
import com.example.loginlianxi.P.IPersenterImpl;
import com.example.loginlianxi.R;
import com.example.loginlianxi.V.IView;
import com.example.xlistview.me.maxwin.view.XListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.List;

public class Fragment_Home extends Fragment implements IView {
    private Banner banner;
    private IPersenterImpl persenter;
    private XListView xListView;
    private UserAdapter adapter;
    private String imageUrl="http://www.xieast.com/api/banner.php";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banner =view.findViewById(R.id.banner);
        xListView =view.findViewById(R.id.xListView);
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {

            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                BannerBean.DataBean bean = (BannerBean.DataBean) path;
                com.nostra13.universalimageloader.core.ImageLoader.getInstance()
                .displayImage(bean.getImg(),imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });
        persenter = new IPersenterImpl(this);
        persenter.startRequest(imageUrl,BannerBean.class);
        adapter = new UserAdapter(getActivity());
        xListView.setAdapter(adapter);

    }

    @Override
    public void onSuccess(Object data) {
        BannerBean bannerBean = (BannerBean) data;
        List<BannerBean.DataBean> data1 = bannerBean.getData();
        banner.setImages(data1);
        banner.start();
        if(bannerBean.getCode()==1){
           adapter.setList(bannerBean.getData());
        }

    }

}
