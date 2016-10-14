package cn.ucai.fulicenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {
    RadioButton mrbNewGood;
    RadioButton mrbBoutique;
    RadioButton mrbCategory;
    RadioButton mrbCart;
    RadioButton mrbContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.i("MainActivity onCreate");
        initView();
    }

    private void initView() {
        mrbNewGood = (RadioButton) findViewById(R.id.rbGoodNews);
        mrbBoutique = (RadioButton) findViewById(R.id.rbBoutique);
        mrbCategory = (RadioButton) findViewById(R.id.rbCategory);
        mrbCart = (RadioButton) findViewById(R.id.rbCart);
        mrbContact = (RadioButton) findViewById(R.id.rbContact);
    }
}
