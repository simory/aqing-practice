package aqing.interview;

/**
 * @Description:
 * @Author: fuqi
 * @Date: 18/9/14 上午6:15
 */
public enum PaymentTypes{
    BALANCE("b",0),//余额
    RED_PACKAGE("red",1),//红包
    DISCOUNT_COUPON("dc",2),//优惠券
    VOUCHER("V",3),//代金券
    ;
    private String paymentType;
    private Integer index;
    private PaymentTypes(String paymentType, Integer index){
        this. paymentType  = paymentType;
        this. index = index;
    }

    public String getPaymentType(){
        return paymentType;
    }

    public void setPaymentType(String paymentType){
        this.paymentType = paymentType;
    }

    public Integer getIndex(){
        return index;
    }

    public void setIndex(Integer index){
        index = index;
    }
}
