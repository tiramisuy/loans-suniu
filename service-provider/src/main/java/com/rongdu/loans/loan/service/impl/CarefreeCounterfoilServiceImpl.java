package com.rongdu.loans.loan.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.ancun.ops.client.OpsClient;
import com.ancun.ops.dto.OpsRequest;
import com.ancun.ops.dto.OpsResponse;
import com.ancun.ops.dto.OpsUserInfo;
import com.ancun.ops.utils.JsonUtils;
import com.lowagie.text.pdf.BaseFont;
import com.rongdu.common.config.Global;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.NumberUtils;
import com.rongdu.common.utils.json.Json;
import com.rongdu.loans.common.OpsClientUtil;
import com.rongdu.loans.common.fmk.Fmkr;
import com.rongdu.loans.common.fmk.FmkrCallback;
import com.rongdu.loans.compute.helper.ContractHelper;
import com.rongdu.loans.cust.entity.CustUserInfo;
import com.rongdu.loans.cust.manager.CustUserInfoManager;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.BankCodeEnum;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.option.CarefreeCounterfoilDataOP;
import com.rongdu.loans.loan.option.CarefreeCounterfoilDataOP.LoanRepayPlanItemOP;
import com.rongdu.loans.loan.option.GeneratorEquitableAssignmentDataOP;
import com.rongdu.loans.loan.option.RepayPlanOP;
import com.rongdu.loans.loan.service.CarefreeCounterfoilService;
import com.rongdu.loans.loan.service.LoanRepayPlanService;
import com.rongdu.loans.loan.vo.PactRecord;

import lombok.extern.slf4j.Slf4j;

/**
 * 生成无忧存证实现类
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/3
 */
@Slf4j
@Service("carefreeCounterfoilService")
public class CarefreeCounterfoilServiceImpl implements CarefreeCounterfoilService {
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private CustUserInfoManager custUserInfoManager;
    @Autowired
    private LoanRepayPlanService loanRepayPlanService;

    @Override
    public PactRecord generateCardfreeCounterfoil(@Valid CarefreeCounterfoilDataOP carefreeCounterfoilDataOP) {
        String dataJson = Json.parse(carefreeCounterfoilDataOP);
        final Map<String, Object> dataMap = Json.recover(dataJson, Json.MAP_OBJECT);
//        testMethod(dataMap);
//        log.info("无忧存证：" + dataJson);
//        // 制作个人章
        awardCaForPersonal(dataMap);
////        // 生成小贷借款合同
        String loanRecordNo = generatePact("certificate_one.ftl", dataMap, true);
//        // 生成购物协议
        String shoppingRecordNo = generatePact("shopping_protocol.ftl", dataMap, false);
        return new PactRecord(loanRecordNo, shoppingRecordNo);
//        return null;
    }

    private void testMethod(Map<String, Object> dataMap) {
        File file = new File("E:\\test\\20181229.pdf");
        String url = save(dataMap, file, true);
        log.info("测试债转合同.{}", url);
    }

    @Override
    public String generateEquitableAssignment(List<GeneratorEquitableAssignmentDataOP> list) {
        String dataJson = Json.parse(list);
        final Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", Json.recover(dataJson, Json.LIST_MAP_OBJECT));
//		dataMap.put("borrowerName", "重庆乐视小额贷款有限公司");
//		dataMap.put("borrowerIdCard", "MA5U4Y0B8");

        dataMap.put("borrowerName", "武汉聚宝袋网络科技有限公司");
        dataMap.put("borrowerIdCard", "91420102MA4KW27823");
        log.info("无忧存证：" + dataJson);
        awardCaForCompany(dataMap);
        // 生成债权转让合同
        return generatePact("certificate_ea.ftl", dataMap, true);
    }


