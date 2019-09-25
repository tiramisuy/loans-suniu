package com.rongdu.loans.loan.service;

import java.util.List;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.cust.option.ShopOrderOP;
import com.rongdu.loans.loan.option.ApiResultVO;
import com.rongdu.loans.loan.option.GoodsListOp;
import com.rongdu.loans.loan.option.GoodsOrderOP;
import com.rongdu.loans.loan.vo.GoodsAddressVO;
import com.rongdu.loans.loan.vo.GoodsListVO;
import com.rongdu.loans.loan.vo.GoodsOrderVO;
import com.rongdu.loans.loan.vo.RepayLogVO;

/**
 * Created by lee on 2018/8/28.
 */
public interface ShopService {
    List<GoodsListVO> getGoodsList();

    ApiResultVO buyGoods(GoodsOrderOP goodsOrderOP);

    void paySuccess(RepayLogVO rePayOP);

    ApiResultVO payGoods(String id, String userId);
    /**
     *
    * @Title: findGoodsOrderByUserId
    * @Description: 查询客户订单
    * @param userId
    * @return    设定文件
    * @return List<GoodsOrderVO>    返回类型
    * @throws
     */
    List<GoodsOrderVO> findGoodsOrderByUserId(String userId);
    
    /**
    *
   * @Title: findGoodsOrder
   * @Description: 查询客户订单
   * @param userId
   * @return    设定文件
   * @return List<GoodsOrderVO>    返回类型
   * @throws
    */
   List<GoodsOrderVO> findGoodsOrder(GoodsOrderOP op);
    

    ApiResultVO cancelOrder(String id);

	Page<GoodsOrderVO> goodsOrderList(ShopOrderOP shopOrder);

	int updateGoodsOrderDeliver(String shopOrderID,String expressNo,String updateBy);

    ApiResultVO selectAdress(String userId);

    ApiResultVO updateAdress(GoodsAddressVO goodsAddressVO);

	List<GoodsOrderVO> goodsOrderExportList(ShopOrderOP shopOrder);

	Boolean cancelDeliver(String shopOrderID,String updateBy);

    public GoodsListVO get(String id);



    public Integer saveOrUpdate(GoodsListVO goodsListVO);


    Page<GoodsListVO> queryGoodsList(GoodsListOp op);

    public Integer delete(GoodsListVO goodsList);
}
