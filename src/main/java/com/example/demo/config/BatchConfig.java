package com.example.demo.config;

import com.example.demo.entity.WebLog;
import com.example.demo.rw.WebLogItemReader;
import com.example.demo.rw.WebLogItemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final KafkaTemplate<String, WebLog> kafkaTemplate;

    @Bean
    public Job WebLogGenAndProduceJob() {
        return new JobBuilder("webLogGenAndProduceJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(WebLogGenAndProduceStep())
                .build();
    }

    @Bean
    public Step WebLogGenAndProduceStep() {
        return new StepBuilder("webLogGenAndProduceJob", jobRepository)
                .<WebLog, WebLog>chunk(10, platformTransactionManager)
                .reader(webLogItemReader())
                .writer(webLogItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public WebLogItemReader webLogItemReader() {
        return new WebLogItemReader();
    }

    @Bean
    public WebLogItemWriter webLogItemWriter() {
        return new WebLogItemWriter(kafkaTemplate);
    }
}
