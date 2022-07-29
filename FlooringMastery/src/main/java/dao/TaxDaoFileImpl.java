package dao;
import dto.Tax;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class TaxDaoFileImpl implements TaxDao{

    public String taxData;
    public static final String DElIMITER = ",";

    public TaxDaoFileImpl(){
        this.taxData = "Data/Taxes.txt";
    }

    // second constructor used in JUnit test calls, arg avoids overwriting actual Taxes.txt file
    public TaxDaoFileImpl(String taxStorageFileName){
        this.taxData = taxStorageFileName;
    }

    private Map<String, Tax> currentTaxData = new HashMap<>();

    //put: if existing key: return previous value, if new key, no previous so: returns null
    @Override
    public Tax createTaxObject(String stateAbbreviation, Tax taxData) throws TaxDataPersistenceException {
        loadTaxFile();
        Tax newStateTax = currentTaxData.put(stateAbbreviation, taxData);
        writeTaxFile();
        return newStateTax;
    }

    @Override
    public List<Tax> readAllTaxes() throws TaxDataPersistenceException {
        loadTaxFile();
        return new ArrayList<>(currentTaxData.values());
    }

    @Override
    public Tax readByStateAbbreviation(String stateAbbreviation) throws TaxDataPersistenceException {
        loadTaxFile();
        Tax stateTaxData = currentTaxData.get(stateAbbreviation);
        return stateTaxData;
    }

    @Override
    public Tax updateTaxData(Tax taxData) throws TaxDataPersistenceException {
        loadTaxFile();
        Tax updatedTaxData = new Tax(taxData.getStateAbbreviation());
        updatedTaxData.setStateName(taxData.getStateName());
        updatedTaxData.setTaxRate(taxData.getTaxRate());
        currentTaxData.put(updatedTaxData.getStateAbbreviation(), updatedTaxData);
        writeTaxFile();
        return updatedTaxData;
    }

    @Override
    public Tax deleteTaxData(String stateAbbreviation) throws TaxDataPersistenceException {
        loadTaxFile();
        Tax removedStateTaxData = currentTaxData.remove(stateAbbreviation);
        writeTaxFile();
        return removedStateTaxData;
    }

    // build object from line of text in comma-separated file
    private Tax unmarshallTaxData(String taxItemAsText){
        String[] taxTokens = taxItemAsText.split(DElIMITER);
        String taxStateAbbreviation = taxTokens[0];
        Tax taxObjectFromFile = new Tax(taxStateAbbreviation);
        taxObjectFromFile.setStateName(taxTokens[1]);
        BigDecimal taxRate = new BigDecimal(taxTokens[2]);
        taxObjectFromFile.setTaxRate(taxRate);
        return taxObjectFromFile;
    }

    private void loadTaxFile() throws TaxDataPersistenceException{
        Scanner scanner;
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(taxData)));
        } catch (FileNotFoundException e){
            throw new TaxDataPersistenceException("Could not load Tax data into memory!", e);
        }
        String currentLine;
        Tax taxItem;
        while (scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            taxItem = unmarshallTaxData(currentLine);
            currentTaxData.put(taxItem.getStateAbbreviation(), taxItem);
        }
    }

    String marshallTaxData(Tax stateTaxData) throws TaxDataPersistenceException{
        String taxDataAsText = stateTaxData.getStateAbbreviation() + DElIMITER;
        taxDataAsText += stateTaxData.getStateName() + DElIMITER;
        taxDataAsText += stateTaxData.getTaxRate();
        return taxDataAsText;
    }

    public void writeTaxFile() throws TaxDataPersistenceException{
        PrintWriter out;
        try{
            out = new PrintWriter(new FileWriter(taxData));
        } catch(IOException e){
            throw new TaxDataPersistenceException("Could not save Tax data!");
        }
        String taxDataAsText;
        List<Tax> taxDataList = this.readAllTaxes();
        for (Tax stateTaxData : taxDataList){
            taxDataAsText = marshallTaxData(stateTaxData);
            out.println(taxDataAsText);
            out.flush();
        }
        out.close();
    }
}
