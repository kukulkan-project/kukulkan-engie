package mx.infotec.dads.kukulkan.engine.translator.database;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

import mx.infotec.dads.kukulkan.engine.translator.Source;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * The DataBaseSource
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class DataBaseSource {

    private DataStore dataStore;
    

//    @Override
//    public <T> Optional<T> getSource(Class<T> clazz) {
//        Objects.requireNonNull(clazz);
//        if (!File.class.equals(clazz)) {
//            throw new MetaModelException(
//                    "incompatibility types, excepted java.io.File, but encontered" + clazz.toString());
//        }
//        return Optional.of(clazz.cast(source));
//    }

}
