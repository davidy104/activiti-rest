

package nz.co.bookshop.process.test.activitids;

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.*
import groovy.util.logging.Slf4j
import nz.co.bookshop.process.ActivitiSupportModule
import nz.co.bookshop.process.SharedModule
import nz.co.bookshop.process.config.ConfigurationServiceModule
import nz.co.bookshop.process.ds.DeploymentDS
import nz.co.bookshop.process.test.GuiceJUnitRunner
import nz.co.bookshop.process.test.GuiceJUnitRunner.GuiceModules

import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

import com.google.common.io.Resources
import com.google.inject.Inject

@RunWith(GuiceJUnitRunner.class)
@GuiceModules([ConfigurationServiceModule.class, SharedModule.class, ActivitiSupportModule.class ])
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DeploymentDSIntegrationTest {

	static final String TEST_BPMN_BAR="laptopOrderHumanProcess.bar"
	static final String TEST_PROCESS_NAME="laptop"
	static final String TEST_PROCESS_CATEGORY="order"

	static File uploadBarFile
	static String testDeploymentId

	@Inject
	DeploymentDS deploymentDs

	@BeforeClass
	static void setUp(){
		uploadBarFile = new File(Resources.getResource(TEST_BPMN_BAR).file)
	}

	//	@After
	//	void tearDown(){
	//		if(testDeploymentId){
	//			deploymentDs.unDeployment(testDeploymentId)
	//		}
	//	}

	@Test
	void deployment(){
		Map<String,String> metaMap = deploymentDs.deployment("laptop", "order", uploadBarFile)
		assertThat(metaMap['tenantId'], is("laptop:order"))
		assertNotNull(metaMap['id'])
		testDeploymentId = metaMap['id']
	}
	
	@Test
	void getDeploymentById(){
		Map<String,String> resultMap = deploymentDs.getDeployment(testDeploymentId)
		assertNotNull(resultMap)
		assertThat(resultMap['tenantId'], is("laptop:order"))
	}

	@Test
	void getDeploymentByNameAndCategory(){
		Map<String,Object> resultMap = deploymentDs.getDeployment(TEST_PROCESS_NAME, TEST_PROCESS_CATEGORY)
		log.info "resultMap:{} $resultMap"
		assertNotNull(resultMap)
		Map<String,String> dataMap = ((List)resultMap['data']).first()
		assertThat(dataMap['tenantId'], is("laptop:order"))
	}
	
	@Test
	void getDeploymentResources(){
		List<Map<String,Object>> deployResources = deploymentDs.getDeploymentResource(testDeploymentId)
		println "deployResources:{} $deployResources"
	}

	@Test
	void undeployment(){
		println "testDeploymentId:{} $testDeploymentId"
		deploymentDs.unDeployment(testDeploymentId)
	}
}
