package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.GoodsDetailsActivity;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;


/**
 * Created by Administrator on 2016/10/17 0017.
 */

public class GoodAdapter extends Adapter {
    Context mContext;
    List<NewGoodsBean> mList;
    boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public GoodAdapter(Context context, List<NewGoodsBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
                break;
            case I.TYPE_ITEM:
                holder = new GoodViewHoder(View.inflate(mContext, R.layout.item_good, null));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder vh = (FooterViewHolder) holder;
            vh.tvFooter.setText(getFootString());
        } else {
            GoodViewHoder vh = (GoodViewHoder) holder;
            NewGoodsBean goods = mList.get(position);
            ImageLoader.downloadImg(mContext, vh.ivGoodThumb, goods.getGoodsThumb());
            vh.tvGoodName.setText(goods.getGoodsName());
            vh.tvGoodPrice.setText(goods.getCurrencyPrice());
            vh.itemGoodLL.setTag(goods.getGoodsId());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() + 1;
    }

    public int getFootString() {

        return isMore ? R.string.load_more : R.string.no_more;
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class GoodViewHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.niv_good_thumb)
        ImageView ivGoodThumb;
        @BindView(R.id.tv_good_name)
        TextView tvGoodName;
        @BindView(R.id.tv_good_price)
        TextView tvGoodPrice;
        @BindView(R.id.item_good_LL)
        LinearLayout itemGoodLL;

        @OnClick(R.id.item_good_LL)
        public void onGoodsItemClick() {
            int goodsId = (int) itemGoodLL.getTag();
            mContext.startActivity(new Intent(mContext, GoodsDetailsActivity.class)
                      .putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId));
        }
        GoodViewHoder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
