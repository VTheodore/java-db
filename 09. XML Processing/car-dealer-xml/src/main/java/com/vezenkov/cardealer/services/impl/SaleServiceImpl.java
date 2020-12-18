package com.vezenkov.cardealer.services.impl;

import com.vezenkov.cardealer.model.dtos.sales.export.SaleDto;
import com.vezenkov.cardealer.model.dtos.sales.export.SaleRootDto;
import com.vezenkov.cardealer.model.entitites.Sale;
import com.vezenkov.cardealer.repositories.SaleRepository;
import com.vezenkov.cardealer.services.CarService;
import com.vezenkov.cardealer.services.CustomerService;
import com.vezenkov.cardealer.services.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    private final CarService carService;

    private final CustomerService customerService;

    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarService carService, CustomerService customerService, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedSales() {
        Random random = new Random();

        int countOfSales = random.nextInt(420) + 1;

        for (int i = 0; i < countOfSales; i++) {
            this.saleRepository.save(this.getSale(random));
        }
    }

    @Override
    public SaleRootDto getSalesWithDiscounts() {
        SaleRootDto saleRootDto = new SaleRootDto();

        saleRootDto.setSales(this.saleRepository.findAll()
                .stream()
                .map(sale -> {
                    SaleDto saleDto = this.modelMapper.map(sale, SaleDto.class);

                    BigDecimal carPrice = this.carService.getPriceOfCarById(sale.getCar().getId());
                    saleDto.setPrice(carPrice);

                    BigDecimal discountPrice = carPrice.subtract(carPrice.multiply(sale.getDiscount()).divide(BigDecimal.valueOf(100), sale.getDiscount().scale() - BigDecimal.valueOf(100).scale(), RoundingMode.CEILING));
                    saleDto.setPriceWithDiscount(discountPrice);

                    return saleDto;
                }).collect(Collectors.toList()));

        return saleRootDto;
    }

    private Sale getSale(Random random) {
        Sale sale = new Sale();
        sale.setCar(this.carService.getRandomCar(random));
        sale.setCustomer(this.customerService.getRandomCustomer(random));
        sale.setDiscount(this.getRandomDiscount(random));
        return sale;
    }

    private BigDecimal getRandomDiscount(Random random) {
        return switch (random.nextInt(8)) {
            case 0 -> BigDecimal.valueOf(0);
            case 1 -> BigDecimal.valueOf(5);
            case 2 -> BigDecimal.valueOf(10);
            case 3 -> BigDecimal.valueOf(15);
            case 4 -> BigDecimal.valueOf(20);
            case 5 -> BigDecimal.valueOf(30);
            case 6 -> BigDecimal.valueOf(40);
            case 7 -> BigDecimal.valueOf(50);
            default -> throw new IllegalStateException("Unexpected value: " + random.nextInt(8));
        };
    }
}
