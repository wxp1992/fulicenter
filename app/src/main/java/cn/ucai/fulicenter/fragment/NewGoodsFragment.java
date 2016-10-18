package cn.ucai.fulicenter.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.adapter.GoodAdapter;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.NetDao;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.view.SpaceItemDecoration;

/**
 * Created by Administrator on 2016/10/17 0017.
 */

public class NewGoodsFragment extends Fragment {

    @BindView(R.id.tv_rfresh)
    TextView mTvRfresh;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    MainActivity mContext;
    GoodAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    int pageId = 1;
    GridLayoutManager glm;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle save) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        initView();
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    private void setPullDownListener() {
       mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               mSrl.setRefreshing(true);
               mTvRfresh.setVisibility(View.VISIBLE);
               pageId = 1;
               downloadNewGoods(I.ACTION_PULL_DOWN);
           }
       });
    }

    private void setPullUpListener() {
     mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
         @Override
         public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
             super.onScrollStateChanged(recyclerView, newState);
             int lastPosition = glm.findLastVisibleItemPosition();
//             L.e("newState:"+newState);
//             L.e("lastPosition:"+lastPosition);
//             L.e("isMore:"+mAdapter.isMore());
             if (newState==RecyclerView.SCROLL_STATE_IDLE
                     &&lastPosition==mAdapter.getItemCount()-1
                     &&mAdapter.isMore()){
                 pageId++;
                 downloadNewGoods(I.ACTION_PULL_UP);
             }
         }

         @Override
         public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
             super.onScrolled(recyclerView, dx, dy);
             int firstPosition = glm.findFirstVisibleItemPosition();
             mSrl.setEnabled(firstPosition==0);
         }
     });
    }

    private void initData() {
        downloadNewGoods(I.ACTION_DOWNLOAD);
    }
    private void downloadNewGoods(final int action){
        NetDao.downloadNewGoods(mContext,pageId,new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>(){
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mSrl.setRefreshing(false);
                mTvRfresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                L.e("result"+result);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if (action==I.ACTION_DOWNLOAD||action==I.ACTION_PULL_DOWN){
                        mAdapter.initData(list);
                    }else {
                        mAdapter.addData(list);
                        //mAdapter.setMore(false);
                    }
                    if (list.size()<I.PAGE_SIZE_DEFAULT){
                        mSrl.setRefreshing(false);
                        mTvRfresh.setVisibility(View.GONE);
                        mAdapter.setMore(false);
                    }
                }
            }

            @Override
            public void onError(String error) {
                mSrl.setRefreshing(false);
                mTvRfresh.setVisibility(View.GONE);
                mAdapter.setMore(false);
                CommonUtils.showShortToast(error);
                L.e("error"+error);
            }
        });
    }

    private void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        mRv.setLayoutManager(glm);
        mRv.setHasFixedSize(true);
        mList = new ArrayList<>();
        mAdapter = new GoodAdapter(mContext, mList);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
    }
}
