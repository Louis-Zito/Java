package dao;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class FlooringMasteryAudiDaoFileImpl implements FlooringMasteryAuditDao{

        public final String AUDIT_FILE;

        public FlooringMasteryAudiDaoFileImpl(){
            AUDIT_FILE = "Data/audit.txt";
        }

        public FlooringMasteryAudiDaoFileImpl(String auditTextFile){
            AUDIT_FILE = auditTextFile;
        }

        @Override
        public void writeAuditEntry(String entry) throws FlooringMasteryPersistenceException{
            PrintWriter out;

            try{
                out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
            } catch (IOException e){
                throw new FlooringMasteryPersistenceException("Could not persist audit information", e);
            }
            LocalDateTime timestamp = LocalDateTime.now();
            out.println(timestamp.toString() + " : " + entry);
            out.flush();
        }
    }
