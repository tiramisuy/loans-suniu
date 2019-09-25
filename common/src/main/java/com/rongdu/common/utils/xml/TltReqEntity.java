package com.rongdu.common.utils.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 通联通请求报文对象
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/2
 */
@Data
@XStreamAlias("INFO")
public class TltReqEntity {

    /** 交易代码 */
    @XStreamAlias("trxCode")
    private String TRX_CODE;
    /** 版本 */
    @XStreamAlias("version")
    private String VERSION;
    /** 数据格式,固定2 XML格式 */
    @XStreamAlias("dataType")
    private String DATA_TYPE="2";
    /** 处理级别,固定值5.  0-9 0优先级最低 */
    @XStreamAlias("level")
    private String LEVEL="5";
    /** 商户代码 */
    @XStreamAlias("merchantId")
    private String MERCHANT_ID;
    /** 用户名 */
    @XStreamAlias("userName")
    private String USER_NAME;
    /** 用户密码 */
    @XStreamAlias("userPass")
    private String USER_PASS;
    /** 交易批次号 */
    @XStreamAlias("reqSn")
    private String REQ_SN;
    /** 签名信息 */
    @XStreamAlias("signedMsg")
    private String SIGNED_MSG;






}
