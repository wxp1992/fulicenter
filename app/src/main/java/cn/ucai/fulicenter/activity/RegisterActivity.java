package cn.ucai.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.NetDao;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.view.DisplayUtils;

import static cn.ucai.fulicenter.R.string.confirm_password_connot_be_empty;
import static cn.ucai.fulicenter.R.string.illegal_user_name;
import static cn.ucai.fulicenter.R.string.nick_name_connot_be_empty;
import static cn.ucai.fulicenter.R.string.password_connot_be_empty;
import static cn.ucai.fulicenter.R.string.two_input_password;
import static cn.ucai.fulicenter.R.string.user_name_connot_be_empty;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.Nick)
    EditText mNick;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;

    String  nick;
    String username ;
    String pwd ;
    String confirm_pwd;
    RegisterActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this,"账户注册");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.btnRegister)
    public void checkedInput() {
        /**
         * 验证
         */
        nick = mNick.getText().toString().trim();
        username = mUsername.getText().toString().trim();
        pwd = mPassword.getText().toString().trim();
        confirm_pwd = mConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getResources().getString(user_name_connot_be_empty), Toast.LENGTH_SHORT).show();
            mUsername.requestFocus();
            return;
        }
        else if (!username.matches("[a-zA-Z]\\w{5,15}")){
            Toast.makeText(this, getResources().getString(illegal_user_name), Toast.LENGTH_SHORT).show();
            mUsername.requestFocus();
            return;
        } else if (TextUtils.isEmpty(nick)) {
            Toast.makeText(this, getResources().getString(nick_name_connot_be_empty), Toast.LENGTH_SHORT).show();
            mNick.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, getResources().getString(password_connot_be_empty), Toast.LENGTH_SHORT).show();
            mPassword.requestFocus();
            return;
        } else if (TextUtils.isEmpty(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(confirm_password_connot_be_empty), Toast.LENGTH_SHORT).show();
            return;
        }else if (!pwd.equals(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(two_input_password), Toast.LENGTH_SHORT).show();
            return;
        }
        register();
    }
    /**
     * 注册
     */
    private void register() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.register));
        pd.show();
        NetDao.register(mContext, username, nick, pwd, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if (result == null) {
                    CommonUtils.showShortToast(R.string.register_fail);
                } else {
                    if (result.isRetMsg()) {
                        CommonUtils.showLongToast(R.string.register_success);
                        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
                        MFGT.finish(mContext);
                    } else {
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);
                L.e(TAG,"register error="+error);
            }
        });

    }
}
