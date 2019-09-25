/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongdu.common.web.BaseController;
import com.rongdu.loans.borrow.service.HelpResultService;

/**
 * 助贷结果表Controller
 * @author liuliang
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/helpResult")
public class HelpResultController extends BaseController {

	@Autowired
	private HelpResultService helpResultService;
	

}