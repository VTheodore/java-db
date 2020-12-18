package com.vezenkov.cardealer.services;

import com.vezenkov.cardealer.model.dtos.cars.export.CarMakeRootDto;
import com.vezenkov.cardealer.model.dtos.cars.export.CarPartsRootDto;
import com.vezenkov.cardealer.model.dtos.cars.seed.CarSeedRootDto;
import com.vezenkov.cardealer.model.entitites.Car;

import java.math.BigDecimal;
import java.util.Random;

public interface CarService {
    void seedCars(CarSeedRootDto carSeedRootDto);

    Car getRandomCar(Random random);

    CarMakeRootDto getCarsFromMake(String make);

    CarPartsRootDto getCarsAndParts();

    BigDecimal getPriceOfCarById(Long id);
}