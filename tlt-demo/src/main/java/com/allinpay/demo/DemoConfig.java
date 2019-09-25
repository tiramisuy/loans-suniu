package com.allinpay.demo;


/**
 * 参数的配置，入网商户对接通联通的商户必须找相应的分公司技术颁发唯一的系统对接参数
 * 如，商户号，用户名，密码，私钥证书
 **/
public class DemoConfig {
	
	public static String merchantid = "200604000006432";//系统对接的商户号,找通联的客户经理分配
	public static String url = "https://113.108.182.3/aipg/ProcessServlet";//对接的测试接口地址
	public static String urlFileGet="https://113.108.182.3/aipg/GetConFile.do?SETTDAY=@xxx&REQTIME=@yyy&MERID=@zzz&SIGN=@sss&CONTFEE=1";//简单对账文件的接口地址 
	public static String username = "20060400000643204"; //用户名 ,找通联的客户经理分配
	public static String userpass = "111111"; //用户密码,找通联的客户经理分配
	
	public static String pathpfx = "cert/20060400000643204.p12"; //商户私钥路径,找通联的客户经理分配
	public static String pfxpass = "111111"; //私钥密码,找通联的客户经理分配
	
	public static String pathcer = "cert/allinpay-pds.cer"; //通联公钥
	
}
