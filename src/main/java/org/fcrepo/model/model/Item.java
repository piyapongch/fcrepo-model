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
    private List<String> dcTitle;

    @Field(property = Property.DC_CREATOR, dataType = DataType.XSD_STRING)
    private List<String> dcCreator;

    @Field(property = Property.DC_DESCRIPTION, dataType = DataType.XSD_STRING)
    private List<String> dcDescription;

    @Field(property = Property.DC_DATE, dataType = DataType.XSD_DATE)
    private List<String> dcDate;

    @Field(property = Property.DCTERMS_TITLE, dataType = DataType.XSD_STRING)
    private List<String> dctermsTitle;

    @Field(property = Property.DCTERMS_CREATOR, dataType = DataType.XSD_STRING)
    private List<String> dctermsCreator;

    @Field(property = Property.DCTERMS_DESCRIPTION, dataType = DataType.XSD_STRING)
    private List<String> dctermsDescription;

    @Field(property = Property.DCTERMS_CREATED, dataType = DataType.XSD_STRING)
    private List<String> dctermsCreated;
}
