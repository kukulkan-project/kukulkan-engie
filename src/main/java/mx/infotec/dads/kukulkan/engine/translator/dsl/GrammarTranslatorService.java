package mx.infotec.dads.kukulkan.engine.translator.dsl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.engine.service.InflectorService;
import mx.infotec.dads.kukulkan.engine.service.ModelValidator;
import mx.infotec.dads.kukulkan.engine.translator.Source;
import mx.infotec.dads.kukulkan.engine.translator.TranslatorService;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelGroup;
import mx.infotec.dads.kukulkan.metamodel.foundation.JavaDomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;

/**
 * GrammarTranslatorService
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@Service
public class GrammarTranslatorService implements TranslatorService {

    @Autowired
    private ModelValidator validator;

    @Autowired
    private InflectorService inflectorService;

    @Override
    public DomainModel translate(ProjectConfiguration pConf, Source from) {
        DomainModel domainModel = new JavaDomainModel();
        from.getSource(File.class).ifPresent(file -> {
            GrammarSemanticAnalyzer semanticAnalyzer = new GrammarSemanticAnalyzer(pConf, inflectorService);
            List<DomainModelGroup> dmgList = GrammarMapping.createSingleDataModelGroupList(semanticAnalyzer, file);
            domainModel.setDomainModelGroup(dmgList);
        });
        validator.validateModel(domainModel);
        return domainModel;
    }
}
