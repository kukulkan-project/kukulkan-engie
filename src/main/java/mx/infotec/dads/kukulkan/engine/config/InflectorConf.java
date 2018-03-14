package mx.infotec.dads.kukulkan.engine.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import mx.infotec.dads.inflector.core.Dictionary;
import mx.infotec.dads.inflector.service.SpanishInflector;

@Configuration
@Import(mx.infotec.dads.inflector.config.InflectorConfiguration.class)
public class InflectorConf {

	@Bean
	public SpanishInflector inflectorSpanish(@Qualifier(value = "spanishDict") Dictionary dict) {
		return new SpanishInflector(dict);
	}
}
