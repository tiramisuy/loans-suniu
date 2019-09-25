package com.rongdu.loans.loan.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.TongLianStatusEnum;
import com.rongdu.loans.pay.service.TonglianWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;
import com.rongdu.common.annotation.ExportLimit;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.excel.ExportExcel;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.RoleControl;
import com.rongdu.loans.common.RoleControlParam;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.hanjs.service.HanJSUserService;
import com.rongdu.loans.hanjs.vo.HanJSResultVO;
import com.rongdu.loans.koudai.service.KDPayService;
import com.rongdu.loans.loan.op.WithdrawOP;
import com.rongdu.loans.loan.option.WithdrawDetailListOP;
import com.rongdu.loans.loan.service.ContractService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.service.ShopWithholdService;
import com.rongdu.loans.loan.vo.AdminWebResult;
import com.rongdu.loans.loan.vo.WithdrawDetailListVO;
import com.rongdu.loans.oa.entity.DataDownload;
import com.rongdu.loans.oa.service.DataDownloadService;
import com.rongdu.loans.pay.service.BaofooWithdrawService;
import com.rongdu.loans.pay.service.TRBaofooWithdrawService;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.utils.UserUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WAITING_WITHDRAWAL;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.CASH_WITHDRAWAL;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WITHDRAWAL_FAIL;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS;


