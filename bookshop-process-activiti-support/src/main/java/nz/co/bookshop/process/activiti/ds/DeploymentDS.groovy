package nz.co.bookshop.process.activiti.ds;

import nz.co.bookshop.process.model.activiti.Deployment
import nz.co.bookshop.process.model.activiti.DeploymentResource

interface DeploymentDS {

	Deployment deployment(String name, String category, File uploadFile) throws Exception
	Deployment getDeployment(String name,String category) throws Exception
	Deployment getDeployment(String deploymentId) throws Exception
	void unDeployment(String deploymentId) throws Exception
	Set<DeploymentResource> getDeploymentResource(String deploymentId) throws Exception
}
