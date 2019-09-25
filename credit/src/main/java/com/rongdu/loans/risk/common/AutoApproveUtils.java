package com.rongdu.loans.risk.common;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportEmergencyContact;
import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;
import com.rongdu.loans.baiqishi.entity.ReportMnoMui;
import com.rongdu.loans.baiqishi.vo.Contact;
import com.rongdu.loans.baiqishi.vo.DeviceContactVO;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.enums.RelationshipEnum;
import com.rongdu.loans.loan.option.rongTJreportv1.CallLog;
import com.rongdu.loans.loan.option.xjbk.ContactList;
import com.rongdu.loans.loan.option.xjbk.ContactRegion;
import com.rongdu.loans.risk.comparator.SortByCalInCnt;
import com.rongdu.loans.risk.comparator.SortByCallOutCnt;
import com.rongdu.loans.risk.comparator.SortByConnectTime;
import com.rongdu.loans.risk.comparator.SortByContactConnectTime;
import com.rongdu.loans.risk.comparator.SortByContactRegion;
import com.rongdu.loans.risk.comparator.SortByMnoMonth;
import com.rongdu.loans.risk.comparator.SortByOriginatingCallCount;
import com.rongdu.loans.risk.comparator.SortByRongCallInCnt;
import com.rongdu.loans.risk.comparator.SortByRongCallOutCnt;
import com.rongdu.loans.risk.comparator.SortByTerminatingCallConnectCount;

/**
 * 自动审核工具类
 *
 * @author liuzhuang
 * @version 2018-04-17
 */
public class AutoApproveUtils {

    /**
     * 获得申请人工作所在地
     *
     * @param userInfo
     * @return
     */
    public static String getWorkArea(UserInfoVO userInfo) {
        StringBuilder builder = new StringBuilder("");
        if (StringUtils.isNotBlank(userInfo.getWorkProvince())) {
            builder.append(userInfo.getWorkProvince());
        }
        if (StringUtils.isNotBlank(userInfo.getWorkCity())) {
            builder.append(userInfo.getWorkCity());
        }
        // if (StringUtils.isNotBlank(userInfo.getWorkDistrict())){
        // builder.append(userInfo.getWorkDistrict());
        // }
        return builder.toString();
    }

    /**
     * 过滤地区中的干扰字段
     *
     * @param area
     * @return
     */
    public static String filterArea(String area) {
        if (StringUtils.isNotBlank(area)) {
            String regex = "省|市|区|县|旗|自治区";
            return area.replaceAll(regex, "");
        }
        return area;
    }

    /**
     * 是否为手机号码
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        boolean match = false;
        if (StringUtils.isNotBlank(mobile)) {
            String regex = "1(3|4|5|6|7|8|9)\\d{9}";
            if (mobile.matches(regex)) {
                match = true;
            }
        }
        return match;
    }

    /**
     * 从字符串中提取数字，返回第一个匹配的数字
     *
     * @param str
     * @return
     */
    public static int extractNumFromString(String str) {
        String reg = "\\d+";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return 0;
    }

    /**
     * 判断字符串中是否包含字符数组中的的仁义字符
     *
     * @param str
     * @param list
     * @return
     */
    public static boolean containsAny(String str, List<String> list) {
        boolean match = false;
        for (String item : list) {
            if (StringUtils.contains(str, item)) {
                return true;
            }
        }
        return match;
    }

    /**
     * 获得父母或者配偶的手机号码
     *
     * @param contactList
     * @return
     */
    public static List<String> getFamilyContact(List<CustContactVO> contactList) {
        List<String> list = new ArrayList<>();
        for (CustContactVO contact : contactList) {
            if (1 == contact.getRelationship() || 2 == contact.getRelationship()) {
                list.add(contact.getMobile());
            }
        }
        return list;
    }

    /**
     * 获得配偶的手机号码
     *
     * @param contactList
     * @return
     */
    public static List<String> getSpouseContact(List<CustContactVO> contactList) {
        List<String> list = new ArrayList<>();
        for (CustContactVO contact : contactList) {
            if (2 == contact.getRelationship()) {
                list.add(contact.getMobile());
            }
        }
        return list;
    }

    /**
     * 关于“近半年内某一个月”的情况，根据贷款申请日期是否过月半（15日）来决定截止月份，如：
     * 1、2017年8月1日~2017年8月14日申请贷款，那么截止月份为7月
     * 2、2017年8月15日~2017年8月31日申请贷款，那么截止月份为8月
     *
     * @param applyTimeSting
     * @return
     */
    public static long getMonthMillis(String applyTimeSting) {
        long millis = 0;
        Date applyTime = DateUtils.parseDate(applyTimeSting);
        Calendar applyCalendar = Calendar.getInstance();
        applyCalendar.setTime(applyTime);
        int day = applyCalendar.get(Calendar.DAY_OF_MONTH);
        Date thisMonth = DateUtils.parseDate(DateUtils.getDate("yyyy-MM"));
        if (day < 15) {
            // 贷款申请当月1号0点0分0秒的毫秒数
            millis = thisMonth.getTime();
        } else {
            // 贷款申请时刻的毫秒数
            millis = applyTime.getTime();
        }
        return millis;
    }

