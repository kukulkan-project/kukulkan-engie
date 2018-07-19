package mx.infotec.dads.kukulkan.engine.translator.database;

import static mx.infotec.dads.kukulkan.metamodel.foundation.SuperColumnType.BINARY_TYPE;
import static mx.infotec.dads.kukulkan.metamodel.foundation.SuperColumnType.BOOLEAN_TYPE;
import static mx.infotec.dads.kukulkan.metamodel.foundation.SuperColumnType.LITERAL_TYPE;
import static mx.infotec.dads.kukulkan.metamodel.foundation.SuperColumnType.NUMBER_TYPE;
import static mx.infotec.dads.kukulkan.metamodel.foundation.SuperColumnType.TIME_TYPE;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.metamodel.factory.DataContextProperties;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.ColumnType;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldTypeImpl;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldTypeMapping;
import mx.infotec.dads.kukulkan.metamodel.foundation.FieldType;
import mx.infotec.dads.kukulkan.metamodel.foundation.GrammarFieldType;

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

    public static GrammarFieldType fieldTypeFrom(Column column) {
        ColumnType type = column.getType();
//        DataBaseTypeMap.
        if (type.equals(ColumnType.ARRAY)) {
            return GrammarFieldTypeMapping.fieldTypeFrom(FieldType.STRING);
        }        
        // GrammarFieldTypeMapping.getMap()
        return null;
    }

    public static String propertyNameFrom(Column column) {
        column.getName();
        // TODO Auto-generated method stub
        return null;
    }
    
    public static boolean isString(Column column){

        return false;
    } 

    private DataBaseTranslatorUtil() {

    }
}
