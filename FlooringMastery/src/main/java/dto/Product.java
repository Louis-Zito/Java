package dto;
import java.math.BigDecimal;

public class Product {

    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;

    public Product(String productType){
        this.productType = productType;
    }

    public String getProductType(){
        return productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    @Override
    public String toString(){
        return getProductType() + "," + getCostPerSquareFoot() + "," + getLaborCostPerSquareFoot();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (getProductType() != null ? !getProductType().equals(product.getProductType()) : product.getProductType() != null)
            return false;
        if (getCostPerSquareFoot() != null ? !getCostPerSquareFoot().equals(product.getCostPerSquareFoot()) : product.getCostPerSquareFoot() != null)
            return false;
        return getLaborCostPerSquareFoot() != null ? getLaborCostPerSquareFoot().equals(product.getLaborCostPerSquareFoot()) : product.getLaborCostPerSquareFoot() == null;
    }

    @Override
    public int hashCode() {
        int result = getProductType() != null ? getProductType().hashCode() : 0;
        result = 31 * result + (getCostPerSquareFoot() != null ? getCostPerSquareFoot().hashCode() : 0);
        result = 31 * result + (getLaborCostPerSquareFoot() != null ? getLaborCostPerSquareFoot().hashCode() : 0);
        return result;
    }
}
