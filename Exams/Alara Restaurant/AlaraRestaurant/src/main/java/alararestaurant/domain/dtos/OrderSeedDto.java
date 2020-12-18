package alararestaurant.domain.dtos;

import alararestaurant.util.DateAdapter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSeedDto {
    @NotNull
    @XmlElement(name = "customer")
    private String customer;

    @NotNull
    @Length(min = 3, max = 30)
    @XmlElement(name = "employee")
    private String employee;

    @NotNull
    @PastOrPresent
    @XmlElement(name = "date-time")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date dateTime;

    @NotNull
    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "items")
    private ItemRootSeedDto items;

    public OrderSeedDto() {
    }

    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getEmployee() {
        return this.employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Date getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ItemRootSeedDto getItems() {
        return this.items;
    }

    public void setItems(ItemRootSeedDto items) {
        this.items = items;
    }
}
