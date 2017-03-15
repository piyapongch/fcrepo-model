/**
 * Piyapong Charoenwattana
 * Project: fcrepo-model
 */

package org.fcrepo.model.persistent;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.fcrepo.client.FcrepoClient;
import org.fcrepo.client.FcrepoResponse;
import org.fcrepo.model.exception.ModelManagerException;
import org.fcrepo.model.model.Fedora;
import org.fcrepo.model.model.Version;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * The ModelManagerImpl class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
public class ModelManagerImpl implements ModelManager {

    private final String url;
    private final String lang;
    private final String contentType;
    private final FcrepoClient client;

    /**
     * The ModelManagerImpl class constructor.
     */
    public ModelManagerImpl(final String protocol, final String host, final int port, final String path,
        final String username, final String password) {
        this.url = new StringBuilder().append(protocol).append("://").append(host).append(":")
            .append(Integer.toString(port)).append(path).toString();
        final org.fcrepo.model.annotation.Model an =
            Fedora.class.getAnnotation(org.fcrepo.model.annotation.Model.class);
        this.lang = an.lang().getLang();
        this.contentType = an.contentType().getContentType();
        client =
            FcrepoClient.client().authScope(host).credentials(username, password).throwExceptionOnFailure().build();
    }

    /**
     *
     * @see org.fcrepo.model.persistent.ModelManager#begin()
     */
    @Override
    public void begin() throws ModelManagerException {
        // TODO: Implement this method.

    }

    /**
     *
     * @see org.fcrepo.model.persistent.ModelManager#commit()
     */
    @Override
    public void commit() throws ModelManagerException {
        // TODO: Implement this method.

    }

    /**
     *
     * @see org.fcrepo.model.persistent.ModelManager#roolback()
     */
    @Override
    public void roolback() throws ModelManagerException {
        // TODO: Implement this method.

    }

    /**
     *
     * @see org.fcrepo.model.persistent.ModelManager#find(java.lang.Class, java.lang.String)
     */
    @Override
    public <T extends Fedora> T find(final Class<T> t, final String uri) throws ModelManagerException {
        try {
            final T fedora = t.newInstance();
            final FcrepoResponse resp = client.get(new URI(uri)).accept(contentType).perform();
            final Model model = ModelFactory.createDefaultModel();
            model.read(resp.getBody(), null, lang);
            final Resource res = model.getResource(uri);
            final List<Field> fa = getInheritedFields(fedora.getClass());
            for (final Field f : fa) {
                f.setAccessible(true);
                final org.fcrepo.model.annotation.Field an = f.getAnnotation(org.fcrepo.model.annotation.Field.class);
                if (res.hasProperty(an.property().getProperty())) {
                    final StmtIterator s = res.listProperties(an.property().getProperty());
                    final ArrayList<String> l = new ArrayList<>();
                    while (s.hasNext()) {
                        final Statement sm = s.next();
                        l.add(sm.getString());
                    }
                    f.set(fedora, l);
                }
            }
            return fedora;
        } catch (final Exception e) {
            throw new ModelManagerException(e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.fcrepo.model.persistent.ModelManager#save(java.lang.String, org.fcrepo.model.model.Fedora)
     */
    @SuppressWarnings("unchecked")
    @Override
    public String save(final String path, final Fedora fedora, final String name) throws ModelManagerException {
        try {
            final Model model = ModelFactory.createDefaultModel();
            final Resource res = model.getResource("");
            final Field[] fa = fedora.getClass().getDeclaredFields();
            for (final Field f : fa) {
                f.setAccessible(true);
                final org.fcrepo.model.annotation.Field an = f.getAnnotation(org.fcrepo.model.annotation.Field.class);
                final List<String> l = (List<String>) f.get(fedora);
                if (l != null) {
                    for (final String s : l) {
                        res.addProperty(an.property().getProperty(), s, an.dataType().getDatatype());
                    }
                }
            }
            final ByteArrayOutputStream bo = new ByteArrayOutputStream();
            model.write(bo, lang);
            final FcrepoResponse resp = client.post(new URI(url + path))
                .body(new ByteArrayInputStream(bo.toByteArray()), contentType).slug(name).perform();
            return resp.getLocation().toString();
        } catch (final Exception e) {
            throw new ModelManagerException(e);
        }
    }

    /**
     *
     * @see org.fcrepo.model.persistent.ModelManager#delete(java.lang.String)
     */
    @Override
    public void delete(final String uri) throws ModelManagerException {
        // TODO: Implement this method.

    }

    /**
     *
     * @see org.fcrepo.model.persistent.ModelManager#version(java.lang.String)
     */
    @Override
    public String version(final String uri) throws ModelManagerException {
        // TODO: Implement this method.
        return null;
    }

    /**
     *
     * @see org.fcrepo.model.persistent.ModelManager#versions(java.lang.String)
     */
    @Override
    public List<Version> versions(final String uri) throws ModelManagerException {
        // TODO: Implement this method.
        return null;
    }

    /**
     *
     * @see org.fcrepo.model.persistent.ModelManager#reverse(java.lang.String)
     */
    @Override
    public void reverse(final String uri) throws ModelManagerException {
        // TODO: Implement this method.

    }

    /**
     *
     * @see org.fcrepo.model.persistent.ModelManager#deleteVersion(java.lang.String)
     */
    @Override
    public void deleteVersion(final String uri) throws ModelManagerException {
        // TODO: Implement this method.

    }

    /**
     * The getInheritedFields method.
     *
     * @param cl
     * @return
     */
    private static List<Field> getInheritedFields(final Class<?> cl) {
        final List<Field> fields = new ArrayList<>();
        for (Class<?> c = cl; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

}
