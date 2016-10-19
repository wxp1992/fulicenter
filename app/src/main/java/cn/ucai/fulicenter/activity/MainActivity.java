package cn.ucai.fulicenter.activity;

import android.graphics.ImageFormat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {
    RadioButton mrbNewGood;
    RadioButton mrbBoutique;
    RadioButton mrbCategory;
    RadioButton mrbCart;
    RadioButton mrbContact;
    TextView tvCartHint;
    int index;
    int currentIndex;
    RadioButton[] mrbTabs;
    Fragment[] mFragments;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.i("MainActivity onCreate");
        initView();
      initFragment();
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container, mBoutiqueFragment)
                .hide(mBoutiqueFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    private void initView() {
        mrbNewGood = (RadioButton) findViewById(R.id.rbGoodNews);
        mrbBoutique = (RadioButton) findViewById(R.id.rbBoutique);
        mrbCategory = (RadioButton) findViewById(R.id.rbCategory);
        mrbCart = (RadioButton) findViewById(R.id.rbCart);
        mrbContact = (RadioButton) findViewById(R.id.rbContact);
        tvCartHint = (TextView) findViewById(R.id.tvCartHint);
        mrbTabs=new RadioButton[5];
        mrbTabs[0]=mrbNewGood;
        mrbTabs[1]=mrbBoutique;
        mrbTabs[2]=mrbCategory;
        mrbTabs[3]=mrbCart;
        mrbTabs[4]=mrbContact;

    }

    public  void  onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.rbGoodNews:
                index=0;
                break;
            case R.id.rbBoutique:
                index=1;
                break;
            case R.id.rbCategory:
                index=2;
                break;
            case R.id.rbCart:
                index=3;
                break;
            case R.id.rbContact:
                index=4;
                break;
        }
        setFragment();
    }

    private void setFragment() {
        if (index!=currentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[currentIndex]);
            if (!mFragments[index].isAdded()) {
                ft.add(R.id.fragment_container, mFragments[index]);
            }
            ft.show(mFragments[index]).commit();
        }
        setRadioButtonStatus();
        currentIndex = index;
    }

    private void setRadioButtonStatus() {
//        L.e("index:"+index);
        for (int i=0;i<mrbTabs.length;i++){
            if (index==i){
                mrbTabs[i].setChecked(true);
            }else {
                mrbTabs[i].setChecked(false);
            }
        }
    }
}
