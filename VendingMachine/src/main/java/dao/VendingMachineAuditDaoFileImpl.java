package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class VendingMachineAuditDaoFileImpl implements VendingMachineAuditDao{

    //for JUnit tests make second constructor so as not to overwrite real data
    public final String AUDIT_FILE;

    public VendingMachineAuditDaoFileImpl(){
        AUDIT_FILE = "audit.txt";
    }

    //second constructor used in JUnit test calls
    //argument for new text file avoids overwriting actual inventory txt file
    public VendingMachineAuditDaoFileImpl(String auditTextFile){
        AUDIT_FILE = auditTextFile;
    }


    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        PrintWriter out;

        try{
            //argument True for append mode vs overwriting previous data
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e){
            throw new VendingMachinePersistenceException("Could not persist audit information.", e);
        }
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }
}

