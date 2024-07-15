package experimental.dynamic_steps.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import experimental.dynamic_steps.entity.SourceSystemCSV;

@Configuration
public class CsvReader {
    @Bean
    FlatFileItemReader<SourceSystemCSV> fileReader(@Value("${dynamic_steps.input-file}") String inputFile) {
        return new FlatFileItemReaderBuilder<SourceSystemCSV>()
        .name("reading csv file")
        .resource(new FileSystemResource(inputFile))
        .delimited()
        .names("name","region","query","appDb","filenet")
        .targetType(SourceSystemCSV.class)
        .build();
    }
    
}
