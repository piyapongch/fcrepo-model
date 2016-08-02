/**
 * Piyapong Charoenwattana
 * Project: fcrepo4-oaiprovider
 */

package org.fcrepo.model.vocabulary;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * The FCREPO class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
public class FCREPO {

    private static Model model = ModelFactory.createDefaultModel();

    public static final String NS = "http://fedora.info/definitions/v4/repository#";

    public static final Resource NAMESPACE = model.createResource(NS);

    public static final Property uuid = model.createProperty(NS + "uuid");

    public static final Property lastModifiedBy = model.createProperty(NS + "lastModifiedBy");

    public static final Property lastModified = model.createProperty(NS + "lastModified");

    public static final Property createdBy = model.createProperty(NS + "createdBy");

    public static final Property created = model.createProperty(NS + "created");

    public static final Property hasVersionLabel = model.createProperty(NS + "hasVersionLabel");
}
