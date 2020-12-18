package com.vezenkov.cardealer.services.impl;

import com.vezenkov.cardealer.model.dtos.CarsAndPartsDto;
import com.vezenkov.cardealer.model.dtos.cars.CarBasicDto;
import com.vezenkov.cardealer.model.dtos.cars.CarSeedDto;
import com.vezenkov.cardealer.model.dtos.cars.CarToyotaDto;
import com.vezenkov.cardealer.model.dtos.parts.PartNameAndPriceDto;
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
import java.util.Arrays;
import java.util.List;
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
    public void seedCars(CarSeedDto[] carSeedDtos) {
        Arrays.stream(carSeedDtos)
                .forEach(carSeedDto -> {
                    if (this.validationUtil.isValid(carSeedDto)) {
                        Car car = this.modelMapper.map(carSeedDto, Car.class);
                        car.setParts(this.partService.getRandomParts());
                        this.carRepository.saveAndFlush(car);
                    } else {
                        this.validationUtil
                                .getViolations(carSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Car getRandomCar() {
        Random random = new Random();
        long carId = random.nextInt((int) this.carRepository.count()) + 1;
        return this.carRepository.findCarById(carId);
    }

    @Override
    public List<CarToyotaDto> getCarsFromMakeToyota() {
        return this.carRepository
                .findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream()
                .map(car -> this.modelMapper.map(car, CarToyotaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarsAndPartsDto> getAllCarsWithParts() {
        return this.carRepository
                .findAll()
                .stream()
                .map(car -> {
                    CarsAndPartsDto carsAndPartsDto = new CarsAndPartsDto();
                    carsAndPartsDto.setCar(this.modelMapper.map(car, CarBasicDto.class));
                    carsAndPartsDto.setParts(car
                            .getParts()
                            .stream()
                            .map(part -> this.modelMapper.map(part, PartNameAndPriceDto.class))
                            .collect(Collectors.toList()));
                    return carsAndPartsDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getPriceOfCar(Long carId) {
        return this.carRepository.selectPriceOfCarById(carId);
    }
}
