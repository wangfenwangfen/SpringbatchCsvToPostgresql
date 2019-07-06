package fr.fw.csvtodb.dao;


import fr.fw.csvtodb.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface StudentDao extends CrudRepository<Student,Long> {

    /*
    private void writCsvToDatabase(){
        Query query = entityManager.createNativeQuery("CCOPY student(firstname,lastname,email,age)\n" +
                "FROM 'D:/students.csv'\n" +
                "DELIMITERS ',' CSV HEADER;");
    }
    */

}