    /**
     * 生成合同
     *
     * @param ftlName 模板文件名
     * @param dataMap 模板所需数据
     * @param isLeshi 是否是乐视小额贷,注意不需要给乐视看的合同，传false
     * @return
     */
    private String generatePact(String ftlName, final Map<String, Object> dataMap, final Boolean isLeshi) {
        // 根据 ftl 模板生成 pdf
        // 将数据写入ftl模板,生成html
        return Fmkr.builder(ftlName, dataMap, new FmkrCallback() {
            @Override
            public String handler(Object data) throws Exception {
                // 利用itext 生成pdf
                String htmlStr = (String) data;
                // 将生成的html 转换成PDF文件
                ITextRenderer render = new ITextRenderer();
                // 解决中文问题
                ITextFontResolver fontResolver = render.getFontResolver();
                fontResolver.addFont(ResourceUtils.getFile("classpath:ftl/fonts").getPath() + "/SIMSUN.TTC",
                        BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                fontResolver.addFont(ResourceUtils.getFile("classpath:ftl/fonts").getPath() + "/SIMSUN.TTC",
                        BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                render.setDocumentFromString(htmlStr);
                render.layout();
                File tempFile = File.createTempFile("temp", ".pdf");
                FileOutputStream tempPdf = new FileOutputStream(tempFile);
                render.createPDF(tempPdf);
                render.finishPDF();
                // 生成保单
                String url = "";
                try {
                    url = save(dataMap, tempFile, isLeshi);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    tempPdf.flush();
                    tempPdf.close();
                    tempFile.delete();
                }
                return url;
            }
        });
    }

    /**
     * 生成保单
     */
    private String save(Map<String, Object> dataMap, File tempFile, boolean isLeshi) {
        OpsClient opsClient = OpsClientUtil.getOpsClient();
        OpsRequest opsRequest = OpsRequest.create();
        opsRequest.setItemKey(Global.getConfig("ancun.itemKey"));// 业务编号，平台分配
        opsRequest.setFlowNo(Global.getConfig("ancun.flowNo"));// 流程编号，平台分配
        // 以下是保全的业务数据
        for (Map.Entry<String, Object> map : dataMap.entrySet()) {
            opsRequest.putPreserveData(map.getKey(), map.getValue());
        }
        // 以下是业务数据的用户信息
        OpsUserInfo opsUserInfo = new OpsUserInfo();
        opsUserInfo.getOtherInfo().put("userName", dataMap.get("borrowerName"));
        opsUserInfo.setIdCard(dataMap.get("borrowerIdCard") + "");// 身份证或组织机构代码
        opsUserInfo.setMobile(dataMap.get("borrowerPhone") + "");
        opsUserInfo.setEmail(dataMap.get("borrowerEmail") + "");
        opsRequest.getUserInfos().add(opsUserInfo);

        if (isLeshi) {
            OpsUserInfo opsUserInfo2 = new OpsUserInfo();
            opsUserInfo2.getOtherInfo().put("userName", "重庆乐视小额贷款有限公司");
            opsUserInfo2.setIdCard("91500105MA5U4Y0B86");// 身份证或组织机构代码
            opsUserInfo2.setMobile("18565758368");
            opsUserInfo2.setEmail("majingrui@le.com");
            opsRequest.getUserInfos().add(opsUserInfo2);
        }

        // 需要签章时，设置ca签章参数
        opsRequest.setNeedInvestor(true);// 是否盖投资人章，不需要，可以不设置，或者为false
        opsRequest.setNeedLender(true);// 是否盖借款人章，不需要，可以不设置，或者为false
        // opsRequest.setInvestorIdcard("investorIdcard");//投资人的证件号，和申请证书时保持一致
        opsRequest.setLenderIdcard(dataMap.get("borrowerIdCard") + "");// 借款人的证件号，和申请证书时保持一致
        opsRequest.addCollaboratorIdentNo(Global.getConfig("ancun.collaboratorIdentNo"));// 担保方或合作方的组织机构代码,如果有多个担保方，可以多次添加，没有担保方，可以不设置
        // 磁盘路径形式的附件，参数一表示附件全路径，参数二表示附件的描述信息
        // 附件描述信息不能含中文
        // opsRequest.addFile("/u01/store/aaa/attach1.pdf", "attach");
        opsRequest.addFile(tempFile, tempFile.getName());
        // 执行保全
        OpsResponse opsResponse = opsClient.save(opsRequest);
        log.info(JsonUtils.beanToJSON(opsResponse));
        // return opsResponse.getData();
        if (opsResponse.getData() != null) {
            Map<String, Object> respMap = Json.recover(opsResponse.getData(), Json.MAP_OBJECT);
            // return Global.getConfig("ancun.domain") +
            // respMap.get("recordNo");
            return respMap.get("recordNo") + "";
        }
        return "";

    }

    /**
     * 制作个人章
     */
    public void awardCaForPersonal(Map<String, Object> dataMap) {
        // OpsClientConfiguration conf = new OpsClientConfiguration();
        // conf.setConnectionTimeout(50000);
        // conf.setMaxErrorRetry(3);
        // conf.setMaxConnections(128);
        // conf.setSocketTimeout(18000);
        OpsClient opsClient = OpsClientUtil.getOpsClient();
        OpsRequest opsRequest = OpsRequest.create();

        opsRequest.putPreserveData("userName", dataMap.get("borrowerName"));// 姓名
        opsRequest.putPreserveData("identNo", dataMap.get("borrowerIdCard"));// 证件号
        // opsRequest.putPreserveData("userName", "邹杰");//姓名
        // opsRequest.putPreserveData("identNo", "362204199203132910");//证件号
        opsRequest.putPreserveData("certType", "7");// 证书类型
        opsRequest.putPreserveData("userType", "1");// 用户类型 1：个人

        // opsRequest.putPreserveData("sealLocation", "杭州市拱墅区泰嘉园");//制章地理位置
        // opsRequest.putPreserveData("sealResson", "投资合同签章");//制章原因

        OpsResponse opsResponse = opsClient.awardCaForPersonal(opsRequest);
        log.info(JsonUtils.beanToJSON(opsResponse));
    }


    /**
     * 制作公司章
     */
    public void awardCaForCompany(Map<String, Object> dataMap) {
        // OpsClientConfiguration conf = new OpsClientConfiguration();
        // conf.setConnectionTimeout(50000);
        // conf.setMaxErrorRetry(3);
        // conf.setMaxConnections(128);
        // conf.setSocketTimeout(18000);
        OpsClient opsClient = OpsClientUtil.getOpsClient();
        OpsRequest opsRequest = OpsRequest.create();

        opsRequest.putPreserveData("userName", dataMap.get("borrowerName") + "");//姓名
        opsRequest.putPreserveData("identNo", dataMap.get("borrowerIdCard") + "");//证件号
        opsRequest.putPreserveData("certType", "7");// 证书类型
        opsRequest.putPreserveData("userType", "2");// 用户类型 1：个人

        // opsRequest.putPreserveData("sealLocation", "杭州市拱墅区泰嘉园");//制章地理位置
        // opsRequest.putPreserveData("sealResson", "投资合同签章");//制章原因

        OpsResponse opsResponse = opsClient.awardCaForCompany(opsRequest);
        log.info(JsonUtils.beanToJSON(opsResponse));
    }

    @Override
    public PactRecord generate(String userId, String applyId, String payNo, Date payDate) {
        CustUserVO user = custUserService.getCustUserById(userId);
        LoanApply apply = loanApplyManager.getLoanApplyById(applyId);
        CustUserInfo userInfo = custUserInfoManager.getById(userId);
        RepayPlanOP repayPlanOP = new RepayPlanOP();
        repayPlanOP.setApplyId(applyId);
        Map<String, Object> repayPlan = loanRepayPlanService.getRepayPlan(repayPlanOP);
        @SuppressWarnings("unchecked")
		List<Map<String, Object>> repayPlanItemList = (List<Map<String, Object>>) repayPlan.get("list");

        CarefreeCounterfoilDataOP dataOP = new CarefreeCounterfoilDataOP();

        String startDate = DateUtils.formatDate(payDate, "yyyy-MM-dd");
        String endDate = DateUtils.formatDate(ContractHelper.getLoanEndDate(apply, payDate));

        dataOP.setPactNo(payNo);
        dataOP.setBorrowerName(user.getRealName());
        dataOP.setBorrowerIdCard(user.getIdNo());
        dataOP.setBorrowerAddress(userInfo.getRegAddr());
        dataOP.setBorrowerPhone(user.getMobile());
        dataOP.setBorrowerEmail(user.getEmail());
        dataOP.setLoanAmt(MoneyUtils.convert(apply.getApproveAmt().subtract(apply.getServFee()).toString()));
        dataOP.setBeginLoanDate(startDate);
        dataOP.setEndLoanDate(endDate);
        dataOP.setLoanLimit(String.valueOf(apply.getApproveTerm()));
        dataOP.setLoanUse("购物");
        dataOP.setOtherCondition("");
        dataOP.setPayee(user.getRealName());
        dataOP.setReciptBank(BankCodeEnum.getName(user.getBankCode()));
        dataOP.setReciptBankNo(user.getCardNo());
        dataOP.setTransferBank(BankCodeEnum.getName(user.getBankCode()));
        // 购物协议参数
        dataOP.setRepaymentDate(endDate);
        dataOP.setRepaymentProductMoney(apply.getServFee().toString());
        dataOP.setAdvanceFundMoney(apply.getServFee().multiply(new BigDecimal(apply.getApproveTerm()))
                .multiply(new BigDecimal(0.001)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        dataOP.setSumLoanAmt(dataOP.getLoanAmt());
        dataOP.setAdvanceFundDay(String.valueOf(apply.getApproveTerm()));
        dataOP.setPruductAdvanceFundSumMoney(apply.getServFee().multiply(new BigDecimal(apply.getApproveTerm()))
                .multiply(new BigDecimal(0.001)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        for (int i = 0; i < repayPlanItemList.size(); i++) {
        	Map<String, Object> itemMap = repayPlanItemList.get(i);
        	dataOP.getRepayPlanItemList().add(
        			dataOP.new LoanRepayPlanItemOP("第" + NumberUtils.toCH(i + 1) + "期", 
        					(String)itemMap.get("repayDate"), (BigDecimal)itemMap.get("repayAmt")));
		}
        PactRecord pactRecord = generateCardfreeCounterfoil(dataOP);
        return pactRecord;

    }

}
