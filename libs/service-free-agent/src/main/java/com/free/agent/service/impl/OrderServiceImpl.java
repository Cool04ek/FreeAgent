package com.free.agent.service.impl;


import com.free.agent.dao.OrderDao;
import com.free.agent.model.Order;
import com.free.agent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    public Order createOrder(Order order) {
        return orderDao.create(order);
    }

    @Transactional
    public Order updateOrder(Order order) {
        return orderDao.update(order);
    }
}