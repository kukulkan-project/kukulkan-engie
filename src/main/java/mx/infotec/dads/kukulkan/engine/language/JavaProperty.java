/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2016 Daniel Cortes Pichardo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package mx.infotec.dads.kukulkan.engine.language;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.apache.metamodel.schema.ColumnType;

import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldType;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarFieldTypeMapping;
import mx.infotec.dads.kukulkan.engine.translator.dsl.GrammarMapping;
import mx.infotec.dads.kukulkan.engine.util.DataBaseMapping;
import mx.infotec.dads.kukulkan.grammar.kukulkanParser.FieldTypeContext;
import mx.infotec.dads.kukulkan.metamodel.foundation.Constraint;
import mx.infotec.dads.kukulkan.metamodel.foundation.Property;

/**
 * PropertyHolder Class that is used for hold the properties of a table.
 *
 * @author Daniel Cortes Pichardo
 */
public class JavaProperty implements Property<JavaProperty> {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The name.
     */
    private String name;

    /**
     * The type.
     */
    private String type;

    /**
     * The qualified name.
     */
    private String qualifiedName;

    /**
     * The column name.
     */
    private String columnName;

    /**
     * The column type.
     */
    private String columnType;

    /**
     * The blob.
     */
    private boolean blob;

    /**
     * The blob.
     */
    private boolean anyBlob;

    /**
     * The blob.
     */
    private boolean imageBlob;

    /**
     * The clob.
     */
    private boolean clob;

    /**
     * The big decimal.
     */
    private boolean bigDecimal;

    /**
     * The local date.
     */
    private boolean localDate;

    /**
     * The instant.
     */
    private boolean instant;

    /**
     * The zone date time.
     */
    private boolean zoneDateTime;

    /**
     * The boolean type.
     */
    private boolean booleanType;

    /**
     * The number.
     */
    private boolean number;

    /**
     * The literal.
     */
    private boolean literal;

    /**
     * The large object.
     */
    private boolean largeObject;

    /**
     * The size validation.
     */
    private boolean sizeValidation;

    /**
     * The has constraints.
     */
    private boolean hasConstraints;

    /**
     * The java equivalent class.
     */
    private Class<?> javaEquivalentClass;

    /**
     * The constraint.
     */
    private Constraint constraint;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.metamodel.foundation.Property#isBigDecimal()
     */
    @Override
    public boolean isBigDecimal() {
        return bigDecimal;
    }

