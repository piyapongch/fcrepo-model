/**
 * Piyapong Charoenwattana
 * Project: fcrepo4-oaiprovider
 */

package org.fcrepo.model.persistent;

import java.util.List;

import org.fcrepo.model.exception.ModelManagerException;
import org.fcrepo.model.model.Fedora;
import org.fcrepo.model.model.Version;

/**
 * The ModelManager class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
public interface ModelManager {

    public void begin() throws ModelManagerException;

    public void commit() throws ModelManagerException;

    public void roolback() throws ModelManagerException;

    public <T extends Fedora> T find(Class<T> t, String uri) throws ModelManagerException;

    public String save(String path, Fedora model, String name) throws ModelManagerException;

    public void delete(String uri) throws ModelManagerException;

    public String version(String uri) throws ModelManagerException;

    public List<Version> versions(String uri) throws ModelManagerException;

    public void reverse(String uri) throws ModelManagerException;

    public void deleteVersion(String uri) throws ModelManagerException;
}
