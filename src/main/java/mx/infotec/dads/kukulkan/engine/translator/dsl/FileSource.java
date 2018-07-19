package mx.infotec.dads.kukulkan.engine.translator.dsl;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

import mx.infotec.dads.kukulkan.metamodel.translator.Source;
import mx.infotec.dads.kukulkan.metamodel.translator.TranslatorService;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * FileSource, It is used by {@link TranslatorService} as parameter
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class FileSource implements Source {

    private File source;

    public FileSource(File source) {
        this.source = source;
    }

    public FileSource(String source) {
        this.source = new File(source);
    }

    @Override
    public <T> Optional<T> getSource(Class<T> clazz) {
        Objects.requireNonNull(clazz);
        if (!File.class.equals(clazz)) {
            throw new MetaModelException(
                    "incompatibility types, excepted java.io.File, but encontered" + clazz.toString());
        }
        return Optional.of(clazz.cast(source));
    }
}
