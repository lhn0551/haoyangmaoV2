package com.h.haoyangmaov2;


import java.util.ArrayList;
import java.util.List;

/**
 * User: ljx
 * Date: 2018/10/21
 * Time: 13:16
 */
public class PageList<T> {

    private List<T> datas;


    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<T> datas) {
        this.datas = datas;
    }
}
