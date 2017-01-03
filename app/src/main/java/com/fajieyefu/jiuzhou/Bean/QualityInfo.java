package com.fajieyefu.jiuzhou.Bean;

/**
 * Created by Fajieyefu on 2016/8/11.
 */
public class QualityInfo {
    private String product_code;
    private String item_desc;
    private String item_ok;
    private String item_a;
    private String item_b;
    private String paixu;
    private String editString;
    private String item_result;
    private Boolean focus=false;

    public void setEditString(String editString) {
        this.editString = editString;
    }

    public void setItem_result(String item_result) {
        this.item_result = item_result;
    }

    public String getEditString() {
        return editString;
    }

    public String getItem_result() {
        return item_result;
    }

    public QualityInfo(String product_code, String item_desc, String item_ok, String item_a, String item_b, String paixu) {
        this.product_code = product_code;
        this.item_desc = item_desc;
        this.item_ok = item_ok;
        this.item_a = item_a;
        this.item_b = item_b;
        this.paixu = paixu;
    }

    public String getItem_a() {
        return item_a;
    }

    public String getItem_b() {
        return item_b;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public String getItem_ok() {
        return item_ok;
    }

    public String getPaixu() {
        return paixu;
    }

    public String getProduct_code() {
        return product_code;
    }
    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }
}
