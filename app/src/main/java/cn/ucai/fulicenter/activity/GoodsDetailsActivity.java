package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.NetDao;
import cn.ucai.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/18 0018.
 */

public class GoodsDetailsActivity extends AppCompatActivity {
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
    cn.ucai.fulicenter.view.SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    cn.ucai.fulicenter.view.FlowIndicator indicator;
    @BindView(R.id.goodbref)
    WebView goodbref;
    int goodsId;
    GoodsDetailsActivity mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details", "goodsid" + goodsId);
        if (goodsId == 0) {
            finish();
        }
        mContext = this;
        initView();
        initData();
        setListener();
    }

    private void setListener() {
    }

    private void initData() {
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
                salv.startPlayLoop(indicator,getAlbumImgUrl(details),getAlbumImgCount(details));
                goodbref.loadDataWithBaseURL(null,details.getGoodsBrief(),I.TEXT_HTML,I.UTF_8,null);
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
                    for (int i=0;i<albums.length;i++) {
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

    private void initView() {
    }

    @OnClick(R.id.backClickArea)
    public void onBackClick(){
        MFGT.finish(this);
    }
    public void onBackPressed(){
        MFGT.finish(this);
    }
}
