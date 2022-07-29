package dto;
import java.math.BigDecimal;
import java.util.Locale;

public class Order {

    private int orderNumber;
    private String customerName;
    private String stateAbbreviation;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setOrderNumber(int orderNumber) {this.orderNumber = orderNumber;}

    public int getOrderNumber(){
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        customerName = customerName.toUpperCase();
        this.customerName = customerName;
    }

    public String getStateAbbreviation() {return stateAbbreviation;}

    public void setStateAbbreviation(String state) {
        state = state.toUpperCase();
        this.stateAbbreviation = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        productType = productType.toUpperCase();
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
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

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "       " + getOrderNumber() + "\t\t     " + getCustomerName() + "\t\t    " + getStateAbbreviation() + "\t    " + getTaxRate() + "\t\t"
                + getProductType() + "\t " + getArea() + "\t\t   " + getCostPerSquareFoot() + "\t\t\t     " + getLaborCostPerSquareFoot() + "\t\t\t\t " +
                getMaterialCost() + "\t    " + getLaborCost() + "\t  " + getTax() + "  " + getTotal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (getOrderNumber() != order.getOrderNumber()) return false;
        if (getCustomerName() != null ? !getCustomerName().equals(order.getCustomerName()) : order.getCustomerName() != null)
            return false;
        if (stateAbbreviation != null ? !stateAbbreviation.equals(order.stateAbbreviation) : order.stateAbbreviation != null)
            return false;
        if (getTaxRate() != null ? !getTaxRate().equals(order.getTaxRate()) : order.getTaxRate() != null) return false;
        if (getProductType() != null ? !getProductType().equals(order.getProductType()) : order.getProductType() != null)
            return false;
        if (getArea() != null ? !getArea().equals(order.getArea()) : order.getArea() != null) return false;
        if (getCostPerSquareFoot() != null ? !getCostPerSquareFoot().equals(order.getCostPerSquareFoot()) : order.getCostPerSquareFoot() != null)
            return false;
        if (getLaborCostPerSquareFoot() != null ? !getLaborCostPerSquareFoot().equals(order.getLaborCostPerSquareFoot()) : order.getLaborCostPerSquareFoot() != null)
            return false;
        if (getMaterialCost() != null ? !getMaterialCost().equals(order.getMaterialCost()) : order.getMaterialCost() != null)
            return false;
        if (getLaborCost() != null ? !getLaborCost().equals(order.getLaborCost()) : order.getLaborCost() != null)
            return false;
        if (getTax() != null ? !getTax().equals(order.getTax()) : order.getTax() != null) return false;
        return getTotal() != null ? getTotal().equals(order.getTotal()) : order.getTotal() == null;
    }

    @Override
    public int hashCode() {
        int result = getOrderNumber();
        result = 31 * result + (getCustomerName() != null ? getCustomerName().hashCode() : 0);
        result = 31 * result + (stateAbbreviation != null ? stateAbbreviation.hashCode() : 0);
        result = 31 * result + (getTaxRate() != null ? getTaxRate().hashCode() : 0);
        result = 31 * result + (getProductType() != null ? getProductType().hashCode() : 0);
        result = 31 * result + (getArea() != null ? getArea().hashCode() : 0);
        result = 31 * result + (getCostPerSquareFoot() != null ? getCostPerSquareFoot().hashCode() : 0);
        result = 31 * result + (getLaborCostPerSquareFoot() != null ? getLaborCostPerSquareFoot().hashCode() : 0);
        result = 31 * result + (getMaterialCost() != null ? getMaterialCost().hashCode() : 0);
        result = 31 * result + (getLaborCost() != null ? getLaborCost().hashCode() : 0);
        result = 31 * result + (getTax() != null ? getTax().hashCode() : 0);
        result = 31 * result + (getTotal() != null ? getTotal().hashCode() : 0);
        return result;
    }
}//end class
