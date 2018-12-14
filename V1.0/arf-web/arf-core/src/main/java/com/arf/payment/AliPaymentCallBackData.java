package com.arf.payment;

import java.io.Serializable;

public class AliPaymentCallBackData implements Serializable{

	private static final long serialVersionUID = 5109767295545546830L;


	/*
     * notify_time
                    通知时间
                    Date
                    通知的发送时间。
                    格式为yyyy-MM-dd HH:mm:ss。
                    不可空
                    2013-08-22 14:45:24
     */
    private String notify_time;
    
    
    /*
     * notify_type
                    通知类型
                    String
                    通知的类型。
                    不可空
                    trade_status_sync
     */
    private String notify_type;
    
    /*
     * notify_id
                    通知校验ID
                    String
                    通知校验ID。
                    不可空
                    64ce1b6ab92d00ede0ee56ade98fdf2f4c
     */
    private String notify_id;
    
    /*
     * sign_type
                    签名方式
                    String
                    固定取值为RSA。
                    不可空
                    RSA
     */
    private String sign_type;
    
    /*
     * sign
                    签名
                    String
                    请参见“9 签名机制”。
                    不可空
                    1glihU9DPWee+UJ82u3+mw3Bdnr9u01at0M/xJnPsGuHh+JA5bk3zbWaoWhU6GmLab3dIM4JNdktTcEUI9/FBGhgfLO39BKX/eBCFQ3bXAmIZn4l26fiwoO613BptT44GTEtnPiQ6+tnLsGlVSrFZaLB9FVhrGfipH2SWJcnwYs=
     */
    private String sign;
    
    //***********************业务参数***************************
    /*
     * out_trade_no
                    商户网站唯一订单号
                    String(64)
                    对应商户网站的订单系统中的唯一订单号，非支付宝交易号。
                    需保证在商户网站中的唯一性。是请求时对应的参数，原样返回。
                    可空
                    082215222612710
     */
    private String out_trade_no;
    
    /*
     * subject
                    商品名称
                    String(128)
                    商品的标题/交易标题/订单标题/订单关键字等。
                    它在支付宝的交易明细中排在第一列，对于财务对账尤为重要。是请求时对应的参数，原样通知回来。
                    可空
                    测试
     */
    private String subject;
    
    /*
     * payment_type
                    支付类型
                    String(4)
                    支付类型。默认值为：1（商品购买）。
                    可空
                    1
     */
    private String payment_type;
    
    /*
     * trade_no
                    支付宝交易号
                    String(64)
                    该交易在支付宝系统中的交易流水号。
                    最短16位，最长64位。
                    可空
                    2013082244524842
     */
    private String trade_no;
    
    /*
     * trade_status
                    交易状态
                    String
                    交易状态，取值范围请参见“11.3 交易状态”。
                    可空
                    TRADE_SUCCESS
     */
    private String trade_status;
    
    /*
     * seller_id
                卖家支付宝用户号
                String(30)
                卖家支付宝账号对应的支付宝唯一用户号。
                以2088开头的纯16位数字。
                可空
                2088501624816263
     */
    private String seller_id;
    
    /*
     *seller_email
                卖家支付宝账号
                String(100)
                卖家支付宝账号，可以是email和手机号码。
                可空
                xxx@alipay.com
     */
    private String seller_email;
    
    /*
     * buyer_id
                    买家支付宝用户号
                    String(30)
                    买家支付宝账号对应的支付宝唯一用户号。
                    以2088开头的纯16位数字。
                    可空
                    2088602315385429
     */
    private String buyer_id;
    
    /*
     * buyer_email
                    买家支付宝账号
                    String(100)
                    买家支付宝账号，可以是Email或手机号码。
                    可空
                    dlwdgl@gmail.com
     */
    private String buyer_email;
    
    /*
     * total_fee
                    交易金额
                    Number
                    该笔订单的总金额。
                    请求时对应的参数，原样通知回来。
                    可空
                    1.00
     */
    private double total_fee;
    
    /*
     * quantity
                    购买数量
                    Number
                    购买数量，固定取值为1（请求时使用的是total_fee）。
                    可空
                    1
     */
    private int quantity;
    
    /*
     * price
                    商品单价
                    Number
                    price等于total_fee（请求时使用的是total_fee）。
                    可空
                    1.00
     */
    private double price;
    
    /*
     * body
                    商品描述
                    String(512)
                    该笔订单的备注、描述、明细等。
                    对应请求时的body参数，原样通知回来。
                    可空
                    测试测试
     */
    private String body;
    
    /*
     * gmt_create
                    交易创建时间
                    Date
                    该笔交易创建的时间。
                    格式为yyyy-MM-dd HH:mm:ss。
                    可空
                    2013-08-22 14:45:23
     */
    private String gmt_create;
    
    /*
     * gmt_payment
                    交易付款时间
                    Date
                    该笔交易的买家付款时间。
                    格式为yyyy-MM-dd HH:mm:ss。
                    可空
                    2013-08-22 14:45:24
     */
    private String gmt_payment;
    
    /*
     * is_total_fee_adjust
                    是否调整总价
                    String(1)
                    该交易是否调整过价格。
                    可空
                    N
     */
    private String is_total_fee_adjust;
    
    /*
     * use_coupon
                    是否使用红包买家
                    String(1)
                    是否在交易过程中使用了红包。
                    可空
                    N
     */
    private String use_coupon;
    
    /*
     * discount
                    折扣
                    String
                    支付宝系统会把discount的值加到交易金额上，如果有折扣，本参数为负数，单位为元。
                    可空
                    0.00
     */
    private String discount;
    
    /*
     * refund_status
                    退款状态
                    String
                    取值范围请参见“11.4 退款状态”。
                    可空
                    REFUND_SUCCESS
     */
    private String refund_status;
    
    /*
     * gmt_refund
                    退款时间
                    Date
                    卖家退款的时间，退款通知时会发送。
                    格式为yyyy-MM-dd HH:mm:ss。
                    可空
                    2008-10-29 19:38:25
     */
    private String gmt_refund;

    public String getNotify_time() {
        return notify_time;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public String getSign() {
        return sign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public double getTotal_fee() {
        return total_fee;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getBody() {
        return body;
    }

    public String getGmt_create() {
        return gmt_create;
    }

    public String getGmt_payment() {
        return gmt_payment;
    }

    public String getIs_total_fee_adjust() {
        return is_total_fee_adjust;
    }

    public String getUse_coupon() {
        return use_coupon;
    }

    public String getDiscount() {
        return discount;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public String getGmt_refund() {
        return gmt_refund;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public void setTotal_fee(double total_fee) {
        this.total_fee = total_fee;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public void setGmt_payment(String gmt_payment) {
        this.gmt_payment = gmt_payment;
    }

    public void setIs_total_fee_adjust(String is_total_fee_adjust) {
        this.is_total_fee_adjust = is_total_fee_adjust;
    }

    public void setUse_coupon(String use_coupon) {
        this.use_coupon = use_coupon;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public void setGmt_refund(String gmt_refund) {
        this.gmt_refund = gmt_refund;
    }

}
