package com.vezenkov.cardealer.controllers;

import com.google.gson.Gson;
import com.vezenkov.cardealer.model.dtos.CarsAndPartsDto;
import com.vezenkov.cardealer.model.dtos.cars.CarSeedDto;
import com.vezenkov.cardealer.model.dtos.cars.CarToyotaDto;
import com.vezenkov.cardealer.model.dtos.customers.CustomerCustomDto;
import com.vezenkov.cardealer.model.dtos.customers.CustomerNameDto;
import com.vezenkov.cardealer.model.dtos.customers.CustomerSeedDto;
import com.vezenkov.cardealer.model.dtos.parts.PartSeedDto;
import com.vezenkov.cardealer.model.dtos.sales.SaleDto;
import com.vezenkov.cardealer.model.dtos.supplier.SupplierNameAndCountDto;
import com.vezenkov.cardealer.model.dtos.supplier.SupplierSeedDto;
import com.vezenkov.cardealer.services.*;
import com.vezenkov.cardealer.util.FileIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static com.vezenkov.cardealer.util.Constants.*;

@Component
public class AppController implements CommandLineRunner {
    private final SupplierService supplierService;

    private final PartService partService;

    private final CarService carService;

    private final CustomerService customerService;

    private final SaleService saleService;

    private final Gson gson;

    private final FileIOUtil fileIOUtil;

    @Autowired
    public AppController(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, Gson gson, FileIOUtil fileIOUtil) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.gson = gson;
        this.fileIOUtil = fileIOUtil;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedSuppliers();
//        this.seedParts();
//        this.seedCars();
//        this.seedCustomers();
//        this.saleService.seedSales();

//        this.exportOrderedCustomers();
//        this.exportCarsFromMakeToyota();
//        this.exportLocalSuppliers();
//        this.exportCarsAndParts();
//        this.exportCustomersTotalSales();
        this.exportSalesWithAppliedDiscount();
        System.out.println("Success");
    }

    private void exportSalesWithAppliedDiscount() {
        List<SaleDto> salesWithAppliedDiscount = this.saleService.getSalesWithAppliedDiscount();
        this.fileIOUtil.writeToFile(OUTPUT_FILE_FOLDER, SALES_DISCOUNTS, this.gson.toJson(salesWithAppliedDiscount));
    }

    private void exportCustomersTotalSales() {
        List<CustomerCustomDto> customersWithTotalSales = this.customerService.getCustomersWithTotalSales();
        this.fileIOUtil.writeToFile(OUTPUT_FILE_FOLDER, TOTAL_SALES_BY_CUSTOMERS, this.gson.toJson(customersWithTotalSales));
    }

    private void exportCarsAndParts() {
        List<CarsAndPartsDto> carsWithParts = this.carService.getAllCarsWithParts();
        this.fileIOUtil.writeToFile(OUTPUT_FILE_FOLDER, CARS_AND_PARTS, this.gson.toJson(carsWithParts));
    }

    private void exportLocalSuppliers() {
        List<SupplierNameAndCountDto> suppliersThatDontImport = this.supplierService.getAllLocalSuppliers();
        this.fileIOUtil.writeToFile(OUTPUT_FILE_FOLDER, LOCAL_SUPPLIERS, this.gson.toJson(suppliersThatDontImport));
    }

    private void exportCarsFromMakeToyota() {
        List<CarToyotaDto> carsFromMakeToyota = this.carService.getCarsFromMakeToyota();
        this.fileIOUtil.writeToFile(OUTPUT_FILE_FOLDER, CARS_FROM_MAKE_TOYOTA, this.gson.toJson(carsFromMakeToyota));
    }

    private void exportOrderedCustomers() {
        List<CustomerNameDto> orderedCustomers = this.customerService.getOrderedCustomers();
        this.fileIOUtil.writeToFile(OUTPUT_FILE_FOLDER, ORDERED_CUSTOMERS, this.gson.toJson(orderedCustomers));
    }

    private void seedCustomers() throws FileNotFoundException {
        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(new FileReader(CUSTOMERS_FILE_PATH), CustomerSeedDto[].class);
        this.customerService.seedCustomers(customerSeedDtos);
    }

    private void seedCars() throws FileNotFoundException {
        CarSeedDto[] carSeedDtos = this.gson.fromJson(new FileReader(CARS_FILE_PATH), CarSeedDto[].class);
        this.carService.seedCars(carSeedDtos);
    }

    private void seedParts() throws FileNotFoundException {
        PartSeedDto[] partSeedDtos = this.gson.fromJson(new FileReader(PARTS_FILE_PATH), PartSeedDto[].class);
        this.partService.seedParts(partSeedDtos);
    }

    private void seedSuppliers() throws FileNotFoundException {
        SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(new FileReader(SUPPLIERS_FILE_PATH), SupplierSeedDto[].class);
        this.supplierService.seedSuppliers(supplierSeedDtos);
    }
}
