package com.rongdu.loans.enums;

/**商品上下架状态
 * @author qifeng
 * @date 2018/11/29 0029
 */
public enum GoodsStatusEnum {


    ON_SHELVES(1, "上架"),
    OFF_SHELVES(0, "下架");

    private Integer value;
    private String desc;

    GoodsStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
