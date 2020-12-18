package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;
import softuni.exam.util.impl.ValidatorUtilImpl;
import softuni.exam.util.impl.XmlParserImpl;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }


    @Bean
    public ValidatorUtil validationUtil() {
        return new ValidatorUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public XmlParser xmlParser (){
        return new XmlParserImpl();
    }
}
