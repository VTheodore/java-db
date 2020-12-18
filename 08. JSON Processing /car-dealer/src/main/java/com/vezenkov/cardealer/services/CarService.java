package com.vezenkov.cardealer.services;

import com.vezenkov.cardealer.model.dtos.CarsAndPartsDto;
import com.vezenkov.cardealer.model.dtos.cars.CarSeedDto;
import com.vezenkov.cardealer.model.dtos.cars.CarToyotaDto;
import com.vezenkov.cardealer.model.entitites.Car;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {
    void seedCars(CarSeedDto[] carSeedDtos);

    Car getRandomCar();

    List<CarToyotaDto> getCarsFromMakeToyota();

    List<CarsAndPartsDto> getAllCarsWithParts();

    BigDecimal getPriceOfCar(Long carId);
}
