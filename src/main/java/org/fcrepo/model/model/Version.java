/**
 * Piyapong Charoenwattana
 * Project: fcrepo-model
 */

package org.fcrepo.model.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Version class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
@Data
@AllArgsConstructor
public class Version {

    public String uri;

    public String hasVersionLabel;

    public String created;

}
