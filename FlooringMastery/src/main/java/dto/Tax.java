package dto;
import java.math.BigDecimal;

public class Tax {

    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;

    public Tax(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getStateAbbreviation(){
        return stateAbbreviation;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public String toString(){
        return getStateAbbreviation() + "," + getStateName() + "," + getTaxRate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tax tax = (Tax) o;

        if (getStateAbbreviation() != null ? !getStateAbbreviation().equals(tax.getStateAbbreviation()) : tax.getStateAbbreviation() != null)
            return false;
        if (getStateName() != null ? !getStateName().equals(tax.getStateName()) : tax.getStateName() != null)
            return false;
        return getTaxRate() != null ? getTaxRate().equals(tax.getTaxRate()) : tax.getTaxRate() == null;
    }

    @Override
    public int hashCode() {
        int result = getStateAbbreviation() != null ? getStateAbbreviation().hashCode() : 0;
        result = 31 * result + (getStateName() != null ? getStateName().hashCode() : 0);
        result = 31 * result + (getTaxRate() != null ? getTaxRate().hashCode() : 0);
        return result;
    }
}
