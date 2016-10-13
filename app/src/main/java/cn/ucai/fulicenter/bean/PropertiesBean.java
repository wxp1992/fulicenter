package cn.ucai.fulicenter.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13 0013.
 */

public class PropertiesBean {


    /**
     * id : 9529
     * goodsId : 0
     * colorId : 7
     * colorName : 白色
     * colorCode : #ffffff
     * colorImg :
     * colorUrl : https://detail.tmall.com/item.htm?spm=a1z10.5-b.w4011-3609973698.66.6PtkVY&id=520971761592&rn=5ddf7aff64dbe1a24da0eaf7409e3389&abbucket=15&skuId=3104519239252
     * albums : [{"pid":7677,"imgId":28296,"imgUrl":"201509/goods_img/7677_P_1442391216432.png","thumbUrl":"no_picture.gif"},{"pid":7677,"imgId":28297,"imgUrl":"201509/goods_img/7677_P_1442391216215.png","thumbUrl":"no_picture.gif"},{"pid":7677,"imgId":28298,"imgUrl":"201509/goods_img/7677_P_1442391216692.png","thumbUrl":"no_picture.gif"},{"pid":7677,"imgId":28299,"imgUrl":"201509/goods_img/7677_P_1442391216316.png","thumbUrl":"no_picture.gif"}]
     */

    private ColorBean mColorBean;

    @Override
    public String toString() {
        return "PropertiesBean{" +
                "mColorBean=" + mColorBean +
                ", albumsList=" + albumsList +
                '}';
    }

    private List<AlbumsBean> albumsList;

    public PropertiesBean() {
    }

    public ColorBean getColorBean() {
        return mColorBean;
    }

    public void setColorBean(ColorBean colorBean) {
        mColorBean = colorBean;
    }

    public List<AlbumsBean> getAlbums() {
        return albumsList;
    }

    public void setAlbums(List<AlbumsBean> albumsList) {
        this.albumsList = albumsList;
    }

}

