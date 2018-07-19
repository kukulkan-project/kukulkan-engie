package mx.infotec.dads.kukulkan.engine.translator;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.kukulkan.engine.config.InflectorConf;
import mx.infotec.dads.kukulkan.engine.config.XtextDSLConfiguration;
import mx.infotec.dads.kukulkan.engine.language.JavaProperty;
import mx.infotec.dads.kukulkan.engine.service.DefaultModelValidator;
import mx.infotec.dads.kukulkan.engine.service.InflectorService;
import mx.infotec.dads.kukulkan.engine.service.InflectorServiceImpl;
import mx.infotec.dads.kukulkan.engine.translator.database.DataBaseTranslatorService;
import mx.infotec.dads.kukulkan.engine.translator.database.DefaultSchemaAnalyzer;
import mx.infotec.dads.kukulkan.engine.translator.database.SchemaAnalyzer;
import mx.infotec.dads.kukulkan.engine.translator.dsl.FileSource;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarTranslatorService;
import mx.infotec.dads.kukulkan.engine.util.EntityFactory;
import mx.infotec.dads.kukulkan.metamodel.foundation.Constraint;
import mx.infotec.dads.kukulkan.metamodel.foundation.DatabaseType;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.Entity;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.foundation.Property;
import mx.infotec.dads.kukulkan.metamodel.translator.Source;
import mx.infotec.dads.nlp.inflector.core.Inflector;
import mx.infotec.dads.nlp.inflector.service.EnglishInflector;
import mx.infotec.dads.nlp.inflector.service.SpanishInflector;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = { GrammarTranslatorService.class,
        DataBaseTranslatorService.class, SchemaAnalyzer.class, DefaultSchemaAnalyzer.class, InflectorService.class,
        InflectorServiceImpl.class, Inflector.class, SpanishInflector.class, EnglishInflector.class,
        DefaultModelValidator.class })
@Import({ InflectorConf.class, XtextDSLConfiguration.class })
public class TranslatorServiceTest {

    @Autowired
    private GrammarTranslatorService grammarTranslatorService;

    @Test
    public void grammarTranslatorServiceWithXtextSemanticAnalyzer() {
        ProjectConfiguration pConf = EntityFactory.createProjectConfiguration(DatabaseType.SQL_MYSQL);
        Source fileSource = new FileSource("/home/roberto/git/kukulkan-engine/src/test/resources/domain-model.3k");
        DomainModel dm = grammarTranslatorService.translate(pConf, fileSource);

        Entity generated = getEntity(dm, "Persona").get();
        Entity fromFactory = getEntityPersonaForCompare();

        assert !dm.getDomainModelGroup().get(0).getEntities().isEmpty();
        assert fromFactory.getName().equals(generated.getName());
        assert fromFactory.getTableName().equals(generated.getTableName());
        
        //Display Field
        assert fromFactory.getDisplayField().getName().equals(generated.getDisplayField().getName());

        // nombre property
        Property<JavaProperty> nombreProp = getProperty(generated, "nombre").get();
        assert "nombre".equals(nombreProp.getName());
        Constraint nombreConstraint = nombreProp.getConstraint();

        assert !nombreConstraint.isNullable() && "3".equals(nombreConstraint.getMin())
                && "50".equals(nombreConstraint.getMax()) && "persona".equals(nombreConstraint.getPattern());

        // numero Property
        Property<JavaProperty> numeroProp = getProperty(generated, "numero").get();
        assert "numero".equals(numeroProp.getName());

        // associations
        assert !generated.getAssociations().isEmpty() && generated.getNotOwnerAssociations().isEmpty();
    }

    private static Optional<Entity> getEntity(DomainModel domainModel, String entityName) {
        for (Entity entity : domainModel.getDomainModelGroup().get(0).getEntities()) {
            if (entityName.equals(entity.getName())) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    private static Optional<Property<JavaProperty>> getProperty(Entity entity, String name) {
        for (Property<JavaProperty> property : entity.getProperties()) {
            if (name.equals(property.getName())) {
                return Optional.of(property);
            }
        }
        return Optional.empty();
    }

    private static Entity getEntityPersonaForCompare() {
        Entity entity = Entity.createDomainModelElement();
        entity.setName("Persona");
        entity.setTableName("usuarios");

        // numero Property
        JavaProperty numeroProperty = JavaProperty.builder().withType("Long").withName("numero").build();
        entity.addProperty(numeroProperty);
        entity.setDisplayField(numeroProperty);

        // nombre Property
        JavaProperty nombreProperty = JavaProperty.builder().withType("String").withName("nombre").build();
        entity.addProperty(nombreProperty);

        Constraint nombreConstraint = new Constraint();
        nombreConstraint.setNullable(true);
        nombreConstraint.setMin("3");
        nombreConstraint.setMax("50");
        nombreConstraint.setPattern("persona");

        return entity;
    }

}