/**
 * Created by zhangxiaolong on 2017/6/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/withdraw")
public class WithdrawController extends BaseController {

    @Autowired
    private PayLogService payLogService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private ShopWithholdService shopWithholdService;
    @Autowired
    private BaofooWithdrawService baofooWithdrawService;
    @Autowired
    private TRBaofooWithdrawService trBaofooWithdrawService;
    @Autowired
    private TonglianWithdrawService tonglianWithdrawService;
    @Autowired
    private DataDownloadService dataDownloadService;
    @Autowired
    private HanJSUserService hanJSUserService;
    @Autowired
    private KDPayService kdPayService;

    /**
     * 提现列表
     *
     * @param withdrawOP
     * @param model
     * @return
     */
    @RequestMapping(value = "list")
    public String list(WithdrawOP withdrawOP, Boolean first, Model model) {
        if (null != first && first) {
            model.addAttribute("page", new Page(1, 10));
            return "modules/loan/withdrawList";
        }
		List<ApplyStatusLifeCycleEnum> withdrawEnums = Arrays.asList(WAITING_WITHDRAWAL, WITHDRAWAL_SUCCESS,
				WITHDRAWAL_FAIL, CASH_WITHDRAWAL);
        model.addAttribute("withdrawEnums", withdrawEnums);
        model.addAttribute("withdrawOP", withdrawOP);
        WithdrawDetailListOP op = withdrawOP2WithdrawDetailListOP(withdrawOP);
/*		RoleControlParam roleControlParam = RoleControl.get(op.getProductId(), op.getCompanyId());
		op.setProductId(roleControlParam.getProductId());
		op.setCompanyId(roleControlParam.getCompanyId());*/
        model.addAttribute("page", payLogService.withdrawList(op));
        return "modules/loan/withdrawList";
    }

    @RequestMapping(value = "exportWithdraw")
    @ExportLimit
    public void exportWithdraw(WithdrawOP withdrawOP, HttpServletRequest request, HttpServletResponse response,
                               RedirectAttributes redirectAttributes) throws IOException {
        User user = UserUtils.getUser();
        logger.info("导出提现明细--->{}--->{}", user.getId(), user.getName());
        ExportExcel excel = null;
        try {
            excel = new ExportExcel("提现明细", WithdrawDetailListVO.class);
            String fileName = "提现明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            WithdrawDetailListOP op = withdrawOP2WithdrawDetailListOP(withdrawOP);
            RoleControlParam roleControlParam = RoleControl.get(op.getProductId(), op.getCompanyId());
            op.setProductId(roleControlParam.getProductId());
            //op.setCompanyId(roleControlParam.getCompanyId());
            List<WithdrawDetailListVO> result = payLogService.exportWithdrawList(op);

            excel.setDataList(result).write(response, fileName);
        } finally {
            if (excel != null)
                excel.dispose();
        }
    }

    /**
     * 手动提现
     */
    @ResponseBody
    @RequestMapping(value = "withdraw")
    public WebResult withdraw(@RequestParam(value = "applyId") String applyId) {
        try {
            contractService.withdraw(applyId);
            return new WebResult("1", "提交成功", null);
        } catch (RuntimeException e) {
            logger.error("手动提现异常：applyId = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("手动提现异常：applyId = " + applyId, e);
            return new WebResult("99", "系统异常");
        }
    }

    private WithdrawDetailListOP withdrawOP2WithdrawDetailListOP(WithdrawOP withdrawOP) {
        WithdrawDetailListOP op = BeanMapper.map(withdrawOP, WithdrawDetailListOP.class);
        if (StringUtils.isNotBlank(withdrawOP.getApplyStart())) {
            op.setApplyTimeStart(DateUtils.parse(withdrawOP.getApplyStart()));
        }
        if (StringUtils.isNotBlank(withdrawOP.getApplyEnd())) {
            op.setApplyTimeEnd(DateUtils.parse(withdrawOP.getApplyEnd()));
        }
        if (StringUtils.isNotBlank(withdrawOP.getAccountStart())) {
            op.setAccountTimeStart(DateUtils.parse(withdrawOP.getAccountStart()));
        }
        if (StringUtils.isNotBlank(withdrawOP.getAccountEnd())) {
            op.setAccountTimeEnd(DateUtils.parse(withdrawOP.getAccountEnd()));
        }
        if (StringUtils.isNotBlank(withdrawOP.getSendStart())) {
            op.setSendTimeStart(DateUtils.parse(withdrawOP.getSendStart()));
        }
        if (StringUtils.isNotBlank(withdrawOP.getSendEnd())) {
            op.setSendTimeEnd(DateUtils.parse(withdrawOP.getSendEnd()));
        }
        return op;
    }

    /**
     * 手动放款 目前仅聚宝钱包产品可以后台手动放款
     */
    @ResponseBody
    @RequestMapping(value = "processAdminLendPay")
    public WebResult processAdminLendPay(@RequestParam(value = "applyId") String applyId,
                                         @RequestParam(value = "loanTime") String loanTime) {
        User user = UserUtils.getUser();
        logger.info("后台手动放款--->{}--->{}--->{}", user.getId(), user.getName(), applyId);
        String loanPaymentLockCacheKey = "loan_payment_lock_" + applyId;
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        try {
            Date loanDate = new Date();
            if (StringUtils.isNotBlank(loanTime)) {
                loanDate = DateUtils.parse(loanTime);
            }
            Boolean isLendPay = UserUtils.haveRole("sdcz");// 手动放款还款
            Boolean isPayment = UserUtils.haveRole("jbqb"); // 聚宝钱包角色用自有资金放款
            if (isLendPay) {
                synchronized (WithdrawController.class) {
                    String loanPaymentLock = JedisUtils.get(loanPaymentLockCacheKey);
                    if (loanPaymentLock == null) {
                        // 加锁，防止并发
                        JedisUtils.set(loanPaymentLockCacheKey, requestId, 30 * 60);
                    } else {
                        logger.warn("手动放款接口调用中，applyId= {}", applyId);
                        return new WebResult("99", "请勿重复提交");
                    }
                }
                contractService.processAdminLendPay(applyId, loanDate, isPayment);
            }
            return new WebResult("1", "提交成功", null);
        } catch (RuntimeException e) {
            logger.error("手动提现异常：applyId = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("手动提现异常：applyId = " + applyId, e);
            return new WebResult("99", "系统异常");
        } finally {
            // 根据请求标识移除锁 code y0621
            // JedisUtils.del(loanPaymentLockCacheKey);
            JedisUtils.releaseLock(loanPaymentLockCacheKey, requestId);
        }
    }

    /**
     * 推标放款
     */
    @ResponseBody
    @RequestMapping(value = "processBorrowLendPay")
    public WebResult processBorrowLendPay(@RequestParam(value = "applyId") String applyId) {
        User user = UserUtils.getUser();
        logger.info("推标放款--->{}--->{}--->{}", user.getId(), user.getName(), applyId);
        String loanBorrowPaymentLockCacheKey = "loan_borrow_payment_lock_" + applyId;
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        try {
            Date loanDate = new Date();
            Boolean isLendPay = UserUtils.haveRole("sdcz");
            Boolean isPayment = UserUtils.haveRole("jbqb"); // 聚宝钱包角色用自有资金放款
            if (isLendPay) {
                synchronized (WithdrawController.class) {
                    String loanBorrowPaymentLock = JedisUtils.get(loanBorrowPaymentLockCacheKey);
                    if (loanBorrowPaymentLock == null) {
                        // 加锁，防止并发
                        JedisUtils.set(loanBorrowPaymentLockCacheKey, requestId, 30 * 60);
                    } else {
                        logger.warn("手动推标放款接口调用中，applyId= {}", applyId);
                        return new WebResult("99", "请勿重复提交");
                    }
                }
                contractService.processBorrowLendPay(applyId, loanDate, isPayment);
            }
            return new WebResult("1", "提交成功", null);
        } catch (RuntimeException e) {
            logger.error("手动推标放款异常：applyId = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("手动推标放款异常：applyId = " + applyId, e);
            return new WebResult("99", "系统异常");
        } finally {
            // 根据请求标识移除锁 code y0621
            // JedisUtils.del(loanPaymentLockCacheKey);
            // JedisUtils.releaseLock(loanBorrowPaymentLockCacheKey, requestId);
        }
    }

    /**
     * 导出借款协议
     *
     * @throws IOException
     */
    @RequestMapping("/exportContract")
    public void exportContractWord(String contNo, String type, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-msdown");
        response.setHeader("Content-Disposition",
                "attachment;filename=".concat(String.valueOf(URLEncoder.encode("借款协议.pdf", "UTF-8"))));
        // 组装数据
        Map<String, Object> dataMap = loanApplyService.getContractDetail(contNo, type);
        StringWriter writer = null;
        OutputStream out = null;
        try {
            // 创建一个FreeMarker实例, 负责管理FreeMarker模板的Configuration实例
            Configuration cfg = new Configuration();
            // 指定FreeMarker模板文件的位置
            String templateFolder = this.getClass().getClassLoader().getResource("../../").getPath()
                    + "WEB-INF/classes/templates/modules/ftl/";
            cfg.setDirectoryForTemplateLoading(new File(templateFolder));
            cfg.setDefaultEncoding("UTF-8");
            // 获取模板文件
            Template template = null;
            if ("xjd".equals(type)) {
                if ((1 < (Integer) dataMap.get("totalTerm"))) {
                    template = cfg.getTemplate("xjdAgreement.ftl");
                } else {
                    template = cfg.getTemplate("xjdAgreement2.ftl");
                }
            } else if ("koudai".equals(type)) {
                template = cfg.getTemplate("kdAgreement.ftl");
            }
            // 数据写入模板
            writer = new StringWriter();
            template.setEncoding("UTF-8");
            template.process(dataMap, writer);
            writer.flush();
            String htmlStr = writer.toString();
            // 将生成的html 转换成PDF文件
            ITextRenderer render = new ITextRenderer();
            // 解决中文问题
            ITextFontResolver fontResolver = render.getFontResolver();
            fontResolver.addFont(
                    getClass().getClassLoader().getResource("../../").getPath()
                            + "WEB-INF/classes/templates/modules/font/SIMSUN.TTC",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            fontResolver.addFont(
                    getClass().getClassLoader().getResource("../../").getPath()
                            + "WEB-INF/classes/templates/modules/font/SIMHEI.TTF",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            out = response.getOutputStream();
            render.setDocumentFromString(htmlStr);
            render.layout();
            render.createPDF(out);
            render.finishPDF();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * 生成合同
     */
    @ResponseBody
    @RequestMapping(value = "/generatorContract")
    public WebResult generatorContract(@RequestParam(value = "payLogId") String payLogId) {
        User user = UserUtils.getUser();
        logger.info("生成合同--->{}--->{}--->{}", user.getId(), user.getName(), payLogId);
        String loanPayLogGenerratorConractLockCacheKey = "loan_pay_log_generatorcontract_lock_" + payLogId;
        try {
            String lock = JedisUtils.get(loanPayLogGenerratorConractLockCacheKey);
            if (lock == null) {
                // 加锁，防止并发
                JedisUtils.set(lock, payLogId, 30 * 60);
            } else {
                logger.warn("合同生成中，payLogId= {}", payLogId);
                return new WebResult("99", "请勿重复提交");
            }
            return new WebResult("1", "提交成功", payLogService.generatorContract(payLogId));
        } catch (Exception e) {
            logger.error("生成合同出现异常：payLogId = " + payLogId, e);
            return new WebResult("99", "系统异常");
        } finally {
            JedisUtils.releaseLock(loanPayLogGenerratorConractLockCacheKey, payLogId);
        }
    }

    /**
     * 生成债转合同
     */
    @ResponseBody
    @RequestMapping(value = "/generateEquitableAssignment")
    public WebResult generateEquitableAssignment(WithdrawOP withdrawOP) {
        User user = UserUtils.getUser();
        logger.info("生成债转合同--->{}--->{}", user.getId(), user.getName());
        try {
            WithdrawDetailListOP op = new WithdrawDetailListOP();
            op.setAccountTimeStart(DateUtils.parseDate(withdrawOP.getAccountStart()));
            op.setAccountTimeEnd(DateUtils.parseDate(withdrawOP.getAccountEnd()));
            String url = payLogService.generateEquitableAssignment(op);
            // 保存合同
            DataDownload dataDownload = new DataDownload(1,
                    "债权转让确认单" + DateUtils.formatDate(op.getAccountTimeEnd(), "yyyyMMdd"), url, op.getAccountTimeStart(),
                    op.getAccountTimeEnd(), "", 0);
            dataDownloadService.save(dataDownload);
            return new WebResult("1", "生成债转合同成功", payLogService.generateEquitableAssignment(op));
        } catch (RuntimeException e) {
            logger.error("生成合同出现异常", e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("生成合同出现异常", e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 修改还款日
     *
     * @param applyId
     * @param loanTime
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/changeRepayDate")
    public WebResult changeRepayDate(@RequestParam(value = "applyId") String applyId,
                                     @RequestParam(value = "loanTime") Date loanTime) {
        User user = UserUtils.getUser();
        logger.info("修改还款日--->{}--->{}", user.getId(), user.getName());
        try {
            if (StringUtils.isBlank(applyId) || loanTime == null) {
                logger.warn("找不到参数，applyId= {}，loanTime= {}", applyId, loanTime);
                return new WebResult("99", "参数异常");
            }
            shopWithholdService.updateRepayTime(applyId, loanTime);
            return new WebResult("1", "提交成功", null);

        } catch (RuntimeException e) {
            logger.error("手动推标放款异常：applyId = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("手动推标放款异常：applyId = " + applyId, e);
            return new WebResult("99", "系统异常");
        } finally {
        }
    }

    /**
     * 通联/宝付/重新放款
     */
    @ResponseBody
    @RequestMapping(value = "reWithdraw")
    public AdminWebResult reWithdraw(@RequestParam(value = "applyId") String applyId,
                                     @RequestParam(value = "payNo") String payNo, @RequestParam(value = "chlCode") String chlCode) {
        String lockKey = "reWithdraw_" + applyId;
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        try {
            boolean lock = JedisUtils.setLock(lockKey, requestId, 5 * 60);
            if (!lock) {
                logger.warn("重新放款接口调用中,applyId={},payNo= {}", applyId, payNo);
                return new AdminWebResult("3", "操作频繁，请五分钟后再试！");
            }
            if (Global.BAOFOO_CHANNEL_CODE.equals(chlCode)) {
                return baofooWithdrawService.reWithdraw(payNo);
            } else if (Global.TONGRONG_BAOFOO_CHANNEL_CODE.equals(chlCode)) {
                return trBaofooWithdrawService.reWithdraw(payNo);
            } else if (Global.TONGLIAN_LOAN_CHANNEL_CODE.equals(chlCode)) {
                return tonglianWithdrawService.reWithdraw(payNo);
            } else {
                return new AdminWebResult("99", "未知的放款渠道");
            }
        } catch (RuntimeException e) {
            logger.error("重新放款异常：applyId = " + applyId, e);
            return new AdminWebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("重新放款异常：applyId = " + applyId, e);
            return new AdminWebResult("99", "系统异常");
        } finally {
            // JedisUtils.releaseLock(lockKey, requestId);
        }
    }

    /**
     * (汉金所)修改放款渠道
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/changePaychannel")
    public WebResult changePaychannel(@RequestParam(value = "id") String id,
                                      @RequestParam(value = "paychannel") String paychannel, @RequestParam(value = "applyId") String applyId) {
        User user = UserUtils.getUser();
        logger.info("修改汉金所订单放款渠道--->{}--->{}", user.getId(), user.getName());
        try {
            String lockKey = "changeHJSPaychannel_lock_" + id;
            String requestId = String.valueOf(System.nanoTime());// 请求标识
            // 根据id防并发加锁
            boolean lock = JedisUtils.setLock(lockKey, requestId, 60 * 5);

            if (!lock) {
                logger.warn("修改汉金所订单放款渠道调用中，id= {}", id);
                return new WebResult("99", "操作频繁，请5分钟后再试！", null);
            }

            if (StringUtils.isBlank(id) || paychannel == null) {
                logger.warn("找不到参数，payLog= {}，paychannel= {}", id, paychannel);
                return new WebResult("99", "参数异常");
            }
            if (StringUtils.isBlank(id) || StringUtils.isBlank(paychannel) || StringUtils.isBlank(applyId)) {
                logger.warn("找不到参数，payLog= {}，paychannel= {}, applyId= {}", id, paychannel, applyId);
                return new WebResult("99", "参数异常");
            }
            synchronized (WithdrawController.class) {
                AdminWebResult vo = hanJSUserService.changeOrder(id, applyId, paychannel);
                return new WebResult(vo.getCode(), vo.getMsg(), null);
            }
        } catch (RuntimeException e) {
            logger.error("修改放款渠道异常：payLog = " + id, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("修改放款渠道异常：payLog = " + id, e);
            return new WebResult("99", "系统异常");
        } finally {
        }
    }

    /**
     * 查询汉金所订单状态
     *
     * @param applyId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryHanjsOrderStatus")
    public WebResult queryHanjsOrderStatus(@RequestParam(value = "applyId") String applyId) {
        User user = UserUtils.getUser();
        logger.info("查询汉金所订单状态--->{}--->{}", user.getId(), user.getName());
        try {
            if (StringUtils.isBlank(applyId)) {
                logger.warn("找不到参数，applyId= {}", applyId);
                return new WebResult("99", "参数异常");
            }
            HanJSResultVO vo = hanJSUserService.queryOrderState(applyId);
            return new WebResult("1", vo.getMessage(), null);

        } catch (RuntimeException e) {
            logger.error("查询汉金所订单状态：payLog = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询汉金所订单状态：payLog = " + applyId, e);
            return new WebResult("99", "系统异常");
        } finally {
        }
    }

    /**
     * 取消汉金所订单
     *
     * @param applyId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cancelHanjsOrder")
    public WebResult cancelHanjsOrder(@RequestParam(value = "id") String id,
                                      @RequestParam(value = "applyId") String applyId) {
        User user = UserUtils.getUser();
        logger.info("取消汉金所订单--->{}--->{}", user.getId(), user.getName());
        try {
            if (StringUtils.isBlank(id) || StringUtils.isBlank(applyId)) {
                logger.warn("找不到参数,id={}，applyId= {}", id, applyId);
                return new WebResult("99", "参数异常");
            }
            AdminWebResult vo = hanJSUserService.cancelOrder(id, applyId);
            return new WebResult(vo.getCode(), vo.getMsg(), null);
        } catch (RuntimeException e) {
            logger.error("取消汉金所订单：payLog = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("取消汉金所订单：payLog = " + applyId, e);
            return new WebResult("99", "系统异常");
        } finally {
        }
    }

}
