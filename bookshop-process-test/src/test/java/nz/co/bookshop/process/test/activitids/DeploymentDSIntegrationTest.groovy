

package nz.co.bookshop.process.test.activitids;

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.*
import groovy.util.logging.Slf4j
import nz.co.bookshop.process.SharedModule
import nz.co.bookshop.process.activiti.ActivitiSupportModule
import nz.co.bookshop.process.activiti.ds.DeploymentDS
import nz.co.bookshop.process.activiti.model.Deployment
import nz.co.bookshop.process.activiti.model.DeploymentQueryParameter
import nz.co.bookshop.process.activiti.model.DeploymentResource
import nz.co.bookshop.process.config.ConfigurationServiceModule
import nz.co.bookshop.process.model.Page
import nz.co.bookshop.process.test.GuiceJUnitRunner
import nz.co.bookshop.process.test.GuiceJUnitRunner.GuiceModules

import org.junit.Assume
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
		Deployment deployment = deploymentDs.deployment("laptop", "order", uploadBarFile)
		assertThat(deployment.tenantId, is("laptop:order"))
		assertNotNull(deployment.id)
		testDeploymentId = deployment.id
	}

	@Test
	void getDeploymentById(){
		Assume.assumeNotNull(testDeploymentId)
		Deployment foundDeploy = deploymentDs.getDeployment(testDeploymentId)
		assertNotNull(foundDeploy)
		assertThat(foundDeploy.tenantId, is("laptop:order"))
	}

	@Test
	void getDeploymentByNameAndCategory(){
		Assume.assumeNotNull(testDeploymentId)
		Deployment foundDeploy = deploymentDs.getDeployment(TEST_PROCESS_NAME, TEST_PROCESS_CATEGORY)
		log.info "foundDeploy:{} $foundDeploy"
		assertNotNull(foundDeploy)
		assertThat(foundDeploy.tenantId, is("laptop:order"))
	}

	@Test
	void getDeploymentResources(){
		Assume.assumeNotNull(testDeploymentId)
		Set<DeploymentResource> deployResources = deploymentDs.getDeploymentResource(testDeploymentId)
		assertNotNull(deployResources)
		println "deployResources:{} $deployResources"
	}

	@Test
	void paginateDeployment(){
		Assume.assumeNotNull(testDeploymentId)
		Map<DeploymentQueryParameter, String> deploymentQueryParameters =[:]
		deploymentQueryParameters.put(DeploymentQueryParameter.nameLike,"laptop")
		Page<Deployment> page = deploymentDs.paginateDeployment(deploymentQueryParameters, null)
		println "page:{} $page"
	}

	@Test
	void undeployment(){
		Assume.assumeNotNull(testDeploymentId)
		println "testDeploymentId:{} $testDeploymentId"
		deploymentDs.unDeployment(testDeploymentId)
	}
}