    /**
     * Sets the big decimal.
     *
     * @param bigDecimal the new big decimal
     */
    public void setBigDecimal(boolean bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isLocalDate()
     */
    @Override
    public boolean isLocalDate() {
        return localDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isInstant()
     */
    @Override
    public boolean isInstant() {
        return instant;
    }

    /**
     * Sets the instant.
     *
     * @param instant the new instant
     */
    public void setInstant(boolean instant) {
        this.instant = instant;

    }

    /**
     * Sets the local date.
     *
     * @param localDate the new local date
     */
    public void setLocalDate(boolean localDate) {
        this.localDate = localDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.metamodel.foundation.Property#isZoneDateTime()
     */
    @Override
    public boolean isZoneDateTime() {
        return zoneDateTime;
    }

    /**
     * Sets the zone date time.
     *
     * @param zoneDateTime the new zone date time
     */
    public void setZoneDateTime(boolean zoneDateTime) {
        this.zoneDateTime = zoneDateTime;
    }

    /**
     * Instantiates a new java property.
     */
    private JavaProperty() {

    }

    /**
     * Builder.
     *
     * @return the java property builder
     */
    public static JavaPropertyBuilder builder() {
        return new JavaPropertyBuilder();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isBlob()
     */
    @Override
    public boolean isBlob() {
        return blob;
    }

    /**
     * Sets the blob.
     *
     * @param blob the new blob
     */
    public void setBlob(boolean blob) {
        this.blob = blob;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isBlob()
     */
    @Override
    public boolean isImageBlob() {
        return imageBlob;
    }

    /**
     * Sets the blob.
     *
     * @param imageBlob the new blob
     */
    public void setImageBlob(boolean imageBlob) {
        this.imageBlob = imageBlob;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isBlob()
     */
    @Override
    public boolean isAnyBlob() {
        return anyBlob;
    }

    /**
     * Sets the blob.
     *
     * @param anyBlob the new blob
     */
    public void setAnyBlob(boolean anyBlob) {
        this.anyBlob = anyBlob;
    }

    /**
     * Checks if is boolean.
     *
     * @return true, if is boolean
     */
    @Override
    public boolean isBoolean() {
        return booleanType;
    }

    /**
     * Sets the boolean.
     *
     * @param booleanType the new boolean
     */
    public void setBoolean(boolean booleanType) {
        this.booleanType = booleanType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isTime()
     */
    @Override
    public boolean isTime() {
        return isLocalDate() || isZoneDateTime() || isInstant();
    }

    /**
     * Sets the time.
     *
     * @param time the new time
     * @deprecated Use setLocaleDate, setZoneDateTime or setInstant
     */
    @Deprecated
    public void setTime(boolean time) {
        //Un used
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#getType()
     */
    @Override
    public String getType() {
        return this.type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.metamodel.foundation.Property#getQualifiedName()
     */
    @Override
    public String getQualifiedName() {
        return this.qualifiedName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.metamodel.foundation.Property#getAssociations()
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Collection<Property> getAssociations() {
        return new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(JavaProperty o) {
        return name.compareTo(o.getName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.metamodel.foundation.Property#getColumnName()
     */
    @Override
    public String getColumnName() {
        return this.columnName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.metamodel.foundation.Property#getColumnType()
     */
    @Override
    public String getColumnType() {
        return this.columnType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isClob()
     */
    @Override
    public boolean isClob() {
        return this.clob;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.metamodel.foundation.Property#getConstraint()
     */
    @Override
    public Constraint getConstraint() {
        return constraint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JavaProperty other = (JavaProperty) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    protected void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the qualified name.
     *
     * @param qualifiedName the new qualified name
     */
    protected void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    /**
     * Sets the column name.
     *
     * @param columnName the new column name
     */
    protected void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Sets the column type.
     *
     * @param columnType the new column type
     */
    protected void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    /**
     * Sets the clob.
     *
     * @param clob the new clob
     */
    public void setClob(boolean clob) {
        this.clob = clob;
    }

    /**
     * Sets the constraint.
     *
     * @param constraint the new constraint
     */
    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }

    /**
     * The Class JavaPropertyBuilder.
     */
    public static class JavaPropertyBuilder {

        /**
         * The java property.
         */
        private final JavaProperty javaProperty;

        /**
         * Instantiates a new java property builder.
         */
        public JavaPropertyBuilder() {
            this.javaProperty = new JavaProperty();
            this.javaProperty.setConstraint(new Constraint());
        }

        /**
         * With java equivalent class.
         *
         * @param javaEquivalentClass the java equivalent class
         * @return the java property builder
         */
        public JavaPropertyBuilder withJavaEquivalentClass(Class<?> javaEquivalentClass) {
            this.javaProperty.setJavaEquivalentClass(javaEquivalentClass);
            return this;
        }

        /**
         * Checks if is sizeValidation.
         *
         * @param sizeValidation the sizeValidation
         * @return the java property builder
         */
        public JavaPropertyBuilder hasSizeValidation(boolean sizeValidation) {
            javaProperty.setSizeValidation(sizeValidation);
            return this;
        }

        /**
         * With name.
         *
         * @param propertyName the property name
         * @return the java property builder
         */
        public JavaPropertyBuilder withName(String propertyName) {
            this.javaProperty.setName(propertyName);
            return this;
        }

        /**
         * With type.
         *
         * @param propertyType the property type
         * @return the java property builder
         */
        public JavaPropertyBuilder withType(String propertyType) {
            this.javaProperty.setType(propertyType);
            return this;
        }

        /**
         * With propertyType.
         *
         * @param propertyType the property type
         * @return the java property builder
         */
        public JavaPropertyBuilder withPropertyType(GrammarFieldType propertyType) {
            GrammarFieldTypeMapping.configurateGrammarFieldType(propertyType, this.javaProperty);
            this.isLargeObject(propertyType.isLargeObject());
            this.withType(propertyType.getJavaName());
            this.withColumnType(propertyType.getFieldType().text());
            this.withQualifiedName(propertyType.getJavaQualifiedName());
            this.withJavaEquivalentClass(propertyType.getJavaEquivalentClass());
            return this;
        }

        /**
         * With type.
         *
         * @param optional the optional
         * @return the java property builder
         */
        public JavaPropertyBuilder withType(Optional<GrammarFieldType> optional) {
            if (optional.isPresent()) {
                this.javaProperty.setType(optional.get().getJavaName());
            }
            return null;
        }

        /**
         * With qualified name.
         *
         * @param qualifiedName the qualified name
         * @return the java property builder
         */
        public JavaPropertyBuilder withQualifiedName(String qualifiedName) {
            this.javaProperty.setQualifiedName(qualifiedName);
            return this;
        }

        /**
         * With column name.
         *
         * @param columnName the column name
         * @return the java property builder
         */
        public JavaPropertyBuilder withColumnName(String columnName) {
            this.javaProperty.setColumnName(columnName);
            return this;
        }

        /**
         * With column type.
         *
         * @param columnType the column type
         * @return the java property builder
         */
        public JavaPropertyBuilder withColumnType(String columnType) {
            this.javaProperty.setColumnType(columnType);
            return this;
        }

        /**
         * Checks if is nullable.
         *
         * @param nullable the nullable
         * @return the java property builder
         */
        public JavaPropertyBuilder isNullable(boolean nullable) {
            this.javaProperty.getConstraint().setNullable(nullable);
            return this;
        }

        /**
         * Checks if is primary key.
         *
         * @param isPrimaryKey the is primary key
         * @return the java property builder
         */
        public JavaPropertyBuilder isPrimaryKey(boolean isPrimaryKey) {
            this.javaProperty.getConstraint().setPrimaryKey(isPrimaryKey);
            return this;
        }

        /**
         * Checks if is indexed.
         *
         * @param indexed the indexed
         * @return the java property builder
         */
        public JavaPropertyBuilder isIndexed(boolean indexed) {
            this.javaProperty.getConstraint().setIndexed(indexed);
            return this;
        }

        /**
         * Adds the type.
         *
         * @param type the type
         * @return the java property builder
         */
        public JavaPropertyBuilder addType(ColumnType type) {
            DataBaseMapping.addType(javaProperty, type);
            return this;
        }

        /**
         * Adds the type.
         *
         * @param type the type
         * @return the java property builder
         */
        public JavaPropertyBuilder addType(FieldTypeContext type) {
            GrammarMapping.addType(javaProperty, type);
            return this;
        }

        /**
         * Checks if is literal.
         *
         * @param literal the literal
         * @return the java property builder
         */
        public JavaPropertyBuilder isLiteral(boolean literal) {
            javaProperty.setLiteral(literal);
            return this;
        }

        /**
         * Checks if is big decimal.
         *
         * @param bigDecimal the big decimal
         * @return the java property builder
         */
        public JavaPropertyBuilder isBigDecimal(boolean bigDecimal) {
            this.javaProperty.setBigDecimal(bigDecimal);
            return this;
        }

        /**
         * Checks if is local date.
         *
         * @param localDate the local date
         * @return the java property builder
         */
        public JavaPropertyBuilder isLocalDate(boolean localDate) {
            this.javaProperty.setLocalDate(localDate);
            return this;
        }

        /**
         * Checks if is zone date time.
         *
         * @param zoneDateTime the zone date time
         * @return the java property builder
         */
        public JavaPropertyBuilder isZoneDateTime(boolean zoneDateTime) {
            this.javaProperty.setZoneDateTime(zoneDateTime);
            return this;
        }

        /**
         * Checks if is instance.
         *
         * @param instant the instant
         * @return the java property builder
         */
        public JavaPropertyBuilder isInstance(boolean instant) {
            this.javaProperty.setInstant(instant);
            return this;
        }

        /**
         * Checks if is large object.
         *
         * @param largeObject the large object
         * @return the java property builder
         */
        public JavaPropertyBuilder isLargeObject(boolean largeObject) {
            this.javaProperty.setLargeObject(largeObject);
            return this;
        }

        /**
         * Builds the.
         *
         * @return the java property
         */
        public JavaProperty build() {
            return this.javaProperty;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isNumber()
     */
    @Override
    public boolean isNumber() {
        return number;
    }

    /**
     * Sets the number.
     *
     * @param number the new number
     */
    public void setNumber(boolean number) {
        this.number = number;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isLiteral()
     */
    @Override
    public boolean isLiteral() {
        return literal;
    }

    /**
     * Sets the literal.
     *
     * @param literal the new literal
     */
    public void setLiteral(boolean literal) {
        this.literal = literal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.metamodel.foundation.Property#isLargeObject()
     */
    @Override
    public boolean isLargeObject() {
        return largeObject;
    }

    /**
     * Sets the large object.
     *
     * @param largeObject the new large object
     */
    public void setLargeObject(boolean largeObject) {
        this.largeObject = largeObject;
    }

    /**
     * Sets the size validation.
     *
     * @param sizeValidation the new size validation
     */
    public void setSizeValidation(boolean sizeValidation) {
        this.sizeValidation = sizeValidation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.metamodel.foundation.Property#isSizeValidation()
     */
    @Override
    public boolean isSizeValidation() {
        return sizeValidation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isLong()
     */
    @Override
    public boolean isLong() {
        return getJavaEquivalentClass().equals(Long.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isInteger()
     */
    @Override
    public boolean isInteger() {
        return getJavaEquivalentClass().equals(Integer.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isDouble()
     */
    @Override
    public boolean isDouble() {
        return getJavaEquivalentClass().equals(Double.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.infotec.dads.kukulkan.metamodel.foundation.Property#isFloat()
     */
    @Override
    public boolean isFloat() {
        return getJavaEquivalentClass().equals(Float.class);
    }

    /**
     * Gets the java equivalent class.
     *
     * @return the java equivalent class
     */
    public Class<?> getJavaEquivalentClass() {
        return javaEquivalentClass;
    }

    /**
     * Sets the java equivalent class.
     *
     * @param javaEquivalentClass the new java equivalent class
     */
    public void setJavaEquivalentClass(Class<?> javaEquivalentClass) {
        this.javaEquivalentClass = javaEquivalentClass;
    }

    /**
     * Sets the checks for constraints.
     *
     * @param hasConstraints the new checks for constraints
     */
    public void setHasConstraints(boolean hasConstraints) {
        this.hasConstraints = hasConstraints;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.infotec.dads.kukulkan.metamodel.foundation.Property#isHasConstraints()
     */
    @Override
    public boolean isHasConstraints() {
        return hasConstraints;
    }
}
