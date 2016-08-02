/**
 * Piyapong Charoenwattana
 * Project: fcrepo4-oaiprovider
 */

package org.fcrepo.model.model;

import java.util.List;

import org.fcrepo.model.annotation.Field;
import org.fcrepo.model.annotation.Field.DataType;
import org.fcrepo.model.annotation.Field.Property;
import org.fcrepo.model.annotation.Model;

import lombok.Getter;
import lombok.ToString;

/**
 * The Fedora class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
@ToString
@Model
public class Fedora {

    @Getter
    @Field(property = Property.FEDORA_CREATED_BY, dataType = DataType.XSD_STRING, write = false)
    private List<String> createdBy;

    @Getter
    @Field(property = Property.FEDORA_UUID, dataType = DataType.XSD_STRING, write = false)
    private List<String> uuid;

    @Getter
    @Field(property = Property.FEDORA_LAST_MODIFIED_BY, dataType = DataType.XSD_STRING, write = false)
    private List<String> lastModifiedBy;

    @Getter
    @Field(property = Property.FEDORA_LAST_MODIFIED, dataType = DataType.XSD_DATETIME, write = false)
    private List<String> lastModified;

}
