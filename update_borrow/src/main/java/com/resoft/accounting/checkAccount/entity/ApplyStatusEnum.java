package com.resoft.accounting.checkAccount.entity;

/**
 * 查账申请状态枚举类.
 *
 * @author SeppSong
 */
public enum ApplyStatusEnum {
    //保存或打回
    SAVE("保存", 0),
    //提交申请
    SUBMIT("提交申请", 1),
    //完成申请
    SUCCESS("匹配完成", 2);

    private String name;
    private Integer value;

    ApplyStatusEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static String getName(ApplyStatusEnum status) {
        switch (status) {
            case SAVE :
                return SAVE.getName();
            case SUBMIT :
                return SUBMIT.getName();
            case SUCCESS :
                return SUCCESS.getName();
            default:
                return null;
        }
    }

    public static ApplyStatusEnum valueOf(Integer value) {
        switch (value) {
            case 0 :
                return SAVE;
            case 1 :
                return SUBMIT;
            case 2 :
                return SUCCESS;
            default :
                throw new IllegalArgumentException("ApplyStatusEnum 找不到对应枚举值" + value);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
