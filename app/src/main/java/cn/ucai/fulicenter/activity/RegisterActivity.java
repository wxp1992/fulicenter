package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.view.DisplayUtils;

import static cn.ucai.fulicenter.R.string.confirm_password_connot_be_empty;
import static cn.ucai.fulicenter.R.string.illegal_user_name;
import static cn.ucai.fulicenter.R.string.nick_name_connot_be_empty;
import static cn.ucai.fulicenter.R.string.password_connot_be_empty;
import static cn.ucai.fulicenter.R.string.two_input_password;
import static cn.ucai.fulicenter.R.string.user_name_connot_be_empty;

public class RegisterActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
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
    public void onClick() {
        register();
    }

    /**
     * 注册
     */
    private void register() {
        nick = mNick.getText().toString().trim();
        username = mUsername.getText().toString().trim();
        pwd = mPassword.getText().toString().trim();
        confirm_pwd = mConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getResources().getString(user_name_connot_be_empty), Toast.LENGTH_SHORT).show();
            mUsername.requestFocus();
            return;
        }
        else if (!username.matches("[\\w][\\w\\d_]+")){
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

    }
}
