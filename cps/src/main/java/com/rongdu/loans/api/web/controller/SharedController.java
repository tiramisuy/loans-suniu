package com.rongdu.loans.api.web.controller;

import com.rongdu.loans.loan.option.share.CustInfo;
import com.rongdu.loans.sys.web.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/5/23
 * @since 1.0.0
 */
@Slf4j
@Controller
@RequestMapping(value = "/shared")
public class SharedController {

    @ResponseBody
    @RequestMapping(value = "/pushCustInfo", method = RequestMethod.POST)
    public ApiResult pushCustInfo(CustInfo custInfo){
        ApiResult apiResult = new ApiResult();



        return apiResult;
    }
}
