package ll.buildings.buildings.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tax implements Serializable {

    private static final long serialVersionUID = 6133293974352042289L;

    @ApiModelProperty(value = "Type of Building (primary Key)")
    @Id
    @Column(nullable = false)
    private String propertyType;

    @ApiModelProperty(value = "Tax Rate of this Building Type")
    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal taxRate;

    public String getPropertyType() {
        return propertyType;
    }

    public Tax setPropertyType(String propertyType) {
        this.propertyType = propertyType;
        return this;

    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public Tax setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public static Tax createNewTax(String propertyType, BigDecimal taxRate) {
        Tax tax = new Tax();
        tax.setTaxRate(taxRate)
            .setPropertyType(propertyType);
        return tax;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.propertyType);
        hash = 41 * hash + Objects.hashCode(this.taxRate);
        return hash;
    }

    @Override
    public boolean equals(Object obj
    ) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tax other = (Tax) obj;
        if (!Objects.equals(this.propertyType, other.propertyType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tax{" + "propertyType=" + propertyType + ", taxRate=" + taxRate + '}';
    }

}
