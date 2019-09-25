package com.rongdu.loans.common;

import com.ancun.ops.client.OpsClient;
import com.rongdu.common.config.Global;

/**
 * OpsClient
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/4
 */
public class OpsClientUtil {

    private volatile static OpsClient opsClient;

    private OpsClientUtil() {
    }

    public static OpsClient getOpsClient() {
        if (opsClient == null) {
            // 线程安全
            synchronized (OpsClient.class) {
                if (opsClient == null) {
                    opsClient = new OpsClient(Global.getConfig("ancun.apiAddress"), Global.getConfig("ancun.accessKey"), Global.getConfig("ancun.secretKey"));
                }
            }
        }
        return opsClient;
    }
}


