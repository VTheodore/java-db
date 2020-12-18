package com.vezenkov.gamestore.services;

import com.vezenkov.gamestore.domain.entities.Game;
import com.vezenkov.gamestore.domain.entities.Order;
import com.vezenkov.gamestore.domain.entities.User;

import java.util.Set;

public interface OrderService {
    Order addOrder(User buyer, Set<Game> games);
}
