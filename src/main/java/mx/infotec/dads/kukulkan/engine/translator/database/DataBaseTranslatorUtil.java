package mx.infotec.dads.kukulkan.engine.translator.database;

import java.util.Objects;

import org.apache.metamodel.factory.DataContextProperties;
import org.apache.metamodel.factory.DataContextPropertiesImpl;

/**
 * DataBaseTranslatorUtil
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class DataBaseTranslatorUtil {

    private static final String DATA_STORE_TYPE = "type";
    private static final String DATA_STORE_URL = "url";
    private static final String DATA_STORE_DRIVER_CLASS = "driver-class";
    private static final String DATA_STORE_USERNAME = "username";
    private static final String DATA_STORE_PASS = "password";

    public static DataContextProperties createDataContextProperties(DataStore dataStore) {
        validateDataStore(dataStore);
        DataContextPropertiesImpl properties = new DataContextPropertiesImpl();
        properties.put(DATA_STORE_TYPE, dataStore.getDataStoreType());
        properties.put(DATA_STORE_URL, dataStore.getUrl() + "/" + dataStore.getSchema());
        properties.put(DATA_STORE_DRIVER_CLASS, dataStore.getDriverClass());
        properties.put(DATA_STORE_USERNAME, dataStore.getUsername());
        properties.put(DATA_STORE_PASS, dataStore.getPassword());
        return properties;
    }

    public static void validateDataStore(DataStore ds) {
        Objects.requireNonNull(ds, "The <<DataStore>> can not be null");
        Objects.requireNonNull(ds.getDataStoreType(), "The <<DataStoreType>> can not be null");
        Objects.requireNonNull(ds.getUrl(), "The <<Url>> can not be null");
        Objects.requireNonNull(ds.getSchema(), "The <<Schema>> can not be null");
        Objects.requireNonNull(ds.getDriverClass(), "The <<DriverClass>> can not be null");
        Objects.requireNonNull(ds.getUsername(), "The <<Username>> can not be null");
        Objects.requireNonNull(ds.getPassword(), "The <<Password>> can not be null");
    }

    private DataBaseTranslatorUtil() {

    }
}
