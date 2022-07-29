package dto;
import java.math.BigDecimal;
import java.math.RoundingMode;
//import java.time.LocalDate;
//
//enum used for coin values
//enum Coins{
//    PENNY(1), NICKLE(5), DIME(10), QUARTER(25), DOLLAR(100);
//
//    private int value;
//
//    Coins(int value) {
//        this.value = value;
//    }
//
//    public int getCoinValue() {
//        return this.value;
//    }
//}

//Change takes in a BigDecimal value of change du
//uses method to convert to pennies
//returns array of coin-denominations from pennies
public class Change {

    BigDecimal changeDue;
    BigDecimal dollars;
    BigDecimal quarters;
    BigDecimal dimes;
    BigDecimal nickles;
    BigDecimal pennies;

    public Change(BigDecimal changeDue){
        this.changeDue = changeDue;
        makeChangeDenominations(this.changeDue);
    }

    //private methods called in constructor that set values upon making Change object holding values
    //BELOW!
    //a.compareTo(b): a>b = 1, a=b = 0, a<b = -1
    private void makeChangeDenominations(BigDecimal changeDue){
        changeDue.setScale(0,RoundingMode.DOWN);
        BigDecimal oneDollar = new BigDecimal("1.00");
        BigDecimal ninetyNineCents = new BigDecimal("0.99");
        BigDecimal twentyFiveCents = new BigDecimal("0.25");
        BigDecimal twentyFourCents = new BigDecimal("0.24");
        BigDecimal tenCents = new BigDecimal("0.10");
        BigDecimal nineCents = new BigDecimal("0.09");
        BigDecimal fiveCents = new BigDecimal("0.05");
        BigDecimal fourCents = new BigDecimal("0.04");
        BigDecimal oneCent = new BigDecimal("0.01");

        //may need RoundingMode.HALF_EVEN in division if values aren't accurate
        if (changeDue.compareTo(ninetyNineCents) > 0){
            dollars = changeDue.divide(oneDollar,RoundingMode.DOWN);
            this.dollars = dollars.setScale(0,RoundingMode.DOWN);
            changeDue = changeDue.remainder(oneDollar);
        }
        if ((changeDue.compareTo(oneDollar) == -1) && (changeDue.compareTo(twentyFourCents) == 1)){
            quarters = changeDue.divide(twentyFiveCents,RoundingMode.DOWN);
            this.quarters = quarters.setScale(0,RoundingMode.DOWN);
            changeDue = changeDue.remainder(twentyFiveCents);
        }
        if ((changeDue.compareTo(nineCents) == 1) && (changeDue.compareTo(twentyFiveCents) == -1)){
            dimes = changeDue.divide(tenCents,RoundingMode.DOWN);
            this.dimes = dimes.setScale(0,RoundingMode.DOWN);
            changeDue = changeDue.remainder(tenCents);
        }
        if ((changeDue.compareTo(fourCents) == 1) && (changeDue.compareTo(tenCents) == -1)){
            nickles = changeDue.divide(fiveCents,RoundingMode.DOWN);
            this.nickles = nickles.setScale(0,RoundingMode.DOWN);
            changeDue = changeDue.remainder(fiveCents);
        }
        if ((changeDue.compareTo(BigDecimal.ZERO) == 1) && (changeDue.compareTo(fiveCents) == -1)){
            this.pennies = changeDue.divide(oneCent);
        }
        return;
    }

    public BigDecimal getDollars() {
        return dollars;
    }

    public BigDecimal getQuarters() {
        return quarters;
    }

    public BigDecimal getDimes(){
        return dimes;
    }

    public BigDecimal getNickles() {
        return nickles;
    }

    public BigDecimal getPennies() {
        return pennies;
    }

    }//end change

