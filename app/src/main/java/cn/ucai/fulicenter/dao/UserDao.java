package cn.ucai.fulicenter.dao;

import android.content.Context;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.utils.L;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class UserDao {
    public static final String USER_TABLE_NAME = "t_superwechat_user";
    public static final String USER_COLUMN_NAME = "m_user_name";
    public static final String USER_COLUMN_NICK = "m_user_nick";
    public static final String USER_COLUMN_AVATAR_ID = "m_user_acatat_id";
    public static final String USER_COLUMN_AVATAR_PATH = "m_user_acatat_path";
    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_user_acatat_suffix";
    public static final String USER_COLUMN_AVATAR_TYPE = "m_user_acatat_type";
    public static final String USER_COLUMN_AVATAR_LASTUPDATE_TIME = "m_user_acatat_lastupdate_time";

    public UserDao(Context context) {
        DBManager.getInstance().onInit(context);
    }
    public boolean saveUser(UserAvatar user) {
        return DBManager.getInstance().saveUser(user);
    }

    public UserAvatar getUser(String username) {
        return DBManager.getInstance().getUser(username);
    }
    public boolean updateUser(UserAvatar user){
        return DBManager.getInstance().updateUser(user);
    }
}
