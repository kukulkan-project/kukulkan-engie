package mx.infotec.dads.kukulkan.engine.translator.database;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

import mx.infotec.dads.kukulkan.metamodel.translator.Source;
import mx.infotec.dads.kukulkan.metamodel.util.MetaModelException;

/**
 * The DataBaseSource
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class DataBaseSource implements Source {

    private DataStore dataStore;

    public DataBaseSource(DataStore dataStore) {
        Objects.requireNonNull(dataStore, "The <<DataStore>> Can not be null");
        this.dataStore = dataStore;
    }

    @Override
    public <T> Optional<T> getSource(Class<T> clazz) {
        Objects.requireNonNull(clazz);
        if (!DataStore.class.equals(clazz)) {
            throw new MetaModelException(
                    "incompatibility types, excepted java.io.File, but encontered" + clazz.toString());
        }
        return Optional.of(clazz.cast(dataStore));
    }
}
