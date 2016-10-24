package cn.ucai.fulicenter.activity;

import android.graphics.ImageFormat;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final int sleepTime = 2000;
    SplashActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserAvatar user = FuLiCenterApplication.getUser();
                L.e(TAG, "fulicenter,user=" + user);
                if(user==null){
                    UserDao dao = new UserDao(mContext);
                    user = dao.getUser("a9527010");
                    L.e(TAG, "database,user=" + user);
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                L.e("gotoMainactivity");
                    finish();
            }
    },sleepTime);
}
}
