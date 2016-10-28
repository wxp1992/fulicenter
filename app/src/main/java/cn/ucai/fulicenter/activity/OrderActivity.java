package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.NetDao;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.ResultUtils;
import cn.ucai.fulicenter.view.DisplayUtils;

public class OrderActivity extends BaseActivity {
    private static final String TAG = OrderActivity.class.getSimpleName();
    @BindView(R.id.ed_order_name)
    EditText mEdOrderName;
    @BindView(R.id.ed_order_phone)
    EditText mEdOrderPhone;
    @BindView(R.id.ed_order_street)
    EditText mEdOrderStreet;
    @BindView(R.id.tv_order_price)
    TextView mTvOrderPrice;
    @BindView(R.id.sp_order_province)
    Spinner mSpOrderProvinc;

    OrderActivity mContext;
    UserAvatar user = null;
    String cartIds = "";
    ArrayList<CartBean> mList = null;
    String[] ids = new String[]{};
    int rankPrice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext,getString(R.string.confirm_order));
    }

    @Override
    protected void initData() {
        cartIds=getIntent().getStringExtra(I.Cart.ID);
        user = FuLiCenterApplication.getUser();
        L.e(TAG,"cartIds="+cartIds);
        if (cartIds == null || cartIds.equals("")||user==null) {
            finish();
        }
        ids = cartIds.split(",");
        getOrderList();
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.tv_order_buy)
    public void onClick() {
        String receiveName=mEdOrderName.getText().toString();
        if (TextUtils.isEmpty(receiveName)){
            mEdOrderName.setError("收货人不能空");
            mEdOrderName.requestFocus();
            return;
        }
        String mobile=mEdOrderPhone.getText().toString();
        if (TextUtils.isEmpty(mobile)){
            mEdOrderPhone.setError("手机号码不能空");
            mEdOrderPhone.requestFocus();
            return;
        }
        if (!mobile.matches("[\\d]{11}")){
            mEdOrderPhone.setError("手机格式错误");
            mEdOrderPhone.requestFocus();
            return;
        }
        String area=mSpOrderProvinc.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)){
            Toast.makeText(OrderActivity.this,"收货地址不能空",Toast.LENGTH_SHORT).show();
            return;
        }
        String street=mEdOrderStreet.getText().toString();
        if (TextUtils.isEmpty(street)){
            mEdOrderStreet.setError("街道地址不能空");
            mEdOrderStreet.requestFocus();
            return;
        }
        gotoStatements();
    }

    private void gotoStatements() {
        L.e(TAG,"rankPrice="+rankPrice);
    }

    private void getOrderList() {
        NetDao.downloadCart(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                L.e("1111111111List==="+list);
                if (list == null || list.size() == 0) {
                    finish();
                } else {
                    mList.addAll(list);
                    sumPrice();
                } 
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void sumPrice() {
        rankPrice = 0;
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                for (String id : ids) {
                    if (id.equals(String.valueOf(c.getId()))) {
                        rankPrice += getPrice(c.getGoods().getRankPrice()) * c.getCount();
                    }
                }
            }
        }
        mTvOrderPrice.setText("合计：￥"+Double.valueOf(rankPrice));
    }
    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }
}
