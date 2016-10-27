package cn.ucai.fulicenter.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;


public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_cart, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods=cartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext,holder.mIvGoodThumb,goods.getGoodsThumb());
            holder.mTvGoodName.setText(goods.getGoodsName());
            holder.mTvGoodPrice.setText(goods.getCurrencyPrice());
        }
        holder.mTvCartCount.setText("("+cartBean.getCount()+")");
        holder.mCbSelect.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }


    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class CartViewHolder extends ViewHolder{
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
    }
}
