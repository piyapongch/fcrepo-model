/**
 * Piyapong Charoenwattana
 * Project: fcrepo-model
 */

package org.fcrepo.model.persistent;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.fcrepo.model.exception.ModelManagerException;
import org.fcrepo.model.model.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The ModelManagerImplIT class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
public class ModelManagerImplIT extends AbstractFedoraModelIT {

    private ModelManagerFactory mmf;

    /**
     * The setUp method.
     *
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        mmf = new ModelManagerFactory("http", HOSTNAME, SERVER_PORT, "/");
    }

    /**
     * The tearDown method.
     *
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#ModelManagerImpl()}.
     */
    @Test
    public void testModelManagerImpl() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#begin()}.
     */
    @Test
    public void testBegin() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#commit()}.
     */
    @Test
    public void testCommit() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#roolback()}.
     */
    @Test
    public void testRoolback() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#find(java.lang.Class, java.lang.String)}.
     *
     * @throws ModelManagerException
     */
    @Test
    public void testFind() throws ModelManagerException {
        final ModelManager mm = mmf.createModelManager("fedoraAdmin", "fedoraAdmin");
        final Item item = mm.find(Item.class,
            "http://localhost:8080/fedora/rest/test/fd/33/27/4c/fd33274c-b478-4612-a986-c003eca82fb9");
        // "http://localhost:8080/fedora/rest/dev/6w/92/4c/95/6w924c95q"
        logger.info(item.toString());
        assertNotNull(item);
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#save(org.fcrepo.model.model.Fedora)}.
     */
    @Test
    public void testSaveCreate() throws ModelManagerException {
        final ModelManager mm = mmf.createModelManager("fedoraAdmin", "fedoraAdmin");
        final Item item = new Item();
        item.setDcTitle(Arrays.asList("Test item"));
        final String uri = mm.save(item);
        logger.info("uri: " + uri);
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#save(org.fcrepo.model.model.Fedora)}.
     */
    @Test
    public void testSaveUpdate() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#delete(java.lang.String)}.
     */
    @Test
    public void testDelete() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#version(java.lang.String)}.
     */
    @Test
    public void testVersion() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#versions(java.lang.String)}.
     */
    @Test
    public void testVersions() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#reverse(java.lang.String)}.
     */
    @Test
    public void testReverse() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.fcrepo.model.persistent.ModelManagerImpl#deleteVersion(java.lang.String)}.
     */
    @Test
    public void testDeleteVersion() {
        fail("Not yet implemented");
    }

}
