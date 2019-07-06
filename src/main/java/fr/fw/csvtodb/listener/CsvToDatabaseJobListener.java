package fr.fw.csvtodb.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class CsvToDatabaseJobListener implements JobExecutionListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private long startTime;
    private long endTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        logger.info("CsvToDatabaseJob start : ");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = System.currentTimeMillis();
        logger.info("CsvToDatabaseJob end，total execute time =" + (endTime - startTime) + "ms");
    }
}
