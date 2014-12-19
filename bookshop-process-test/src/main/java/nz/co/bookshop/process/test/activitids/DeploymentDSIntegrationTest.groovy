

package nz.co.bookshop.process.test.activitids;

import static org.junit.Assert.*
import groovy.util.logging.Slf4j
import nz.co.bookshop.process.ActivitiSupportModule
import nz.co.bookshop.process.SharedModule
import nz.co.bookshop.process.config.ConfigurationServiceModule
import nz.co.bookshop.process.ds.DeploymentDS
import nz.co.bookshop.process.test.GuiceJUnitRunner
import nz.co.bookshop.process.test.GuiceJUnitRunner.GuiceModules

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import com.google.common.io.Resources
import com.google.inject.Inject

@RunWith(GuiceJUnitRunner.class)
@GuiceModules([ConfigurationServiceModule.class, SharedModule.class, ActivitiSupportModule.class ])
@Slf4j
class DeploymentDSIntegrationTest {

	static final String TEST_BPMN_BAR="laptopOrderHumanProcess.bar"
	static final String TEST_PROCESS_NAME="laptop"
	static final String TEST_PROCESS_CATEGORY="order"

	File uploadBarFile
	String testDeploymentId

	@Inject
	DeploymentDS deploymentDs

	@Before
	void setUp(){
		uploadBarFile = new File(Resources.getResource(TEST_BPMN_BAR).file)
		Map<String,String> metaMap = deploymentDs.deployment("laptop", "order", uploadBarFile)
		log.info "metaMap:{} $metaMap"
		testDeploymentId = metaMap['id']
	}

	@After
	void tearDown(){
		if(testDeploymentId){
			deploymentDs.unDeployment(testDeploymentId)
		}
	}

	@Test
	void getDeploymentByNameAndCategory(){
		Map<String,String> resultMap = deploymentDs.getDeployment(TEST_PROCESS_NAME, TEST_PROCESS_CATEGORY)
		assertNotNull(resultMap)
		log.info "resultMap:{} $resultMap"
	}
}
