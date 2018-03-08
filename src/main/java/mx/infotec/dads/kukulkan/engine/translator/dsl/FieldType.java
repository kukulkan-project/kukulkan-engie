package mx.infotec.dads.kukulkan.engine.translator.dsl;

/**
 * GrammarDataTypes,
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public enum FieldType {

    STRING("String"), 
    INTEGER("Integer"), 
    LONG("Long"), 
    BIG_DECIMAL("BigDecimal"), 
    FLOAT("Float"), 
    DOUBLE("Double"), 
    BOOLEAN_TYPE("Boolean"), 
    DATE("Date"), 
    LOCAL_DATE("LocalDate"), 
    ZONED_DATETIME("ZonedDateTime"), 
    INSTANT("Instant"), 
    BLOB("Blob"), 
    ANY_BLOB("AnyBlob"), 
    IMAGE_BLOB("ImageBlob"), 
    TEXT_BLOB("TextBlob");
    
    private String text;
    
    private FieldType(String text){
        this.text=text;
    }
    
    public String text(){
        return this.text;
    }
}