    /**
     * 运营商近半年的常用通话记录TOP6
     *
     * @param list
     * @param top
     * @return
     */
    public static List<ReportMnoMui> getTopList(List<ReportMnoMui> list, int top) {
        Collections.sort(list, Collections.reverseOrder(new SortByMnoMonth()));
        top = (list.size() < top) ? list.size() : top;
        List<ReportMnoMui> subList = list.subList(0, top);
        return subList;
    }

    /**
     * 将百分比字符串转化为小数，如：80%—0.8
     *
     * @param percentageString
     * @return
     */
    public static double parsePercentage(String percentageString) {
        double percentage = 0.0D;
        DecimalFormat df = new DecimalFormat("0.00%");
        Number number = null;
        try {
            number = df.parse(percentageString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (number != null) {
            percentage = number.doubleValue();
        }
        return percentage;
    }

    /**
     * 通讯录号码去除空格，连接符等
     *
     * @param mobile
     * @return
     */
    public static String parseContactMobile(String mobile) {
        if (mobile == null)
            return "";
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(mobile);
        return m.replaceAll("").trim();
    }

    /**
     * 统计该用户与该手机号码半年内半年的通话次数
     *
     * @param ccmList
     * @param mobile
     * @return
     */
    public static int getConnectCount(List<ReportMnoCcm> ccmList, String mobile) {
        int connectCount = 0;
        String ccmMobile = null;
        for (ReportMnoCcm ccm : ccmList) {
            ccmMobile = ccm.getMobile();
            if (StringUtils.equals(mobile, ccmMobile)) {
                connectCount = ccm.getConnectCount();
                break;
            }
        }
        return connectCount;
    }

    /**
     * 统计该用户与该手机号码半年内半年的通话次数
     *
     * @param ccmList
     * @param mobile
     * @return
     */
    public static int getConnectCountByShortNumber(List<ReportMnoCcm> ccmList, String mobile) {
        int connectCount = 0;
        String ccmMobile = null;
        for (ReportMnoCcm ccm : ccmList) {
            ccmMobile = ccm.getMobile();
            if (StringUtils.endsWith(mobile, ccmMobile)) {
                connectCount = ccm.getConnectCount();
                break;
            }
        }
        return connectCount;
    }

    /**
     * 统计该用户与该手机号码半年内半年的通话时间（秒）
     *
     * @param ccmList
     * @param mobile
     * @return
     */
    public static int getConnectTime(List<ReportMnoCcm> ccmList, String mobile) {
        int connectTime = 0;
        String ccmMobile = null;
        for (ReportMnoCcm ccm : ccmList) {
            ccmMobile = ccm.getMobile();
            if (StringUtils.equals(mobile, ccmMobile)) {
                connectTime = ccm.getConnectTime();
                break;
            }
        }
        return connectTime;
    }

    /**
     * 统计该用户与该手机号码半年内半年的通话时间（秒）
     *
     * @param ccmList
     * @param mobile
     * @return
     */
    public static int getConnectTimeByShortNumber(List<ReportMnoCcm> ccmList, String mobile) {
        int connectTime = 0;
        String ccmMobile = null;
        for (ReportMnoCcm ccm : ccmList) {
            ccmMobile = ccm.getMobile();
            if (StringUtils.endsWith(mobile, ccmMobile)) {
                connectTime = ccm.getConnectTime();
                break;
            }
        }
        return connectTime;
    }

    /**
     * 是否存在短号码
     *
     * @param ccmList
     * @param mobile
     * @return
     */
    public static boolean existShortNumber(List<ReportMnoCcm> ccmList, String mobile) {
        boolean exist = false;
        String ccmMobile = null;
        for (ReportMnoCcm ccm : ccmList) {
            ccmMobile = ccm.getMobile();
            if (StringUtils.endsWith(mobile, ccmMobile) && !StringUtils.equals(mobile, ccmMobile)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    /**
     * 获得紧急联系人的手机号码
     *
     * @param contactList
     * @param relationshipEnum
     * @return
     */
    public static String getContactMobileByRelationship(List<CustContactVO> contactList,
                                                        RelationshipEnum relationshipEnum) {
        String contactMobile = "";
        int relationship = 0;
        for (CustContactVO contact : contactList) {
            relationship = contact.getRelationship().intValue();
            if (relationshipEnum.getValue().intValue() == relationship) {
                contactMobile = contact.getMobile();
                break;
            }
        }
        return contactMobile;
    }

    /**
     * 获取紧急联系人的最早通话时间
     *
     * @param ecList
     * @param mobile
     * @return
     */
    public static String getFirstConnectTime(List<ReportEmergencyContact> ecList, String mobile) {
        String connectTimeSting = "";
        if (ecList != null) {
            for (ReportEmergencyContact contact : ecList) {
                if (StringUtils.equals(mobile, contact.getMobile())) {
                    connectTimeSting = contact.getFirstConnectTime();
                    break;
                }
            }
        }
        return connectTimeSting;
    }

    /**
     * 运营商近6个月的常用通话记录TOP
     *
     * @param ccmList
     * @param top
     * @return
     */
    public static List<ReportMnoCcm> getTopCcmList(List<ReportMnoCcm> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, Collections.reverseOrder(new SortByConnectTime()));
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<ReportMnoCcm> list = ccmList.subList(0, top);
            return list;
        } else {
            List<ReportMnoCcm> list = new ArrayList<ReportMnoCcm>();
            return list;
        }
    }

    /**
     * 通讯录TOP
     *
     * @param contactList
     * @param top
     * @return
     */
    public static List<Map<String, Object>> getTopContactList(List<Map<String, Object>> contactList, int top) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (contactList != null) {
            Collections.sort(contactList, Collections.reverseOrder(new SortByContactConnectTime()));
            top = (contactList.size() < top) ? contactList.size() : top;
            for (int i = 0; i < top; i++) {
                list.add(contactList.get(i));
            }
            return list;
        } else {
            return list;
        }
    }

    /**
     * 通讯录移除手机号码为空的联系人
     *
     * @param vo
     */
    public static void removeEmptyContact(DeviceContactVO vo) {
        if (vo != null && vo.getContactsInfo() != null) {
            List<Contact> list = vo.getContactsInfo();
            int totalNum = list.size();
            Contact contact = null;
            for (int i = totalNum - 1; i >= 0; i--) {
                contact = list.get(i);
                if (StringUtils.isBlank(contact.getMobile())) {
                    list.remove(i);
                }
            }
        }
    }

    /**
     * 运营商近6个月的常用通话记录 呼入（被叫）TOP
     *
     * @param ccmList
     * @param top
     * @return
     */
    public static List<ReportMnoCcm> getTopCcmTerminatingCallList(List<ReportMnoCcm> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, Collections.reverseOrder(new SortByTerminatingCallConnectCount()));
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<ReportMnoCcm> list = ccmList.subList(0, top);
            return list;
        } else {
            List<ReportMnoCcm> list = new ArrayList<ReportMnoCcm>();
            return list;
        }
    }

