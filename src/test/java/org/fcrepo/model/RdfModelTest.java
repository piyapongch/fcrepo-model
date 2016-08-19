/**
 * Piyapong Charoenwattana
 * Project: fcrepo4-oaiprovider
 */

package org.fcrepo.model;

import static java.lang.System.out;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.jena.riot.RDFLanguages;
import org.apache.tika.io.IOUtils;
import org.fcrepo.client.FcrepoClient;
import org.fcrepo.client.FcrepoResponse;
import org.fcrepo.model.model.Item;
import org.fcrepo.model.model.Version;
import org.fcrepo.model.vocabulary.FCREPO;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.NsIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.DC;

/**
 * The RdfModelTest class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
public class RdfModelTest {

    // some definitions
    static String tutorialURI = "http://hostname/rdf/tutorial/";
    static String briansName = "Brian McBride";
    static String briansEmail1 = "brian_mcbride@hp.com";
    static String briansEmail2 = "brian_mcbride@hpl.hp.com";
    static String title = "An Introduction to RDF and the Jena API";
    static String date = "23/01/2001";
    private FcrepoClient fc;

    // @Test
    public void testTurtle() throws Exception {

        // some definitions
        final String personURI = "http://somewhere/JohnSmith";
        final String givenName = "John";
        final String familyName = "Smith";
        final String fullName = givenName + " " + familyName;
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // create the resource
        // and add the properties cascading style
        // final Resource johnSmith =
        // model.createResource("http://localhost:8080/fedora/rest/test/item1")
        // .addProperty(DC.creator, "Piyapong Charoenwattana", XSDDatatype.XSDstring)
        // .addProperty(DC.title, "test put item 1", XSDDatatype.XSDstring)
        // .addProperty(DC.description, "Test ingest item using Jena and Fedora Client", XSDDatatype.XSDstring)
        // .addProperty(DC.contributor, "Sumrarn Pisaisansuk", XSDDatatype.XSDstring);

        // now write the model in XML form to a file
        System.out.println("----------- RdfModelTest -----------");
        final ByteArrayOutputStream bo = new ByteArrayOutputStream();
        model.write(bo, "TURTLE");

        // create fedora client
        final FcrepoClient fc =
            FcrepoClient.client().authScope("localhost").credentials("fedoraAdmin", "fedoraAdmin")
                .throwExceptionOnFailure().build();

        try {

            final FcrepoResponse resp = fc.get(new URI("http://localhost:8080/fedora/rest/test")).perform();
            model = ModelFactory.createDefaultModel();
            model.read(resp.getBody(), "", "TURTLE");
            model.write(out, RDFLanguages.strLangRDFXML);
            final Property prop = model.getProperty("http://www.w3.org/ns/ldp#contains");

            final NodeIterator lst = model.listObjectsOfProperty(prop);

            out.println("------------ test: ldp links ------------");
            while (lst.hasNext()) {
                final RDFNode rdfNode = lst.next();
                out.println("uri: " + rdfNode.asResource().getURI());
            }

            out.println("------------ create new object /test ------------");
            final FcrepoResponse resp2 = fc.post(new URI("http://localhost:8080/fedora/rest/test")).perform();
            out.println("post: " + resp2.getUrl());
            out.println("post: " + resp2.getStatusCode());
            out.println("post: " + resp2.getContentType());
            out.println("post: " + resp2.getLocation());

            out.println("------------ get object and add properties ------------");
            FcrepoResponse resp3 = fc.get(resp2.getLocation()).perform();
            model = ModelFactory.createDefaultModel();
            model.read(resp3.getBody(), resp2.getLocation().toString(), "TURTLE");
            model.getResource(resp2.getLocation().toString())
                .addProperty(model.createProperty("http://purl.org/ontology/bibo/ThesisDegree"), "Doctor of Philosophy",
                    XSDDatatype.XSDstring)
                .addProperty(DC.coverage, "Coverage XXX...", XSDDatatype.XSDstring)
                .addProperty(DC.creator, "Piyapong Charoenwattana", XSDDatatype.XSDstring)
                .addProperty(DC.title, "test put item 1", XSDDatatype.XSDstring)
                .addProperty(DC.description, "Test ingest item using Jena and Fedora Client", XSDDatatype.XSDstring)
                .addProperty(DC.contributor, "Sumrarn Pisaisansuk", XSDDatatype.XSDstring);
            model.write(bo, "TURTLE");
            out.println(new String(bo.toByteArray()));
            out.println("------------ update to repository ------------");
            resp3 =
                fc.put(resp2.getLocation()).body(new ByteArrayInputStream(bo.toByteArray()), "text/turtle").perform();
            out.println("put: " + resp3.getUrl());
            out.println("put: " + resp3.getStatusCode());

        } catch (final Exception e) {
            out.println(e);
        }

        // read file
        final Model model2 = ModelFactory.createDefaultModel();
        final FileReader f = new FileReader("src/test/resources/test-data/sample.ttl");
        model2.read(f, "", "TURTLE");

        System.out.println("----------- Statements -----------");
        final StmtIterator stmt = model2.listStatements();
        while (stmt.hasNext()) {
            final Statement st = stmt.next();
            System.out.println(st.toString());
        }

        System.out.println("----------- NameSpaces -----------");
        final NsIterator ns = model2.listNameSpaces();
        while (ns.hasNext()) {
            final String s = ns.next();
            System.out.println(s);
        }
    }

    @Before
    public void setUp() {
        fc =
            FcrepoClient.client().authScope("localhost").credentials("fedoraAdmin", "fedoraAdmin")
                .throwExceptionOnFailure().build();

    }

    @SuppressWarnings("unchecked")
    // @Test
    public void testModel() throws Exception {

        // @TODO create DAO Manager

        final Item item = new Item();
        item.setDcTitle(Arrays.asList("title 1", "test fcrepo model annotation: @Model @Field..."));
        item.setDcCreator(Arrays.asList("Sumrarn Pisaisansuk", "Piyapong Charoenwattana"));

        // get lang and contentTypr from @Model
        final org.fcrepo.model.annotation.Model ma =
            item.getClass().getAnnotation(org.fcrepo.model.annotation.Model.class);
        final String lang = ma.lang().getLang();
        final String contentType = ma.contentType().getContentType();

        // create new object
        final FcrepoResponse resp2 = fc.post(new URI("http://localhost:8080/fedora/rest/test")).perform();
        final URI uri = resp2.getLocation();

        // get the object
        final FcrepoResponse resp3 = fc.get(resp2.getLocation()).accept(contentType).perform();
        out.println(">> uri : " + uri + "; " + resp2.getContentType());
        out.println("resp3.contentType: " + resp3.getContentType());
        out.println(">> headers -----------------");
        final Map<String, List<String>> hd = resp3.getHeaders();
        hd.entrySet().stream().forEach(out::println);

        // create model from fedora object response
        final Model model = ModelFactory.createDefaultModel();
        model.read(resp3.getBody(), uri.toString(), lang);

        // update model with item
        final Resource r = model.getResource(resp2.getLocation().toString());
        final Field[] fd = item.getClass().getDeclaredFields();
        for (final Field f : fd) {
            f.setAccessible(true);
            final org.fcrepo.model.annotation.Field fa = f.getAnnotation(org.fcrepo.model.annotation.Field.class);
            r.removeAll(fa.property().getProperty());
            final List<String> l = (List<String>) f.get(item);
            if (l != null) {
                for (final String s : l) {
                    r.addProperty(fa.property().getProperty(), s, fa.dataType().getDatatype());
                }
            }
        }
        final ByteArrayOutputStream bo = new ByteArrayOutputStream();
        model.write(bo, lang);
        out.println(">> item contents -------------------");
        out.println(bo.toString());

        // update fedora object on repository
        // final FcrepoResponse resp4 =
        // fc.put(uri).body(new ByteArrayInputStream(bo.toByteArray()), contentType).perform();

        // get the object
        final FcrepoResponse resp5 = fc.get(uri).accept(contentType).perform();
        model.removeAll();
        model.read(resp5.getBody(), uri.toString(), lang);
        final Resource r2 = model.getResource(uri.toString());

        // create item model from rdf model
        final Item item2 = new Item();
        for (final Field f : fd) {
            f.setAccessible(true);
            final org.fcrepo.model.annotation.Field fa = f.getAnnotation(org.fcrepo.model.annotation.Field.class);
            if (r2.hasProperty(fa.property().getProperty())) {
                final StmtIterator s = r2.listProperties(fa.property().getProperty());
                final ArrayList<String> l = new ArrayList<String>();
                while (s.hasNext()) {
                    final Statement sm = s.next();
                    l.add(sm.getString());
                }
                f.set(item2, l);
            }
        }
        out.println(">> item2 --------------");
        out.println(item2);
    }

    // @Test
    public void testDateFormat() throws Exception {
        // create new version using timestamp
        out.println(ISODateTimeFormat.basicDateTime().print(new DateTime(DateTimeZone.UTC)));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUpdate() throws Exception {

        // get resource versions
        final URI vUri = new URI("http://localhost:8080/fedora/rest/test/8p/58/pc/92/8p58pc92q/fcr:versions");
        FcrepoResponse resp10 = null;

        resp10 =
            fc.get(vUri).accept(org.fcrepo.model.annotation.Model.ContentType.APPLICATION_RDFXML.getContentType())
                .perform();
        out.println(">> get versions status: " + resp10.getStatusCode());
        resp10.getHeaders().entrySet().stream().forEach(out::println);
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOUtils.copy(resp10.getBody(), bos);
        out.println(">> versions:\n" + bos.toString());

        // list versions
        final List<Version> vers = new ArrayList<Version>();
        final Model model2 = ModelFactory.createDefaultModel();
        model2.read(new ByteArrayInputStream(bos.toByteArray()), null);
        final Resource rs = model2.getResource("http://localhost:8080/fedora/rest/test/8p/58/pc/92/8p58pc92q");
        final StmtIterator st = rs.listProperties();
        while (st.hasNext()) {
            final Statement s = st.next();
            vers.add(new Version(s.getObject().asResource().getURI(),
                s.getObject().asResource().getProperty(FCREPO.hasVersionLabel).getString(),
                s.getObject().asResource().getProperty(FCREPO.created).getString()));
        }
        vers.stream().forEach(out::println);

        // create transaction
        URI txUri = new URI("http://localhost:8080/fedora/rest/fcr:tx");
        resp10 = fc.post(txUri).perform();
        txUri = resp10.getLocation();

        final URI uri = new URI(txUri.toString() + "/test/8p/58/pc/92/8p58pc92q");

        // get lang and contentTypr from @Model
        final org.fcrepo.model.annotation.Model ma = Item.class.getAnnotation(org.fcrepo.model.annotation.Model.class);
        final String lang = ma.lang().getLang();
        final String contentType = ma.contentType().getContentType();
        out.println("\nlang: " + lang + " contentType: " + contentType);

        // 1. get the object
        final FcrepoResponse resp3 = fc.get(uri).accept(contentType).perform();
        out.println(">> status: " + resp3.getStatusCode() + " headers -----------------");
        final Map<String, List<String>> hd = resp3.getHeaders();
        hd.entrySet().stream().forEach(out::println);

        // 2. create model from fedora rest api response
        final Model model = ModelFactory.createDefaultModel();
        model.read(resp3.getBody(), null, lang);
        out.println(">> rdf model contents -------------------");
        model.write(out, lang);

        // 3. store data to item model object
        final Item item2 = new Item();
        final Resource r2 = model.getResource(uri.toString());
        final List<Field> fd = getInheritedFields(Item.class);
        for (final Field f : fd) {
            final org.fcrepo.model.annotation.Field fa = f.getAnnotation(org.fcrepo.model.annotation.Field.class);
            if (fa.read() && r2.hasProperty(fa.property().getProperty())) {
                f.setAccessible(true);
                final StmtIterator s = r2.listProperties(fa.property().getProperty());
                final ArrayList<String> l = new ArrayList<String>();
                while (s.hasNext()) {
                    final Statement sm = s.next();
                    l.add(sm.getString());
                }
                f.set(item2, l);
            }
        }
        out.println(">> item2 --------------");
        out.println(item2);

        // 4. update item data
        item2.setDcCreator(Arrays.asList("Charoenwattana, Piyapong", "Pisaisansuk, Sumrarn"));
        item2.setDcDate(Arrays.asList("2016-07-31"));
        item2.setDcDescription(Arrays.asList("Item description...", "Update description..."));
        item2.setDcTitle(Arrays.asList("Test transaction 2....", "title transaction..."));

        // 5. update item data model and create sqarql query
        final StringBuilder sb = new StringBuilder();
        for (final Field f : fd) {
            final org.fcrepo.model.annotation.Field fa = f.getAnnotation(org.fcrepo.model.annotation.Field.class);
            if (fa.write()) {
                sb.append("DELETE { <> <").append(fa.property().getProperty().getURI()).append("> ?o . }\n")
                    .append("  WHERE { <> <").append(fa.property().getProperty().getURI()).append("> ?o . };\n");
                r2.removeAll(fa.property().getProperty());
                f.setAccessible(true);
                final List<String> l = (List<String>) f.get(item2);
                if (l != null) {
                    for (final String s : l) {
                        sb.append("INSERT { <> <").append(fa.property().getProperty().getURI()).append("> \"").append(s)
                            .append("\"^^<").append(fa.dataType().getDatatype().getURI()).append("> . }\n")
                            .append("  WHERE { } ;\n");
                        r2.addProperty(fa.property().getProperty(), s, fa.dataType().getDatatype());
                    }
                }
            }
        }

        // 6. delete properties and insert new properties
        out.println(">> sparql --------------\n" + sb);
        FcrepoResponse resp = fc.patch(uri).body(new ByteArrayInputStream(sb.toString().getBytes())).perform();
        out.println(">> update data status: " + resp.getStatusCode() + " headers ----------------");
        resp.getHeaders().entrySet().stream().forEach(out::println);

        // 7. commit/rollback tx
        resp = fc.post(new URI(txUri.toString() + "/fcr:tx/fcr:commit")).perform();
        out.println(">> commit tx status: " + resp.getStatusCode() + " headers ----------------");
        resp.getHeaders().entrySet().stream().forEach(out::println);

        // print model
        out.println(">> rdf model updated contents -------------------");
        model.write(out, lang);

        // 8. create version using timestamp
        resp10 = fc.post(vUri).slug(ISODateTimeFormat.basicDateTime().print(new DateTime(DateTimeZone.UTC))).perform();
        out.println(">> create version status: " + resp10.getStatusCode());
        resp10.getHeaders().entrySet().stream().forEach(out::println);
    }

    /**
     * The getInheritedFields method.
     * @param cl
     * @return
     */
    public static List<Field> getInheritedFields(final Class<?> cl) {
        final List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = cl; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}
