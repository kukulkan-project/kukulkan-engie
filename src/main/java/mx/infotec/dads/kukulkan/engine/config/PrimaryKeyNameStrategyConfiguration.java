package mx.infotec.dads.kukulkan.engine.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.infotec.dads.kukulkan.engine.service.pk.DefaultPrimaryKeyNameStrategy;
import mx.infotec.dads.kukulkan.metamodel.conventions.PrimaryKeyNameStrategy;

@Configuration
public class PrimaryKeyNameStrategyConfiguration {

    @Bean
    @ConditionalOnMissingBean(PrimaryKeyNameStrategy.class)
    public PrimaryKeyNameStrategy promptProvider() {
        return new DefaultPrimaryKeyNameStrategy();
    }

}
