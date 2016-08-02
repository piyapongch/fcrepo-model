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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The Item class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Model /* (lang = Lang.RDFXML, contentType = ContentType.APPLICATION_RDFXML) */
public class Item extends Fedora {

    @Field(property = Property.DC_TITLE, dataType = DataType.XSD_STRING)
    private List<String> title;

    @Field(property = Property.DC_CREATOR, dataType = DataType.XSD_STRING)
    private List<String> creator;

    @Field(property = Property.DC_DESCRIPTION, dataType = DataType.XSD_STRING)
    private List<String> description;

    @Field(property = Property.DC_DATE, dataType = DataType.XSD_DATE)
    private List<String> date;

}
