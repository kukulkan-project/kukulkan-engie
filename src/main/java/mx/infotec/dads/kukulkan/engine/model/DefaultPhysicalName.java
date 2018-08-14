package mx.infotec.dads.kukulkan.engine.model;

import com.google.common.base.CaseFormat;

import mx.infotec.dads.kukulkan.metamodel.conventions.PhysicalName;

public class DefaultPhysicalName implements PhysicalName {

    private static final long serialVersionUID = 1L;

    private String upperCamelCase;
    private String upperCamelCasePlural;
    private String lowerCamelCase;
    private String lowerCamelCasePlural;
    private String kebabCase;
    private String kebabCasePlural;
    private String snakeCase;
    private String snakeCasePlural;

    public DefaultPhysicalName(String loweCamelCaseName, String loweCamelCaseNamePlural) {
        this.upperCamelCase = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, loweCamelCaseName);
        this.upperCamelCasePlural = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, loweCamelCaseNamePlural);
        this.lowerCamelCase = loweCamelCaseName;
        this.lowerCamelCasePlural = loweCamelCaseNamePlural;
        this.kebabCase = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, loweCamelCaseName);
        this.kebabCase = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, loweCamelCaseNamePlural);
        this.snakeCase = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, loweCamelCaseName);
        this.snakeCasePlural = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, loweCamelCaseNamePlural);
    }

    @Override
    public String getUpperCamelCase() {
        return upperCamelCase;
    }

    @Override
    public String getUpperCamelCasePlural() {
        return upperCamelCasePlural;
    }

    @Override
    public String getLowerCamelCase() {
        return lowerCamelCase;
    }

    @Override
    public String getLowerCamelCasePlural() {
        return lowerCamelCasePlural;
    }

    public String getKebabCase() {
        return kebabCase;
    }

    @Override
    public String getKebabCasePlural() {
        return kebabCasePlural;
    }

    @Override
    public String getSnakeCase() {
        return snakeCase;
    }

    @Override
    public String getSnakeCasePlural() {
        return snakeCasePlural;
    }
}
