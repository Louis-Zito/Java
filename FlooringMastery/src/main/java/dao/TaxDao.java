package dao;
import java.util.List;
import dto.Tax;


public interface TaxDao {

    Tax createTaxObject(String stateAbbreviation, Tax taxObject) throws TaxDataPersistenceException;

    List<Tax> readAllTaxes() throws TaxDataPersistenceException;

    Tax readByStateAbbreviation(String stateAbbreviation) throws TaxDataPersistenceException;

    Tax updateTaxData(Tax taxData) throws TaxDataPersistenceException;

    Tax deleteTaxData(String stateAbbreviation) throws TaxDataPersistenceException;

}
