package com.rongdu.loans;

import com.google.common.collect.Maps;
import com.rongdu.common.config.Global;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.hanjs.op.HanJSUserOP;
import com.rongdu.loans.hanjs.service.HanJSUserService;
import com.rongdu.loans.loan.option.CarefreeCounterfoilDataOP;
import com.rongdu.loans.loan.service.CarefreeCounterfoilService;
import com.rongdu.loans.loan.vo.PactRecord;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.record.aggregates.SharedValueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/3
 */

@Controller
@RequestMapping("test/testAncun")
public class TestController {

//    @Autowired
//    private CarefreeCounterfoilService carefreeCounterfoilService;

    @Autowired
    private HanJSUserService hanJSUserService;


    @RequestMapping("testMethod")
    public void testMethod() {
        Map<String, String> rePush = Maps.newHashMap();
        rePush.put("R556437819199463575", String.valueOf(System.currentTimeMillis()));
            JedisUtils.mapPut("JDQ:third_key", rePush);

//
//        HanJSUserOP hanJSUserOP = new HanJSUserOP();
//        hanJSUserOP.setChannelId(System.currentTimeMillis() + "");
//        hanJSUserOP.setDatetime(DateUtils.getHHmmss());
//        hanJSUserOP.setGender("M");
//        hanJSUserOP.setMobile("15902762813");
//        hanJSUserOP.setName("王某某");
//        hanJSUserOP.setSign_fail_url("http://192.168.1.1/a/fail/method");
//        hanJSUserOP.setSign_success_url("http://192.168.1.1/a/success/method");
//        hanJSUserService.openAccount(hanJSUserOP);


        System.out.println("======================");
        // 用户信息
//        dataOP.setIdCard("110101196103076011");
//        dataOP.setEmail("wl_code@163.com");
//        dataOP.setMobile("15902762834");
//        dataOP.setIdentNo("110101196103076011");
//        dataOP.setUserName("王某某");
//        dataOP.setNickName("王借款");
//
//        // 项目信息
//        DataProjectVo dataProjectVo = new DataProjectVo();
//        dataProjectVo.setBankCardNo("42024576789998");
//        dataProjectVo.setLimit("8期");
//        dataProjectVo.setMoney("5000");
//        dataProjectVo.setMoneyLower("5000");
//        dataProjectVo.setMoneyUpper("伍仟元整");
//        dataProjectVo.setProjectName("分期乐0.5万");
//        dataProjectVo.setProjectNo("1231231231231");
//        dataProjectVo.setRate("10");
//        dataProjectVo.setRepayment("等额本息");
//        dataProjectVo.setUse("透支生活");
//        dataOP.setProject(dataProjectVo);
//
//
//        //还款计划
//        dataOP.getRepayList().add(new RepayDataVo("第1期", "625", "625", "625", "2018-12-1 11:02"));
//        dataOP.getRepayList().add(new RepayDataVo("第2期", "625", "625", "1250", "2018-12-2 11:03"));
//        dataOP.getRepayList().add(new RepayDataVo("第3期", "625", "625", "1875", "2018-12-3 11:04"));
//        dataOP.getRepayList().add(new RepayDataVo("第4期", "625", "625", "2500", "2018-12-4 11:05"));
//        dataOP.getRepayList().add(new RepayDataVo("第5期", "625", "625", "3125", "2018-12-5 11:06"));
//        dataOP.getRepayList().add(new RepayDataVo("第6期", "625", "625", "3750", "2018-12-6 11:02"));
//        dataOP.getRepayList().add(new RepayDataVo("第7期", "625", "625", "4375", "2018-12-7 11:02"));
//        dataOP.getRepayList().add(new RepayDataVo("第8期", "625", "625", "5000", "2018-12-8 11:02"));

        CarefreeCounterfoilDataOP dataOP = new CarefreeCounterfoilDataOP();
        dataOP.setPactNo("96dd6c0ff6b54c70b099eb912cd5520e");
        dataOP.setBorrowerName("蓝丽明");
        dataOP.setBorrowerIdCard("42102319940816002X");
        dataOP.setBorrowerAddress("湖北省武汉市江夏区藏龙岛惠丰重庆花园2栋206室");
        dataOP.setBorrowerPhone("13164104815");
        dataOP.setBorrowerEmail("131641048@qq.com");
        dataOP.setLoanAmt("玖仟捌佰圆整");
        dataOP.setLoanLimit("30");
        dataOP.setBeginLoanDate("2018-11-24");
        dataOP.setEndLoanDate("2018-12-25");
        dataOP.setLoanUse("购物");
        dataOP.setOtherCondition("");
        dataOP.setPayee("蓝丽明");
        dataOP.setReciptBank("中国建设银行");
        dataOP.setReciptBankNo("6236682870005844282");
        dataOP.setTransferBank("中国建设");

        //购物协议参数
        dataOP.setRepaymentDate("2019-02-11");
        dataOP.setRepaymentProductMoney("1200");
        dataOP.setAdvanceFundMoney("300");
        dataOP.setSumLoanAmt(dataOP.getLoanAmt());
        dataOP.setAdvanceFundDay("15");
        dataOP.setPruductAdvanceFundSumMoney("45");


//        PactRecord pactRecord = carefreeCounterfoilService.generateCardfreeCounterfoil(dataOP);
//        System.out.println(Global.getConfig("ancun.domain") + pactRecord.getLoanRecordNo());
//        System.out.println(Global.getConfig("ancun.domain") + pactRecord.getShoppingRecordNo());
//        PactRecord generate = carefreeCounterfoilService.generate("5dca13394dbb41998fdf89ad34e59e5a", "1020181211202241647942", "c9a10be5b2864e3ba0ab273b03bbfebb", new Date());
//        System.out.println(generate);

    }

