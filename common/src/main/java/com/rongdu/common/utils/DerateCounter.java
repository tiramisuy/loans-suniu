package com.rongdu.common.utils;

import com.rongdu.common.config.Global;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 〈一句话功能简述〉<br>
 * 〈复贷服务费减免计数器-每天有LIMIT个名额递减，隔天重置为LIMIT〉
 *
 * @author yuanxianchu
 * @create 2018/12/4
 * @since 1.0.0
 */
public class DerateCounter {
    AtomicReferenceFieldUpdater<DerateCounter, AtomicInteger> valueUpdater =
            AtomicReferenceFieldUpdater.newUpdater(DerateCounter.class, AtomicInteger.class, "value");
    private volatile Date date;
    private volatile AtomicInteger value;
    //private volatile boolean flag;

    public static Integer LIMIT = 20;

    public static DerateCounter get() {
        return DerateCounterInstance.INSTANCE;
    }

    private static class DerateCounterInstance {
        private static DerateCounter INSTANCE = new DerateCounter();
    }

    private DerateCounter() {
        //this.flag = true;
        //this.date = new Date();
        //String countStr = JedisUtils.get(Global.JBD_15_DERATE_COUNT);
        //this.value = new AtomicInteger(StringUtils.isBlank(countStr) ? LIMIT : Integer.valueOf(countStr));
    }

    private int decrementAndGet() {
        AtomicInteger oldValue = value;
        AtomicInteger newValue = new AtomicInteger(LIMIT);

        int num = -1;
        Date now = new Date();
        //日期一致，计数器减1
        if (DateUtils.isSameDay(date, now)) {
            if (value.intValue() <= 0) {
                return num;
            }

            num = value.decrementAndGet();
            synchronized (this) {
                //修改后计数器存入redis
                /*if (!flag) return num;
                String countStr = JedisUtils.get(Global.JBD_15_DERATE_COUNT);
                if (!StringUtils.isBlank(countStr) && Integer.valueOf(countStr) < 0) flag = false;*/
                if (DateUtils.isSameDay(date, now))
                    JedisUtils.set(Global.JBD_15_DERATE_COUNT, value.toString(), 0);
            }
            return num;
        }

        //日期不一致，重置计数器
        valueUpdater.compareAndSet(this, oldValue, newValue);
        this.date = now;
        //重置后计数器减1
        num = value.decrementAndGet();
        synchronized (this) {
            //修改后计数器存入redis
            JedisUtils.set(Global.JBD_15_DERATE_COUNT, value.toString(), 0);
        }
        return num;
    }

    public int addByDay() {
        Long value = JedisUtils.addByKey(Global.JBD_15_DERATE_COUNT);
        return value == null ? LIMIT + 1 : value.intValue();
    }
}
