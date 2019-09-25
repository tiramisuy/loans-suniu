package com.rongdu.loans.anrong.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 安融-迭代获取待共享接口-响应结果
 * @author fy
 * @version 2019-06-17
 */
@Data
public class IterationShareVO implements Serializable {


    private String maxItemId;

    private List<ShareVO> todoList;
}