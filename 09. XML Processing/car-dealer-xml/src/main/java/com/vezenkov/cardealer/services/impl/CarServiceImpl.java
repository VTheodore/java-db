package com.vezenkov.cardealer.services.impl;

import com.vezenkov.cardealer.model.dtos.cars.export.CarMakeDto;
import com.vezenkov.cardealer.model.dtos.cars.export.CarMakeRootDto;
import com.vezenkov.cardealer.model.dtos.cars.export.CarPartsDto;
import com.vezenkov.cardealer.model.dtos.cars.export.CarPartsRootDto;
import com.vezenkov.cardealer.model.dtos.cars.seed.CarSeedRootDto;
import com.vezenkov.cardealer.model.entitites.Car;
import com.vezenkov.cardealer.repositories.CarRepository;
import com.vezenkov.cardealer.services.CarService;
import com.vezenkov.cardealer.services.PartService;
import com.vezenkov.cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final PartService partService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, PartService partService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partService = partService;
    }

    @Override
    public void seedCars(CarSeedRootDto carSeedRootDto) {
        carSeedRootDto.getCars()
                .forEach(carDto -> {
                    if (this.validationUtil.isValid(carDto)) {
                        Car car = this.modelMapper.map(carDto, Car.class);
                        car.setParts(this.partService.getRandomParts());
                        this.carRepository.save(car);
                    } else {
                        this.validationUtil
                                .getViolations(carDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Car getRandomCar(Random random) {
        long carId = random.nextInt((int) this.carRepository.count()) + 1;
        return this.carRepository.findCarById(carId);
    }

    @Override
    public CarMakeRootDto getCarsFromMake(String make) {
        CarMakeRootDto carMakeRootDto = new CarMakeRootDto();

        carMakeRootDto.setCars(
                this.carRepository
                .findAllByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream()
                .map(car -> this.modelMapper.map(car, CarMakeDto.class))
                .collect(Collectors.toList()));
        return carMakeRootDto;
    }

    @Override
    public CarPartsRootDto getCarsAndParts() {
        CarPartsRootDto carPartsRootDto = new CarPartsRootDto();

        carPartsRootDto.setCars(this.carRepository.findAll()
                .stream()
                .map(car -> this.modelMapper.map(car, CarPartsDto.class))
                .collect(Collectors.toList()));
        return carPartsRootDto;
    }

    @Override
    public BigDecimal getPriceOfCarById(Long id) {
        return this.carRepository.getPriceOfCarById(id);
    }
}
