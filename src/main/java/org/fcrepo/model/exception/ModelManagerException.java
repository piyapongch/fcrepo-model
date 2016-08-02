/**
 * Piyapong Charoenwattana
 * Project: fcrepo-model
 */

package org.fcrepo.model.exception;

/**
 * The ModelManagerException class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
public class ModelManagerException extends Exception {

    private static final long serialVersionUID = 1297987327796574538L;

    /**
     * The ModelManagerException class constructor.
     */
    public ModelManagerException() {
        // TODO: Implement this constructor.
    }

    /**
     * The ModelManagerException class constructor.
     * @param message
     */
    public ModelManagerException(final String message) {
        super(message);
        // TODO: Implement this constructor.
    }

    /**
     * The ModelManagerException class constructor.
     * @param cause
     */
    public ModelManagerException(final Throwable cause) {
        super(cause);
        // TODO: Implement this constructor.
    }

    /**
     * The ModelManagerException class constructor.
     * @param message
     * @param cause
     */
    public ModelManagerException(final String message, final Throwable cause) {
        super(message, cause);
        // TODO: Implement this constructor.
    }

    /**
     * The ModelManagerException class constructor.
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ModelManagerException(final String message, final Throwable cause, final boolean enableSuppression,
        final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO: Implement this constructor.
    }

}
