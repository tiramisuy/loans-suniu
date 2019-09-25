package com.rongdu.loans.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/7/9
 * @since 1.0.0
 */
@Data
public abstract class ApiBaseDTO implements Serializable {

    private static final long serialVersionUID = -7646010663550039219L;
    protected String url;
    protected String data;
    protected String serviceName;
}
