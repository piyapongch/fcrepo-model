/**
 * Piyapong Charoenwattana
 * Project: fcrepo-model
 */

package org.fcrepo.model.persistent;

import static org.slf4j.LoggerFactory.getLogger;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The AbstractFedoraModelIT class.
 *
 * @author <a href="mailto:piyapongch@gmail.com">Piyapong Charoenwattana</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test/test-container.xml")
public abstract class AbstractFedoraModelIT {

    protected Logger logger;

    protected ModelManagerFactory mmf;

    @Before
    public void setLogger() {
        logger = getLogger(this.getClass());
    }

    /**
     * The setUp method.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        mmf = new ModelManagerFactory("http", "localhost", 8080, "/fedora/rest/test");
    }

    /**
     * The AbstractFedoraModelIT class constructor.
     */
    public AbstractFedoraModelIT() {
        // TODO: Implement this constructor.
    }

}
