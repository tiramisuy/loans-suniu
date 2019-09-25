package com.rongdu.loans.risk.option;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈风控白名单〉
 *
 * @author yuanxianchu
 * @create 2019/1/24
 * @since 1.0.0
 */
@Data
public class WhitelistOP implements Serializable {
    private static final long serialVersionUID = 4993804934166645909L;

    private Integer status;

    private String userId;

    private String name;

    private String mobile;

    private String id;

    private Integer pageNo = 1;

    private Integer pageSize = 10;
}
