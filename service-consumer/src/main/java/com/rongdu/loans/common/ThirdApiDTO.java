package com.rongdu.loans.common;

import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class ThirdApiDTO extends ApiBaseDTO {

    private String channelCode;


    public ThirdApiDTO(String url, String data, String serviceName){
        this.url = url;
        this.data = data;
        this.serviceName = serviceName;
    }
}
