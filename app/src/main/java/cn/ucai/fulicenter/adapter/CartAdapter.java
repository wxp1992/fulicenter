package cn.ucai.fulicenter.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.NetDao;
import cn.ucai.fulicenter.utils.OkHttpUtils;


public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_cart, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        final CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods=cartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext,holder.mIvGoodThumb,goods.getGoodsThumb());
            holder.mTvGoodName.setText(goods.getGoodsName());
            holder.mTvGoodPrice.setText(goods.getCurrencyPrice());
        }
        holder.mTvCartCount.setText("("+cartBean.getCount()+")");
        holder.mCbSelect.setChecked(cartBean.isChecked());
        holder.mCbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                cartBean.setChecked(b);
                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
            }
        });
        holder.mIvAddCart.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<CartBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    class CartViewHolder extends ViewHolder {
        @BindView(R.id.cbSelect)
        CheckBox mCbSelect;
        @BindView(R.id.ivGoodThumb)
        ImageView mIvGoodThumb;
        @BindView(R.id.tvGoodName)
        TextView mTvGoodName;
        @BindView(R.id.ivAddCart)
        ImageView mIvAddCart;
        @BindView(R.id.tvCartCount)
        TextView mTvCartCount;
        @BindView(R.id.ivReduceCart)
        ImageView mIvReduceCart;
        @BindView(R.id.tvGoodPrice)
        TextView mTvGoodPrice;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.ivGoodThumb, R.id.tvGoodName, R.id.tvGoodPrice})
        public void gotoDetail() {
            final int position = (int) mIvAddCart.getTag();
            CartBean cart = mList.get(position);
            MFGT.gotoGoodsDetailsActivity(mContext,cart.getGoodsId());
        }
        @OnClick(R.id.ivAddCart)
        public void addCart() {
            final int position = (int) mIvAddCart.getTag();
            CartBean cart = mList.get(position);
            NetDao.updateCart(mContext, cart.getId(), cart.getCount() + 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        mList.get(position).setCount(mList.get(position).getCount()+1);
                        mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                        mTvCartCount.setText("("+(mList.get(position).getCount())+")");

                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }

        @OnClick(R.id.ivReduceCart)
        public void delCart() {
            final int position = (int) mIvAddCart.getTag();
            CartBean cart = mList.get(position);
            if (cart.getCount() > 1) {
                NetDao.updateCart(mContext, cart.getId(), cart.getCount() - 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            mList.get(position).setCount(mList.get(position).getCount() - 1);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            mTvCartCount.setText("(" + (mList.get(position).getCount()) + ")");

                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            } else {
                    NetDao.deleteCart(mContext, cart.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null && result.isSuccess()) {
                                mList.remove(position);
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
            }
        }
    }

}
