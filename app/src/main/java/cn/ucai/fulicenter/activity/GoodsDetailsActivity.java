package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.NetDao;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.SlideAutoLoopView;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2016/10/18 0018.
 */

public class GoodsDetailsActivity extends BaseActivity {
    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.tvgoodenglish)
    TextView tvgoodenglish;
    @BindView(R.id.tvgoodname)
    TextView tvgoodname;
    @BindView(R.id.tvgoodpriceshop)
    TextView tvgoodpriceshop;
    @BindView(R.id.tvgoodpricecurrent)
    TextView tvgoodpricecurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    FlowIndicator indicator;
    @BindView(R.id.goodbref)
    WebView goodbref;
    int goodsId;
    GoodsDetailsActivity mContext;
    boolean isCollected = false;
    @BindView(R.id.ivcollect)
    ImageView mIvcollect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details", "goodsid" + goodsId);
        if (goodsId == 0) {
            finish();
        }
        mContext = this;
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {
        NetDao.downloadGoodsDetail(mContext, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details=" + result);
                if (result != null) {
                    showGoodDetails(result);
                } else {
                    finish();
                }
            }

            private void showGoodDetails(GoodsDetailsBean details) {
                tvgoodenglish.setText(details.getGoodsEnglishName());
                tvgoodname.setText(details.getGoodsName());
                tvgoodpricecurrent.setText(details.getCurrencyPrice());
                tvgoodpriceshop.setText(details.getShopPrice());
                salv.startPlayLoop(indicator, getAlbumImgUrl(details), getAlbumImgCount(details));
                goodbref.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
            }

            private int getAlbumImgCount(GoodsDetailsBean details) {
                if (details.getProperties() != null && details.getProperties().length > 0) {
                    return details.getProperties()[0].getAlbums().length;
                }
                return 0;
            }

            private String[] getAlbumImgUrl(GoodsDetailsBean details) {
                String[] urls = new String[]{};
                if (details.getProperties() != null && details.getProperties().length > 0) {
                    AlbumsBean[] albums = details.getProperties()[0].getAlbums();
                    urls = new String[albums.length];
                    for (int i = 0; i < albums.length; i++) {
                        urls[i] = albums[i].getImgUrl();
                    }
                }
                return urls;
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details,error=" + error);
                CommonUtils.showShortToast(error);
            }
        });
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCollected();
    }

    @OnClick(R.id.backClickArea)
    public void onBackClick() {
        MFGT.finish(this);
    }

    public void onBackPressed() {
        MFGT.finish(this);
    }

    @OnClick(R.id.ivcollect)
    public void onCollectClick() {
        UserAvatar user = FuLiCenterApplication.getUser();
        if (user == null) {
            MFGT.gotoLogin(mContext);
        } else {
            if (isCollected) {
                NetDao.deleteCollect(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollected = !isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            } else {
                NetDao.addCollect(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollected = !isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }
    }

    private void isCollected() {
        UserAvatar user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.isCollected(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollected = true;
                    } else {
                        isCollected = false;
                    }
                    updateGoodsCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollected = false;
                    updateGoodsCollectStatus();
                }
            });
        }
        updateGoodsCollectStatus();
    }

    private void updateGoodsCollectStatus() {
        if (isCollected) {
            mIvcollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            mIvcollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }
    @OnClick(R.id.ivgoodshare)
    public void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}
