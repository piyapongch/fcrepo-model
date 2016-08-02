/**
 * Piyapong Charoenwattana
 * Project: fcrepo4-oaiprovider
 */

package org.fcrepo.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.jena.riot.RDFLanguages;

/**
 * The Model class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Model {

    public enum Lang {

        // @TODO add all lang supports
        RDFXML(RDFLanguages.strLangRDFXML), TURTLE(RDFLanguages.strLangTurtle);

        private String lang;

        Lang(final String lang) {
            this.lang = lang;
        }

        public String getLang() {
            return lang;
        }
    }

    public enum ContentType {

        // @TODO add all contentType supports
        APPLICATION_RDFXML("application/rdf+xml"), TEXT_TURTLE("text/turtle");

        private String contentType;

        ContentType(final String contentType) {
            this.contentType = contentType;
        }

        public String getContentType() {
            return contentType;
        }
    }

    Lang lang() default Lang.TURTLE;

    ContentType contentType() default ContentType.TEXT_TURTLE;
}
