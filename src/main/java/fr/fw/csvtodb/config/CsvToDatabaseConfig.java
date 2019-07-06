package fr.fw.csvtodb.config;

import fr.fw.csvtodb.listener.CsvToDatabaseJobListener;
import fr.fw.csvtodb.model.Student;
import fr.fw.csvtodb.step.StudentDatabaseWriter;
import fr.fw.csvtodb.step.StudentFormatProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


@Configuration
@EnableBatchProcessing
public class CsvToDatabaseConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    private static final Resource INPUT_RESOURCE = new FileSystemResource("input/students.csv");

    @Bean
    public ResourcelessTransactionManager resourcelessTransactionManager() {
        return new ResourcelessTransactionManager();
    }
    //----- Reader ------
    @Bean
    ItemReader<Student> csvStudentItemReader() {
        //Create reader instance
        FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
        //Declare input resource
        reader.setResource(INPUT_RESOURCE);
        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(1);
        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper<Student>() {
            {
                //columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("firstName","lastName", "email", "age");
                    }
                });
                //Set values in objet class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {{
                    setTargetType(Student.class);
                }
                });
            }
        });
        return reader;
    }

    // ------ Processor ------
    @Bean
    ItemProcessor<Student, Student>csvStudentFormatProcessor() {

        return new StudentFormatProcessor();
    }

    // ------ Writer ------
    @Bean
    public ItemWriter<Student> writer() {
        return new StudentDatabaseWriter();
    }
    // *************** end reader, writer, and processor ******************

    // ----------  listener ------
    @Bean
    JobExecutionListener listener() {
        return new CsvToDatabaseJobListener();
    }


    // ------  begin job info ------
    @Bean
    Step csvToDatabaseStep() {
        return stepBuilderFactory
                .get("csvToDatabaseStep")
                .<Student, Student>chunk(5)
                .reader(csvStudentItemReader())
                .processor(csvStudentFormatProcessor())
                .writer(writer())
                .build();
    }


    @Bean
    Job csvFileToDatabaseJob() {
        return jobBuilderFactory
                .get("csvFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .start(csvToDatabaseStep())
                .listener(listener())
                .build();
    }
    //  ------ end job info ------
}
