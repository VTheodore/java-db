package alararestaurant.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemRootSeedDto {
    @XmlElement(name = "item")
    private List<ItemNameAndQuantityDto> items;

    public ItemRootSeedDto() {
    }

    public List<ItemNameAndQuantityDto> getItems() {
        return this.items;
    }

    public void setItems(List<ItemNameAndQuantityDto> items) {
        this.items = items;
    }
}
