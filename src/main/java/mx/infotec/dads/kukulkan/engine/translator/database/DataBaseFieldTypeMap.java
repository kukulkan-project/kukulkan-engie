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
import java.util.UUID;

import org.apache.metamodel.schema.ColumnType;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldTypeImpl;
import mx.infotec.dads.kukulkan.metamodel.foundation.FieldType;
import mx.infotec.dads.kukulkan.metamodel.foundation.GrammarFieldType;

/**
 * DataBaseFieldTypeMap is used for get the FieldType equivalent for DataBase
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class DataBaseFieldTypeMap {

    private DataBaseFieldTypeMap() {

    }

    /** The Constant map. */
    private static final Map<String, GrammarFieldType> FIELD_TYPE_MAP;
    static {
        FIELD_TYPE_MAP = new HashMap<>();
        /*
         * Literal
         */

        FIELD_TYPE_MAP.put(ColumnType.CHAR.getName(), new GrammarFieldTypeImpl(FieldType.STRING, LITERAL_TYPE));
        FIELD_TYPE_MAP.put(ColumnType.VARCHAR.getName(), new GrammarFieldTypeImpl(FieldType.STRING, LITERAL_TYPE));
        FIELD_TYPE_MAP.put(ColumnType.LONGVARCHAR.getName(),
                new GrammarFieldTypeImpl(FieldType.TEXT_BLOB, LITERAL_TYPE, String.class, true));
        FIELD_TYPE_MAP.put(ColumnType.CLOB.getName(),
                new GrammarFieldTypeImpl(FieldType.TEXT_BLOB, LITERAL_TYPE, String.class, true));
        FIELD_TYPE_MAP.put(ColumnType.NCHAR.getName(), new GrammarFieldTypeImpl(FieldType.STRING, LITERAL_TYPE));
        FIELD_TYPE_MAP.put(ColumnType.NVARCHAR.getName(),
                new GrammarFieldTypeImpl(FieldType.TEXT_BLOB, LITERAL_TYPE, String.class, true));
        FIELD_TYPE_MAP.put(ColumnType.LONGNVARCHAR.getName(),
                new GrammarFieldTypeImpl(FieldType.TEXT_BLOB, LITERAL_TYPE, String.class, true));
        FIELD_TYPE_MAP.put(ColumnType.NCLOB.getName(),
                new GrammarFieldTypeImpl(FieldType.TEXT_BLOB, LITERAL_TYPE, String.class, true));

        /*
         * Numbers
         */
        FIELD_TYPE_MAP.put(ColumnType.TINYINT.getName(), new GrammarFieldTypeImpl(FieldType.INTEGER, NUMBER_TYPE, Integer.class));
        FIELD_TYPE_MAP.put(ColumnType.SMALLINT.getName(), new GrammarFieldTypeImpl(FieldType.INTEGER, NUMBER_TYPE, Integer.class));
        FIELD_TYPE_MAP.put(ColumnType.INTEGER.getName(), new GrammarFieldTypeImpl(FieldType.INTEGER, NUMBER_TYPE, Integer.class));
        FIELD_TYPE_MAP.put(ColumnType.BIGINT.getName(), new GrammarFieldTypeImpl(FieldType.LONG, NUMBER_TYPE, Long.class));
        FIELD_TYPE_MAP.put(ColumnType.FLOAT.getName(), new GrammarFieldTypeImpl(FieldType.FLOAT, NUMBER_TYPE, Float.class)); 
        FIELD_TYPE_MAP.put(ColumnType.REAL.getName(), new GrammarFieldTypeImpl(FieldType.BIG_DECIMAL, NUMBER_TYPE, BigDecimal.class));
        FIELD_TYPE_MAP.put(ColumnType.DOUBLE.getName(), new GrammarFieldTypeImpl(FieldType.DOUBLE, NUMBER_TYPE, Double.class)); 
        FIELD_TYPE_MAP.put(ColumnType.NUMERIC.getName(), new GrammarFieldTypeImpl(FieldType.BIG_DECIMAL, NUMBER_TYPE, BigDecimal.class));
        FIELD_TYPE_MAP.put(ColumnType.DECIMAL.getName(), new GrammarFieldTypeImpl(FieldType.BIG_DECIMAL, NUMBER_TYPE, BigDecimal.class));
        FIELD_TYPE_MAP.put(ColumnType.UUID.getName(), new GrammarFieldTypeImpl(FieldType.LONG, NUMBER_TYPE, UUID.class));
        
        /*
         * Time based
         */
        FIELD_TYPE_MAP.put(ColumnType.DATE.getName(), new GrammarFieldTypeImpl(FieldType.DATE, TIME_TYPE, Date.class)); 
        FIELD_TYPE_MAP.put(ColumnType.TIME.getName(), new GrammarFieldTypeImpl(FieldType.DATE, TIME_TYPE, Date.class));
        FIELD_TYPE_MAP.put(ColumnType.TIMESTAMP.getName(), new GrammarFieldTypeImpl(FieldType.ZONED_DATETIME, TIME_TYPE, ZonedDateTime.class));

        /*
         * Booleans
         */
        FIELD_TYPE_MAP.put(ColumnType.BIT.getName(), new GrammarFieldTypeImpl(FieldType.BOOLEAN_TYPE, BOOLEAN_TYPE, boolean.class));
        FIELD_TYPE_MAP.put(ColumnType.BOOLEAN.getName(), new GrammarFieldTypeImpl(FieldType.BOOLEAN_TYPE, BOOLEAN_TYPE, boolean.class));
        
        /*
         * Blobs
         */
        
        FIELD_TYPE_MAP.put(ColumnType.BINARY.getName(), new GrammarFieldTypeImpl(FieldType.BLOB, BINARY_TYPE, byte[].class, true));
        FIELD_TYPE_MAP.put(ColumnType.VARBINARY.getName(), new GrammarFieldTypeImpl(FieldType.BLOB, BINARY_TYPE, byte[].class, true));
        FIELD_TYPE_MAP.put(ColumnType.LONGVARBINARY.getName(), new GrammarFieldTypeImpl(FieldType.BLOB, BINARY_TYPE, byte[].class, true));
        FIELD_TYPE_MAP.put(ColumnType.BLOB.getName(), new GrammarFieldTypeImpl(FieldType.BLOB, BINARY_TYPE, byte[].class, true));
        
    }

    public static GrammarFieldType fieldTypeFrom(String from) {
        return FIELD_TYPE_MAP.get(from);
    }
}
