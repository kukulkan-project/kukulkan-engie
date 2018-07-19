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

import org.apache.metamodel.schema.ColumnType;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldTypeImpl;
import mx.infotec.dads.kukulkan.metamodel.foundation.FieldType;
import mx.infotec.dads.kukulkan.metamodel.foundation.GrammarFieldType;
import mx.infotec.dads.kukulkan.metamodel.translator.FieldTypeMap;

/**
 * DataBaseFieldTypeMap is used for get the FieldType equivalent for DataBase
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class DataBaseFieldTypeMap implements FieldTypeMap<String> {

    private DataBaseFieldTypeMap() {

    }

    /** The Constant map. */
    private static final Map<String, GrammarFieldType> FIELD_TYPE_MAP;
    static {
        FIELD_TYPE_MAP = new HashMap<>();
        /*
         * Literal
         */
        FIELD_TYPE_MAP.put(ColumnType.STRING.getName(), new GrammarFieldTypeImpl(FieldType.STRING, LITERAL_TYPE));
        FIELD_TYPE_MAP.put(FieldType.TEXT_BLOB.text(),
                new GrammarFieldTypeImpl(FieldType.TEXT_BLOB, LITERAL_TYPE, String.class, true));

        /*
         * Numbers
         */
        FIELD_TYPE_MAP.put(FieldType.INTEGER.text(),
                new GrammarFieldTypeImpl(FieldType.INTEGER, NUMBER_TYPE, Integer.class));
        FIELD_TYPE_MAP.put(FieldType.LONG.text(), new GrammarFieldTypeImpl(FieldType.LONG, NUMBER_TYPE, Long.class));
        FIELD_TYPE_MAP.put(FieldType.BIG_DECIMAL.text(),
                new GrammarFieldTypeImpl(FieldType.BIG_DECIMAL, NUMBER_TYPE, BigDecimal.class));
        FIELD_TYPE_MAP.put(FieldType.FLOAT.text(), new GrammarFieldTypeImpl(FieldType.FLOAT, NUMBER_TYPE, Float.class));
        FIELD_TYPE_MAP.put(FieldType.DOUBLE.text(),
                new GrammarFieldTypeImpl(FieldType.DOUBLE, NUMBER_TYPE, Double.class));

        /*
         * Time based
         */
        FIELD_TYPE_MAP.put(FieldType.DATE.text(), new GrammarFieldTypeImpl(FieldType.DATE, TIME_TYPE, Date.class));
        FIELD_TYPE_MAP.put(FieldType.LOCAL_DATE.text(),
                new GrammarFieldTypeImpl(FieldType.LOCAL_DATE, TIME_TYPE, LocalDate.class));
        FIELD_TYPE_MAP.put(FieldType.ZONED_DATETIME.text(),
                new GrammarFieldTypeImpl(FieldType.ZONED_DATETIME, TIME_TYPE, ZonedDateTime.class));
        FIELD_TYPE_MAP.put(FieldType.INSTANT.text(),
                new GrammarFieldTypeImpl(FieldType.INSTANT, TIME_TYPE, Instant.class));

        /*
         * Booleans
         */
        FIELD_TYPE_MAP.put(FieldType.BOOLEAN_TYPE.text(),
                new GrammarFieldTypeImpl(FieldType.BOOLEAN_TYPE, BOOLEAN_TYPE, boolean.class));

        /*
         * Blobs
         */
        FIELD_TYPE_MAP.put(FieldType.BLOB.text(),
                new GrammarFieldTypeImpl(FieldType.BLOB, BINARY_TYPE, byte[].class, true));
        FIELD_TYPE_MAP.put(FieldType.ANY_BLOB.text(),
                new GrammarFieldTypeImpl(FieldType.ANY_BLOB, BINARY_TYPE, byte[].class, true));
        FIELD_TYPE_MAP.put(FieldType.IMAGE_BLOB.text(),
                new GrammarFieldTypeImpl(FieldType.IMAGE_BLOB, BINARY_TYPE, byte[].class, true));

    }

    @Override
    public GrammarFieldType fieldTypeFrom(String from) {
        // TODO Auto-generated method stub
        return null;
    }
}
