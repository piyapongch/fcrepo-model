/**
 * Piyapong Charoenwattana
 * Project: fcrepo-model
 */

package org.fcrepo.model.persistent;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

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

    private final String protocol;
    private final String host;
    private final int port;
    private final String path;
    private final String url;
    private final String lang;
    private final String contentType;
    private final FcrepoClient client;

    /**
     * The ModelManagerImpl class constructor.
     */
    public ModelManagerImpl(final String protocol, final String host, final int port, final String path,
        final String username, final String password) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
        this.url =
            new StringJoiner("://", ":", "").add(protocol).add(host).add(Integer.toString(port)).add(path).toString();
        final org.fcrepo.model.annotation.Model ma =
            Fedora.class.getAnnotation(org.fcrepo.model.annotation.Model.class);
        this.lang = ma.lang().getLang();
        this.contentType = ma.contentType().getContentType();
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
            final T fm = t.newInstance();
            final FcrepoResponse resp = client.get(new URI(uri)).accept(contentType).perform();
            final Model rm = ModelFactory.createDefaultModel();
            rm.read(resp.getBody(), null, lang);
            final Resource res = rm.getResource(uri);
            final List<Field> fd = getInheritedFields(fm.getClass());
            for (final Field f : fd) {
                f.setAccessible(true);
                final org.fcrepo.model.annotation.Field fa = f.getAnnotation(org.fcrepo.model.annotation.Field.class);
                if (res.hasProperty(fa.property().getProperty())) {
                    final StmtIterator s = res.listProperties(fa.property().getProperty());
                    final ArrayList<String> l = new ArrayList<String>();
                    while (s.hasNext()) {
                        final Statement sm = s.next();
                        l.add(sm.getString());
                    }
                    f.set(fm, l);
                }
            }
            return fm;
        } catch (final Exception e) {
            throw new ModelManagerException(e);
        }
    }

    /**
     *
     * @see org.fcrepo.model.persistent.ModelManager#save(org.fcrepo.model.model.Fedora)
     */
    @Override
    public String save(final Fedora model) throws ModelManagerException {
        // TODO: Implement this method.
        return null;
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
     * @param cl
     * @return
     */
    private static List<Field> getInheritedFields(final Class<?> cl) {
        final List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = cl; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}
