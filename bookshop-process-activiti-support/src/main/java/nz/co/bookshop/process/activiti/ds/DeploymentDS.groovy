package nz.co.bookshop.process.activiti.ds;

import nz.co.bookshop.process.activiti.model.Deployment
import nz.co.bookshop.process.activiti.model.DeploymentQueryParameter
import nz.co.bookshop.process.activiti.model.DeploymentResource
import nz.co.bookshop.process.model.Page
import nz.co.bookshop.process.model.PagingAndSortingParameter;

interface DeploymentDS {

	Deployment deployment(String name, String category, File uploadFile) throws Exception
	Deployment getDeployment(String name,String category) throws Exception
	Deployment getDeployment(String deploymentId) throws Exception
	void unDeployment(String deploymentId) throws Exception
	Set<DeploymentResource> getDeploymentResource(String deploymentId) throws Exception
	Page<Deployment> paginateDeployment(Map<DeploymentQueryParameter, String> deploymentQueryParameters,Map<PagingAndSortingParameter,String> pagingAndSortingParameters)
}
