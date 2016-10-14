package cn.ucai.fulicenter.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/13 0013.
 */

public class MessageBean implements Serializable {
    /**
     * success : true
     * msg : 收藏成功
     */

    private boolean success;
    private String msg;

    @Override
    public String toString() {
        return "MessageBean{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }

    public MessageBean() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
