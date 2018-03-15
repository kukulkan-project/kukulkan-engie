package mx.infotec.dads.kukulkan.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.infotec.dads.nlp.inflector.core.Inflector;

@Service("defaultInflectorService")
public class InflectorServiceImpl implements InflectorService {

	@Autowired
	private Inflector inflector;

	@Override
	public String singularize(String word) {
		return inflector.singularize(word);
	}

	@Override
	public String pluralize(String word) {
		return inflector.pluralize(word);
	}

}
