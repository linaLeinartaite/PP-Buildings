package ll.buildings.buildings.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Building implements Serializable {

    private static final long serialVersionUID = -5413856132398535960L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Id Number of Building (Primary Key)")
    private Integer id;

    @ApiModelProperty(value = "Address of Building")
    @Column(nullable = false)
    private String address;

    @ApiModelProperty(value = "Owner of Building")
    @Column(nullable = false)
    private String owner;

    @ApiModelProperty(value = "Size of Building in Square Meters")
    @Column(nullable = false)
    private double size;

    @ApiModelProperty(value = "Market Value of Building in Euros")
    @Column(nullable = false)
    private BigDecimal marketValue;

    @ApiModelProperty(value = "Type of Building")
    @Column(nullable = false)
    private String propertyType;

    public Integer getId() {
        return id;
    }

    public Building setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Building setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public Building setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public double getSize() {
        return size;
    }

    public Building setSize(double size) {
        this.size = size;
        return this;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public Building setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
        return this;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public Building setPropertyType(String propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public static Building createNewBuilding(Integer id, String address, String owner, double size, BigDecimal price, String type) {
        Building building = new Building();
        building.setId(id)
                .setAddress(address)
                .setOwner(owner)
                .setSize(size)
                .setMarketValue(price)
                .setPropertyType(type);

        return building;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Building other = (Building) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Building{" + "id=" + id + ", address=" + address + ", owner=" + owner + ", size=" + size + ", marketValue=" + marketValue + ", propertyType=" + propertyType + '}';
    }

}
