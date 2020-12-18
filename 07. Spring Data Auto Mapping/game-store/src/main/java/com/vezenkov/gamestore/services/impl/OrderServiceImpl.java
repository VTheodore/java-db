package com.vezenkov.gamestore.services.impl;

import com.vezenkov.gamestore.domain.entities.Game;
import com.vezenkov.gamestore.domain.entities.Order;
import com.vezenkov.gamestore.domain.entities.User;
import com.vezenkov.gamestore.repositories.OrderRepository;
import com.vezenkov.gamestore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order addOrder(User buyer, Set<Game> games) {
        return this.orderRepository.saveAndFlush(new Order(buyer, games));
    }
}
