package com.rongdu.loans.basic.service;


import com.rongdu.loans.loan.option.jdq.IntoOrder;

public interface ProductorService {
    public void sendIntoOrder(IntoOrder intoOrder, String type) ;
    public void sendOrderStatus(String orderId, String bizId, String type);
}
