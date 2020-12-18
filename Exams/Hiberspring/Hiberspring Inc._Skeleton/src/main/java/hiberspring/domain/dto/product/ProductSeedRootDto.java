package hiberspring.domain.dto.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedRootDto {
    @XmlElement(name = "product")
    List<ProductSeedDto> products;

    public ProductSeedRootDto() {
    }

    public List<ProductSeedDto> getProducts() {
        return this.products;
    }

    public void setProducts(List<ProductSeedDto> products) {
        this.products = products;
    }
}