    /**
     * 运营商近6个月的常用通话记录 呼出（主叫）TOP
     *
     * @param ccmList
     * @param top
     * @return
     */
    public static List<ReportMnoCcm> getTopCcmOriginatingCallList(List<ReportMnoCcm> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, Collections.reverseOrder(new SortByOriginatingCallCount()));
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<ReportMnoCcm> list = ccmList.subList(0, top);
            return list;
        } else {
            List<ReportMnoCcm> list = new ArrayList<ReportMnoCcm>();
            return list;
        }
    }


    /**
     * 运营商近6个月的常用通话记录 呼入（被叫）TOP
     *
     * @param ccmList
     * @param top
     * @return
     */
    public static List<ContactList> getTopCalInCntList(List<ContactList> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, Collections.reverseOrder(new SortByCalInCnt()));
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<ContactList> list = ccmList.subList(0, top);
            return list;
        } else {
            List<ContactList> list = new ArrayList<ContactList>();
            return list;
        }
    }

    /**
     * 运营商近6个月的常用通话记录 呼出（主叫）TOP
     *
     * @param ccmList
     * @param top
     * @return
     */
    public static List<ContactList> getTopCallOutCntList(List<ContactList> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, Collections.reverseOrder(new SortByCallOutCnt()));
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<ContactList> list = ccmList.subList(0, top);
            return list;
        } else {
            List<ContactList> list = new ArrayList<ContactList>();
            return list;
        }
    }

    public static List<ContactRegion> getTopContactRegion(List<ContactRegion> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, Collections.reverseOrder(new SortByContactRegion()));
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<ContactRegion> list = ccmList.subList(0, top);
            return list;
        } else {
            List<ContactRegion> list = new ArrayList<ContactRegion>();
            return list;
        }
    }
    
    /**
     * 运营商近6个月的常用通话记录呼入（被叫） TOP
     *
     * @param ccmList
     * @param top
     * @return
     */
    public static List<CallLog> getRongTopCallInCntList(List<CallLog> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, Collections.reverseOrder(new SortByRongCallInCnt()));
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<CallLog> list = new ArrayList<>(ccmList.subList(0, top));
            return list;
        } else {
            List<CallLog> list = new ArrayList<CallLog>();
            return list;
        }
    }
    
    /**
     * 运营商近6个月的常用通话记录呼出（主叫） TOP
     *
     * @param ccmList
     * @param top
     * @return
     */
    public static List<CallLog> getRongTopCallOutCntList(List<CallLog> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, Collections.reverseOrder(new SortByRongCallOutCnt()));
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<CallLog> list =  new ArrayList<>(ccmList.subList(0, top));
            return list;
        } else {
            List<CallLog> list = new ArrayList<CallLog>();
            return list;
        }
    }
}