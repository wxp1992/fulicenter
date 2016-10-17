package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by Administrator on 2016/10/17 0017.
 */

public class GoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context mContext;
    List<NewGoodsBean> mGoodList;
    GoodViewHoder mGoodViewHoder;
    FooterViewHolder mFooterViewHolder;
    String footerText;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout;
        RecyclerView.ViewHolder holder=null;
        switch (viewType){
            case I.TYPE_FOOTER:
                layout= LayoutInflater.from(mContext).inflate(R.layout.item_footer,null);
                holder=new FooterViewHolder(layout);
                break;
            case  I.TYPE_ITEM:
                layout=LayoutInflater.from(mContext).inflate(R.layout.item_good,null);
                holder=new GoodViewHoder(layout);
                break;
        }
        return  holder;
    }
    public void initItem(ArrayList<NewGoodsBean> list) {
        if (mGoodList!=null){
            mGoodList.clear();
        }
        mGoodList.addAll(list);
        notifyDataSetChanged();
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder){
            mFooterViewHolder= (FooterViewHolder) holder;
            mFooterViewHolder.tvFooter.setText(footerText);
        }
        if (holder instanceof  GoodViewHoder) {
            mGoodViewHoder = (GoodViewHoder) holder;
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1){
            return  I.TYPE_FOOTER;
        }else {
            return  I.TYPE_ITEM;
        }
    }
    @Override
    public int getItemCount() {
        return mGoodList!=null?mGoodList.size()+1:1;
    }
    class  GoodViewHoder extends RecyclerView.ViewHolder {
        ImageView ivGoodThumb;
        TextView tvGoodName;
        TextView tvGoodPrice;

        public GoodViewHoder(View itemView) {
            super(itemView);
            ivGoodThumb = (ImageView) itemView.findViewById(R.id.niv_good_thumb);
            tvGoodPrice = (TextView) itemView.findViewById(R.id.tv_good_price);
            tvGoodName= (TextView) itemView.findViewById(R.id.tv_good_name);

        }
    }
}
