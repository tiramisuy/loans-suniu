package com.jubao.cps.utils;

import com.rongdu.common.config.Global;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.option.jdq.AddressBook;
import com.rongdu.loans.loan.option.jdq.Calls;
import com.rongdu.loans.loan.option.jdq.Count;
import com.rongdu.loans.loan.option.xjbk.ContactList;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lee on 2018/12/11.
 */
public class CpsUtil {
    private static FileServerClient fileServerClient = new FileServerClient();

    public static Map<String, Count> CallCount(String type, Calls calls, Map<String, Count> call) {
        if (calls.getInitType().equals(type)) {
            Count object = call.get(calls.getOtherCellPhone());
            if (object == null) {
                Count count1 = new Count();
                count1.setCount(1);
                count1.setSum(calls.getUseTime());
                count1.setGsd(calls.getPlace());
                count1.setFirstCall(calls.getStartTime());
                count1.setLastCall(calls.getStartTime());
                call.put(calls.getOtherCellPhone(), count1);
            } else {
                object.setCount(object.getCount() + 1);
                object.setSum(object.getSum() + calls.getUseTime());
                if (DateUtils.compareDate(object.getFirstCall(), calls.getStartTime()) == 1) {
                    object.setFirstCall(calls.getStartTime());
                }
                if (DateUtils.compareDate(object.getLastCall(), calls.getStartTime()) == -1) {
                    object.setLastCall(calls.getStartTime());
                }
                call.put(calls.getOtherCellPhone(), object);
            }
        }
        return call;
    }

    public static List<ContactList> getTopCalInCntList(List<ContactList> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, new Comparator<ContactList>() {
                @Override
                public int compare(ContactList o1, ContactList o2) {
                    ContactList ccm1 = o1;
                    ContactList ccm2 = o2;
                    if (ccm1.getCallInCnt() > ccm2.getCallInCnt()) {
                        return -1;
                    }
                    if (ccm1.getCallInCnt() < ccm2.getCallInCnt()) {
                        return 1;
                    }
                    return 0;
                }
            });
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<ContactList> list = ccmList.subList(0, top);
            return list;
        } else {
            List<ContactList> list = new ArrayList<ContactList>();
            return list;
        }
    }

    public static List<ContactList> getTopCallOutCntList(List<ContactList> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, new Comparator<ContactList>() {
                @Override
                public int compare(ContactList o1, ContactList o2) {
                    ContactList ccm1 = o1;
                    ContactList ccm2 = o2;
                    if (ccm1.getCallOutCnt() > ccm2.getCallOutCnt()) {
                        return -1;
                    }
                    if (ccm1.getCallOutCnt() < ccm2.getCallOutCnt()) {
                        return 1;
                    }
                    return 0;
                }
            });
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<ContactList> list = ccmList.subList(0, top);
            return list;
        } else {
            List<ContactList> list = new ArrayList<ContactList>();
            return list;
        }
    }

    public static List jdqContactMatch(List<ContactList> contactLists, List<AddressBook> phoneList) {
        List ccmList2 = new ArrayList();
        for (ContactList contactList : contactLists) {
            String phoneNum = contactList.getPhoneNum();
            String contactName = "";
            if (phoneList != null) {
                for (AddressBook phone : phoneList) {
                    String contactMobile = phone.getMobile();
                    if (StringUtils.equals(phoneNum, contactMobile)) {
                        contactName = phone.getName();
                        break;
                    }
                }
            }
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("mobile", phoneNum);
            m.put("belongTo", contactList.getPhoneNumLoc());
            m.put("contact_1w", contactList.getContact1w());
            m.put("contact_1m", contactList.getContact1m());
            m.put("contact_3m", contactList.getContact3m());
            m.put("contact_3m_plus", contactList.getContact3mPlus());
            m.put("call_cnt", contactList.getCallCnt());
            m.put("call_len", new Double(contactList.getCallLen()).intValue());
            m.put("terminatingCallCount", contactList.getCallInCnt());
            m.put("terminatingTime", new Double(contactList.getCallInLen()).intValue());
            m.put("originatingCallCount", contactList.getCallOutCnt());
            m.put("originatingTime", new Double(contactList.getCallOutLen()).intValue());
            m.put("contactName", contactName);
            m.put("lastCall", contactList.getLastCall());
            m.put("firstCall", contactList.getFirstCall());
            ccmList2.add(m);
        }
        return ccmList2;
    }

    public static <T> String uploadBaseData(T jdqReport, String code, String userId, String orderSn) {
        UploadParams params = new UploadParams();
        String clientIp = "127.0.0.1";
        String source = "4";
        params.setUserId(userId);
        params.setApplyId(orderSn);
        params.setIp(clientIp);
        params.setSource(source);
        params.setBizCode(code);
        String fileBodyText = JsonMapper.toJsonString(jdqReport);
        String fileExt = "txt";
        String res = fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
        return res;
    }

    public static PromotionCaseOP getPromotionCase(String flag) {
        PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
        switch (flag) {
            case "1":
                promotionCaseOP.setApplyAmt(new BigDecimal("3000.00"));
                promotionCaseOP.setApplyTerm(Global.XJD_AUTO_FQ_DAY_28);
                promotionCaseOP.setChannelId(ChannelEnum.JUQIANBAO.getCode());
                promotionCaseOP.setProductId(LoanProductEnum.XJD.getId());

                promotionCaseOP.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
                promotionCaseOP.setRepayFreq("D");
                promotionCaseOP.setRepayUnit(7);
                promotionCaseOP.setTerm(4);
                break;
            case "2":
                promotionCaseOP.setApplyAmt(new BigDecimal("1500.00"));
                promotionCaseOP.setApplyTerm(Global.XJD_DQ_DAY_15);
                promotionCaseOP.setChannelId(ChannelEnum.JUQIANBAO.getCode());
                promotionCaseOP.setProductId(LoanProductEnum.XJD.getId());

                promotionCaseOP.setRepayMethod(RepayMethodEnum.ONE_TIME.getValue());
                promotionCaseOP.setRepayFreq("D");
                promotionCaseOP.setRepayUnit(15);
                promotionCaseOP.setTerm(1);
                break;
            case "3":
                promotionCaseOP.setApplyAmt(new BigDecimal("1500.00"));
                promotionCaseOP.setApplyTerm(Global.XJD_DQ_DAY_14);
                promotionCaseOP.setChannelId(ChannelEnum.JUQIANBAO.getCode());
                promotionCaseOP.setProductId(LoanProductEnum.XJD.getId());

                promotionCaseOP.setRepayMethod(RepayMethodEnum.ONE_TIME.getValue());
                promotionCaseOP.setRepayFreq("D");
                promotionCaseOP.setRepayUnit(14);
                promotionCaseOP.setTerm(1);
                break;
            default:
                promotionCaseOP = getPromotionCase("1");
                break;
        }
        return promotionCaseOP;
    }

    public static boolean isUnsolved(String status) {
        if (StringUtils.isBlank(status)) {
            return false;
        }
        String[] statusArr = {"BF00100", "BF00112", "BF00113", "BF00115", "BF00144", "BF00202", "I"};
        for (String str : statusArr) {
            if (status.equals(str)) {
                return true;
            }
        }
        return false;
    }


}
