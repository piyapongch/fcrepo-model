/**
 * Piyapong Charoenwattana
 * Project: fcrepo-model
 */

package org.fcrepo.model.persistent;

import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * The ModelManagerFactory class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
@AllArgsConstructor
@Setter
public class ModelManagerFactory {

    private String protocol;

    private String host;

    private int port;

    private String path;

    public ModelManager createModelManager(final String username, final String password) {
        return new ModelManagerImpl(protocol, host, port, path, username, password);
    }
}
