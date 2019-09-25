package com.rongdu.loans.workbench.web;

import com.rongdu.common.web.BaseController;
import com.rongdu.loans.statistical.service.StatisticalService;
import com.rongdu.loans.statistical.vo.WorkbenchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by zhangxiaolong on 2017/6/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/workbench/")
public class WorkbenchController extends BaseController {

    @Autowired
    private StatisticalService statisticalService;

    /**
     * 工作台统计
     * @param model
     * @return
     */
    @RequestMapping(value = "statistical")
    public String statistical(Model model) {
//        model.addAttribute("vo",statisticalService.workbench());
        return "modules/workbench/workbenchView";
    }



}