    /**
     * 获得响应HTTP实体内容
     *
     * @param response
     * @return
     * @throws java.io.IOException
     * @throws java.io.UnsupportedEncodingException
     */
    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
        //通过HttpResponse 的getEntity()方法获取返回信息
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            FileOutputStream fileOutputStream = new FileOutputStream("E:\\test\\test_new1.pdf");
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = is.read(data)) != -1) {//循环读取inputStream流中的数据，存入文件流fileOutputStream
                fileOutputStream.write(data, 0, len);
            }
            fileOutputStream.close();
            is.close();
        }
        return "";
    }


    public static void main(String[] args) {
        //首先需要先创建一个DefaultHttpClient的实例
        System.out.println(UUID.randomUUID());
//        try {
//            HttpClient httpClient = new DefaultHttpClient();
//            //先创建一个HttpGet对象,传入目标的网络地址,然后调用HttpClient的execute()方法即可:
//            HttpGet httpGet = new HttpGet();
//            httpGet.setURI(URI.create("https://www.51cunzheng.com/outService/attachDownLoad?recordNo=13597340611841474561014"));
//            httpGet.setHeader("Referer", "www.le.com");
//            HttpResponse response = httpClient.execute(httpGet);
//            String httpEntityContent = getHttpEntityContent(response);
//            httpGet.abort();
//            System.out.println(httpEntityContent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//
//        }


//        CarefreeCounterfoilDataOP dataOP = new CarefreeCounterfoilDataOP();
//        dataOP.setPactNo("1231231");
//        dataOP.setBorrowerName("陈凯哥");
//        dataOP.setBorrowerIdCard("110101199003073079");
//        dataOP.setBorrowerAddress("北京市东城区");
//        dataOP.setBorrowerPhone("15902762834");
//        dataOP.setBorrowerEmail("wl_code_test_1@163.com");
//        dataOP.setLoanAmt("贰仟圆整");
//        dataOP.setLoanLimit("3个月");
//        dataOP.setBeginLoanDate("2018-12-11");
//        dataOP.setEndLoanDate("2019-02-11");
//        dataOP.setLoanUse("分期乐");
//        dataOP.setOtherCondition("");
//        dataOP.setPayee("陈凯哥");
//        dataOP.setReciptBank("中国建设银行");
//        dataOP.setReciptBankNo("6222600260001072444");
//        dataOP.setTransferBank("招商银行");
//        //购物协议参数
//        dataOP.setRepaymentDate("2019-02-11");
//        dataOP.setRepaymentProductMoney("1200");
//        dataOP.setAdvanceFundMoney("300");
//        dataOP.setSumLoanAmt(dataOP.getLoanAmt());
//        dataOP.setAdvanceFundDay("15");
//        dataOP.setPruductAdvanceFundSumMoney("45");
//        System.out.println(dataOP);
    }
}
