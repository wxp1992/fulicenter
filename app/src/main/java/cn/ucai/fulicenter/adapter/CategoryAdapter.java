package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/20 0020.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> groupList,
                           ArrayList<ArrayList<CategoryChildBean>> childList) {
        mContext = context;
        mGroupList = new ArrayList<>();
        mGroupList.addAll(groupList);
        mChildList = new ArrayList<>();
        mChildList.addAll(childList);
    }

    @Override
    public int getGroupCount() {
        return mGroupList != null ? mGroupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupList != null ? mGroupList.get(groupPosition) : null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        GroupViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        } else {
            view.getTag();
            holder = (GroupViewHolder) view.getTag();
        }
        CategoryGroupBean group = getGroup(groupPosition);
        if (group != null) {
            ImageLoader.downloadImg(mContext, holder.ivCateGroupThumb, group.getImageUrl());
            holder.tvGroupName.setText(group.getName());
            holder.ivIndicator.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ChildViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_child, null);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        CategoryChildBean child = getChild(groupPosition, childPosition);
        if (child != null) {
            ImageLoader.downloadImg(mContext, holder.ivCategoryChildThumb, child.getImageUrl());
            holder.tvCatogryChildName.setText(child.getName());
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> groupList,
                         ArrayList<ArrayList<CategoryChildBean>> childList) {
        if (mGroupList != null) {
            mGroupList.clear();
        }
        mGroupList.addAll(groupList);
        if (mChildList != null) {
            mChildList.clear();
        }
        mChildList.addAll(childList);
        notifyDataSetChanged();
    }

    class GroupViewHolder {
        @BindView(R.id.ivCateGroupThumb)
        ImageView ivCateGroupThumb;
        @BindView(R.id.tvGroupName)
        TextView tvGroupName;
        @BindView(R.id.ivIndicator)
        ImageView ivIndicator;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.ivCategoryChildThumb)
        ImageView ivCategoryChildThumb;
        @BindView(R.id.tvCatogryChildName)
        TextView tvCatogryChildName;
        @BindView(R.id.layout_catogry_child)
        RelativeLayout layoutCatogryChild;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
