package mx.infotec.dads.kukulkan.engine.translator.dsl;

import java.io.File;
import java.util.List;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.infotec.dads.kukulkan.engine.service.InflectorService;
import mx.infotec.dads.kukulkan.engine.service.ModelValidator;
import mx.infotec.dads.kukulkan.metamodel.conventions.PrimaryKeyNameStrategy;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.DomainModelGroup;
import mx.infotec.dads.kukulkan.metamodel.foundation.JavaDomainModel;
import mx.infotec.dads.kukulkan.metamodel.foundation.ProjectConfiguration;
import mx.infotec.dads.kukulkan.metamodel.translator.Source;
import mx.infotec.dads.kukulkan.metamodel.translator.TranslatorService;

@Service(value = "grammarTranslatorService")
public class GrammarTranslatorService implements TranslatorService {

    @Autowired
    private ModelValidator validator;

    @Autowired
    private InflectorService inflectorService;

    @Autowired
    private ResourceSet resourceSet;
    
    @Autowired
    private PrimaryKeyNameStrategy primaryKeyNameStrategy;

    @Override
    public DomainModel translate(ProjectConfiguration pConf, Source from) {
        DomainModel domainModel = new JavaDomainModel();
        from.getSource(File.class).ifPresent(file -> {
            GrammarSemanticAnalyzer semanticAnalyzer = new GrammarSemanticAnalyzer(pConf, inflectorService, primaryKeyNameStrategy);
            List<DomainModelGroup> dmgList = GrammarMapping.createSingleDataModelGroupList(semanticAnalyzer, file,
                    resourceSet);
            domainModel.setDomainModelGroup(dmgList);
        });
        validator.validateModel(domainModel);
        return domainModel;
    }
}
