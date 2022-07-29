package dto;

import java.math.BigDecimal;

public class InventoryItem {

    private String itemName;
    private BigDecimal itemCost;
    private int numberInInventory;

    public InventoryItem(String itemName){
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public int getNumberInInventory() {
        return numberInInventory;
    }

    public void setNumberInInventory(int numberInInventory) {
        this.numberInInventory = numberInInventory;
    }

    //all objects need an Overridden toString to avoid RAM address displays
    @Override
    public String toString(){
        return "Item \t\t" + getItemName() + "\n" +
                "Cost \t\t" + getItemCost() + "\n" +
                "Inventory \t\t" + getNumberInInventory() + "\n";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryItem that = (InventoryItem) o;

        if (getNumberInInventory() != that.getNumberInInventory()) return false;
        if (getItemName() != null ? !getItemName().equals(that.getItemName()) : that.getItemName() != null)
            return false;
        return getItemCost() != null ? getItemCost().equals(that.getItemCost()) : that.getItemCost() == null;
    }

    @Override
    public int hashCode() {
        int result = getItemName() != null ? getItemName().hashCode() : 0;
        result = 31 * result + (getItemCost() != null ? getItemCost().hashCode() : 0);
        result = 31 * result + getNumberInInventory();
        return result;
    }
}
