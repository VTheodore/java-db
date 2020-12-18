package hiberspring.domain.dto.product;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedDto {
    @XmlAttribute(name = "name")
    @NotNull
    @Length(min = 3, max = 100)
    private String name;

    @XmlAttribute(name = "clients")
    @NotNull
    @PositiveOrZero
    private int clients;

    @XmlElement(name = "branch")
    @NotNull
    private String branchName;

    public ProductSeedDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClients() {
        return this.clients;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
