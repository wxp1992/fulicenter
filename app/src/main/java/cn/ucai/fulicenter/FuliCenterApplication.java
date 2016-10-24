package cn.ucai.fulicenter;

import android.app.Application;

import cn.ucai.fulicenter.bean.UserAvatar;

/**
 * Created by Administrator on 2016/10/17 0017.
 */

public class FuLiCenterApplication extends Application {
    public static UserAvatar getUser() {
        return user;
    }

    public static void setUser(UserAvatar user) {
        FuLiCenterApplication.user = user;
    }

    public static FuLiCenterApplication application;
    private static FuLiCenterApplication instance;

    public static String username;
    private static UserAvatar user;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        instance = this;
    }

    public static FuLiCenterApplication getInstance(){
        if (instance == null) {
            instance = new FuLiCenterApplication();
        }
        return instance;
    }


}
