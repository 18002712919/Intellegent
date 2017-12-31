package com.asus.intellegent.open;

/**
 * Created by ASUS on 2017/6/23.
 */

public class ContactInfo {
    private String name;    //联系人名字
    private String number;  //联系人电话
    public ContactInfo(String name, String number) {
        this.name = name;
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
}
