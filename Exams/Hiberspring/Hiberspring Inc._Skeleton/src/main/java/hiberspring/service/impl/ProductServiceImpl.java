package hiberspring.service.impl;

import hiberspring.domain.dto.product.ProductSeedRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.repository.ProductRepository;
import hiberspring.service.BranchService;
import hiberspring.service.ProductService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static hiberspring.common.GlobalConstants.*;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final BranchService branchService;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final XmlParser xmlParser;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, BranchService branchService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.productRepository = productRepository;
        this.branchService = branchService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean productsAreImported() {
        return this.productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return Files.readString(Path.of(PRODUCTS_FILE_PATH));
    }

    @Override
    public String importProducts() throws JAXBException, FileNotFoundException {
        ProductSeedRootDto productSeedRootDto = this.xmlParser.parseXml(ProductSeedRootDto.class, PRODUCTS_FILE_PATH);

        StringBuilder productsOutput = new StringBuilder();

        productSeedRootDto.getProducts()
                .forEach(productSeedDto -> {
                    if (this.validationUtil.isValid(productSeedDto)){
                        Branch branch = this.branchService.getBranchByName(productSeedDto.getBranchName());

                        if (branch == null) {
                            productsOutput
                                    .append(INCORRECT_DATA_MESSAGE)
                                    .append(System.lineSeparator());

                            return;
                        }

                        Product product = this.modelMapper.map(productSeedDto, Product.class);

                        product.setBranch(branch);
                        this.productRepository.save(product);

                        productsOutput.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, "Product", productSeedDto.getName()));
                    } else {
                        productsOutput.append(INCORRECT_DATA_MESSAGE);
                    }

                    productsOutput.append(System.lineSeparator());
                });
        return productsOutput.toString();
    }
}
