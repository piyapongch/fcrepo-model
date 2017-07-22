/**
 * Piyapong Charoenwattana
 * Project: fcrepo4-oaiprovider
 */

package org.fcrepo.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.fcrepo.model.vocabulary.FCREPO;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * The Property class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Field {

    public enum Property {

        // Fedora Properties
        FEDORA_UUID(FCREPO.uuid), FEDORA_LAST_MODIFIED(FCREPO.lastModified), FEDORA_LAST_MODIFIED_BY(
            FCREPO.lastModifiedBy), FEDORA_CREATED_BY(FCREPO.createdBy),

        // RDF Properties
        RDF_TYPE(RDF.type),

        // DC Properties
        DC_TITLE(DC.title), DC_CREATOR(DC.creator), DC_DESCRIPTION(DC.description), DC_DATE(DC.date),

        // DCTerms Properties
        DCTERMS_TITLE(DCTerms.title), DCTERMS_CREATOR(DCTerms.creator), DCTERMS_DESCRIPTION(
            DCTerms.description), DCTERMS_CREATED(DCTerms.created);

        private com.hp.hpl.jena.rdf.model.Property property;

        Property(final com.hp.hpl.jena.rdf.model.Property property) {
            this.property = property;
        }

        public com.hp.hpl.jena.rdf.model.Property getProperty() {
            return property;
        }
    }

    public enum DataType {
        NULL(null), XSD_STRING(XSDDatatype.XSDstring), XSD_DATE(XSDDatatype.XSDdate), XSD_DATETIME(
            XSDDatatype.XSDdateTime), XSD_URI(XSDDatatype.XSDanyURI);

        private XSDDatatype dataType;

        DataType(final XSDDatatype datatype) {
            this.dataType = datatype;
        }

        public XSDDatatype getDatatype() {
            return dataType;
        }
    }

    Property property();

    DataType dataType()

    default DataType.NULL;

    boolean read()

    default true;

    boolean write() default true;
}
