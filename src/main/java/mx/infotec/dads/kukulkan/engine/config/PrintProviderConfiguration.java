package mx.infotec.dads.kukulkan.engine.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.infotec.dads.kukulkan.engine.service.DefaultGeneratorPrintProvider;
import mx.infotec.dads.kukulkan.engine.service.GeneratorPrintProvider;

@Configuration
public class PrintProviderConfiguration {

    @Bean
    @ConditionalOnMissingBean(GeneratorPrintProvider.class)
    public GeneratorPrintProvider promptProvider() {
        return new DefaultGeneratorPrintProvider();
    }
}
