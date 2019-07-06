package fr.fw.csvtodb.step;

import fr.fw.csvtodb.dao.StudentDao;
import fr.fw.csvtodb.model.Student;
import org.springframework.batch.item.ItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StudentDatabaseWriter implements ItemWriter<Student> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDatabaseWriter.class);

    @Autowired
    StudentDao studentDao;
    @Override
    public void write(List<? extends Student> listStudents){
       // LOGGER.info("Received the information of {} students", listStudents.size());
        for(Student student:listStudents)
        {
          //  LOGGER.info(String.format("inserting for customre %s %s", student.getFirstName(), student.getLastName()));
           studentDao.save(student) ;
        }
    }
}
