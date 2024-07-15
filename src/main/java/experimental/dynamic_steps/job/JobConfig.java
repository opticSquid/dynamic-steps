package experimental.dynamic_steps.job;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import experimental.dynamic_steps.entity.CommonRawDataFormat;
import experimental.dynamic_steps.entity.SourceSystemCSV;
import experimental.dynamic_steps.processor.RawDataProcessor;

@Configuration
public class JobConfig {
    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;

    public JobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job vendorProcessingJob(Step step1, Step step2) throws Exception {
        return new JobBuilder("batch job", jobRepository)
                .flow(step1)
                .next(step2)
                .end()
                .build();
    }

    @Bean
    Step step1(FlatFileItemReader<SourceSystemCSV> fileReader, RawDataProcessor rawDataProcessor) {
        return new StepBuilder("collect data from prod and save in local", jobRepository)
                .<SourceSystemCSV, List<CommonRawDataFormat>>chunk(10, transactionManager)
                .reader(fileReader)
                .processor(rawDataProcessor)
                .build();
    }
}
