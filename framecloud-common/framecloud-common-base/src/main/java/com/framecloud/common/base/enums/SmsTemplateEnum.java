package com.framecloud.common.base.enums;


import lombok.Getter;

public enum  SmsTemplateEnum {

    /**
     * 腾讯云
     */
    LOGIN_CODE("程序咖啡厅", "238684");

    @Getter
    String signName;

    @Getter
    String tempalte;

    private SmsTemplateEnum(String signName, String tempalte) {
        this.signName = signName;
        this.tempalte = tempalte;
    }
}
