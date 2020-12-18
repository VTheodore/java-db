package hiberspring.domain.dto.employee;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeSeedDto {
    @XmlAttribute(name = "first-name")
    @NotNull
    @Length(min = 2, max = 60)
    private String firstName;

    @XmlAttribute(name = "last-name")
    @NotNull
    @Length(min = 2, max = 60)
    private String lastName;

    @XmlAttribute(name = "position")
    @NotNull
    @Length(min = 2, max = 100)
    private String position;

    @XmlElement(name = "card")
    @NotNull
    private String card;

    @XmlElement(name = "branch")
    @NotNull
    private String branch;

    public EmployeeSeedDto() {
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCard() {
        return this.card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getBranch() {
        return this.branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
