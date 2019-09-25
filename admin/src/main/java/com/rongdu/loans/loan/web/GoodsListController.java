/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.web;

import com.rongdu.common.config.Global;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.enums.GoodsStatusEnum;
import com.rongdu.loans.loan.option.GoodsListOp;
import com.rongdu.loans.loan.service.ShopService;
import com.rongdu.loans.loan.vo.GoodsListVO;
import com.rongdu.loans.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 商品信息表Controller
 * @author qifeng
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/goodsList")
public class GoodsListController extends BaseController {

    public static final String shopGoodsListKey = "SHOP_GOODS_LIST";

    @Autowired
    private ShopService shopService;

    @ModelAttribute
    public GoodsListVO get(@RequestParam(required=false) String id) {
        GoodsListVO entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = shopService.get(id);
        }
        if (entity == null){
            entity = new GoodsListVO();
        }
        return entity;
    }

    /*@RequiresPermissions("loan:goodsList:view")*/
    @RequestMapping(value = {"list", ""})
    public String list(GoodsListOp op, Model model) {
        model.addAttribute("goodsList", op);
        Page<GoodsListVO> page = shopService.queryGoodsList(op);
        model.addAttribute("page", page);
        return "modules/loan/goodsListList";
    }


    /*@RequiresPermissions("loan:goodsList:view")*/
    @RequestMapping(value = "form")
    public String form(GoodsListVO goodsList, Model model) {
        if(StringUtils.isNotBlank(goodsList.getPicDetail())){
            goodsList.setPicDetail(goodsList.getPicDetail().replace("\"","&quot;"));
        }
        if(StringUtils.isNotBlank(goodsList.getPicBanner())){
            goodsList.setPicBanner(goodsList.getPicBanner().replace("\"","&quot;"));
        }

        model.addAttribute("goodsList", goodsList);
        return "modules/loan/goodsListForm";
    }



    /*@RequiresPermissions("loan:goodsList:edit")*/
    @RequestMapping(value = "save")
    public String save(GoodsListVO goodsList, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, goodsList)){
            return form(goodsList, model);
        }
        if (StringUtils.isNotBlank(goodsList.getId())) {
            goodsList.setUpdateBy(UserUtils.getUser().getName());
        } else {
            goodsList.setUpdateBy(UserUtils.getUser().getName());
            goodsList.setCreateBy(UserUtils.getUser().getName());
        }
        goodsList.setPicDetail(goodsList.getPicDetail().replace("&quot;","\""));
        goodsList.setPicBanner(goodsList.getPicBanner().replace("&quot;","\""));
        shopService.saveOrUpdate(goodsList);
        JedisUtils.delObject(shopGoodsListKey);
        addMessage(redirectAttributes, "保存商品信息表成功");
        return "redirect:"+ Global.getAdminPath()+"/loan/goodsList/?repage";
    }

    /*@RequiresPermissions("loan:goodsList:edit")*/
    @RequestMapping(value = "delete")
    public String delete(GoodsListVO goodsList, RedirectAttributes redirectAttributes) {
        goodsList.setUpdateBy(UserUtils.getUser().getName());
        shopService.delete(goodsList);
        JedisUtils.delObject(shopGoodsListKey);
        addMessage(redirectAttributes, "删除商品信息表成功");
        return "redirect:"+Global.getAdminPath()+"/loan/goodsList/?repage";
    }


    @ResponseBody
    @RequestMapping(value = "updateStatus")
    public WebResult updateStatus(@RequestParam(value = "goodsId") String goodsId,@RequestParam(value = "status") String status) {
        try {
            GoodsListVO goods=new GoodsListVO();
            goods.setId(goodsId);
            if(StringUtils.isBlank(status)){
                return new WebResult("99", "状态值异常", null);
            }
            if(GoodsStatusEnum.ON_SHELVES.getValue().toString().equals(status)){
                goods.setStatus(GoodsStatusEnum.OFF_SHELVES.getValue().toString());
            }else if(GoodsStatusEnum.OFF_SHELVES.getValue().toString().equals(status)){
                goods.setStatus(GoodsStatusEnum.ON_SHELVES.getValue().toString());
            }
            goods.setUpdateBy(UserUtils.getUser().getName());
            shopService.saveOrUpdate(goods);
            JedisUtils.delObject(shopGoodsListKey);
            return new WebResult("1", "更改状态成功", null);
        } catch (Exception e) {
            logger.error("取消异常：goodsId = " + goodsId, e);
            return new WebResult("99", "更改状态异常");
        }
    }



}