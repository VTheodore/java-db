package com.vezenkov.cardealer.controllers;

import com.vezenkov.cardealer.model.dtos.cars.export.CarMakeRootDto;
import com.vezenkov.cardealer.model.dtos.cars.export.CarPartsRootDto;
import com.vezenkov.cardealer.model.dtos.cars.seed.CarSeedRootDto;
import com.vezenkov.cardealer.model.dtos.customers.export.CustomerFullNameRootDto;
import com.vezenkov.cardealer.model.dtos.customers.export.CustomerOrderRootDto;
import com.vezenkov.cardealer.model.dtos.customers.seed.CustomerSeedRootDto;
import com.vezenkov.cardealer.model.dtos.parts.seed.PartSeedRootDto;
import com.vezenkov.cardealer.model.dtos.sales.export.SaleRootDto;
import com.vezenkov.cardealer.model.dtos.suppliers.export.SupplierIdAndNameRootDto;
import com.vezenkov.cardealer.model.dtos.suppliers.seed.SupplierSeedRootDto;
import com.vezenkov.cardealer.services.*;
import com.vezenkov.cardealer.util.XMLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

import static com.vezenkov.cardealer.util.Constants.*;

@Component
public class AppInitializer implements CommandLineRunner {
    private final SupplierService supplierService;

    private final PartService partService;

    private final CarService carService;

    private final CustomerService customerService;

    private final SaleService saleService;

    private final XMLParser xmlParser;


    @Autowired
    public AppInitializer(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, XMLParser xmlParser) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedSuppliers();
//        this.seedParts();
//        this.seedCars();
//        this.seedCustomers();
//        this.seedSales();

//        this.exportOrderedCustomers();
//        this.exportCarsFromMakeToyota();
//        this.exportLocalSuppliers();
//        this.exportCarsAndParts();
//        this.exportCustomersAndBoughtCars();
//        this.exportSalesWithDiscounts();
    }

    private void exportSalesWithDiscounts() throws JAXBException {
        SaleRootDto saleRootDto = this.saleService.getSalesWithDiscounts();
        File file = this.createFile(SALES_DISCOUNTS);
        this.xmlParser.exportToXML(saleRootDto, file);
    }

    private void exportCustomersAndBoughtCars() throws JAXBException {
        CustomerFullNameRootDto customerFullNameRootDto = this.customerService.getCustomersAndBoughtCars();
        File file = this.createFile(TOTAL_SALES_BY_CUSTOMERS);
        this.xmlParser.exportToXML(customerFullNameRootDto, file);
    }

    private void exportCarsAndParts() throws JAXBException {
        CarPartsRootDto carPartsRootDto = this.carService.getCarsAndParts();
        File file = this.createFile(CARS_AND_PARTS);
        this.xmlParser.exportToXML(carPartsRootDto, file);
    }

    private void exportLocalSuppliers() throws JAXBException {
        SupplierIdAndNameRootDto localSuppliers = this.supplierService.getLocalSuppliers();
        File file = this.createFile(LOCAL_SUPPLIERS);
        this.xmlParser.exportToXML(localSuppliers, file);
    }

    private void exportCarsFromMakeToyota() throws JAXBException {
        CarMakeRootDto carsMakeRootDto = this.carService.getCarsFromMake("Toyota");
        File file = this.createFile(CARS_FROM_MAKE_TOYOTA);
        this.xmlParser.exportToXML(carsMakeRootDto, file);
    }

    private void exportOrderedCustomers() throws JAXBException {
        CustomerOrderRootDto orderedCustomers = this.customerService.getOrderedCustomers();
        File file = this.createFile(ORDERED_CUSTOMERS);
        this.xmlParser.exportToXML(orderedCustomers, file);
    }

    private void seedSales() {
        this.saleService.seedSales();
    }

    private void seedCustomers() {
        CustomerSeedRootDto customerSeedRootDto = this.xmlParser.importFromXML(CustomerSeedRootDto.class, CUSTOMERS_FILE_PATH);
        this.customerService.seedCustomers(customerSeedRootDto);
    }

    private void seedCars() {
        CarSeedRootDto carSeedRootDto = this.xmlParser.importFromXML(CarSeedRootDto.class, CARS_FILE_PATH);
        this.carService.seedCars(carSeedRootDto);
    }

    private void seedParts() {
        PartSeedRootDto partSeedRootDto = this.xmlParser.importFromXML(PartSeedRootDto.class, PARTS_FILE_PATH);
        this.partService.seedPart(partSeedRootDto);
    }

    private void seedSuppliers() {
        SupplierSeedRootDto supplierSeedRootDto = this.xmlParser.importFromXML(SupplierSeedRootDto.class, SUPPLIERS_FILE_PATH);
        this.supplierService.seedSuppliers(supplierSeedRootDto);
    }

    private File createFile(String fileName) {
        try {
            File file = new File(OUTPUT_FILE_FOLDER + fileName);

            if (file.createNewFile()) {
                System.out.printf("Created file %s in %s%n", fileName, OUTPUT_FILE_FOLDER);
            }

            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
