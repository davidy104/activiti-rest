package nz.co.bookshop.process.ds;

interface DeploymentDS {

	Map deployment(String name, String category, File uploadFile) throws Exception
	Map getDeployment(String name,String category) throws Exception
	Map getDeployment(String deploymentId) throws Exception
	void unDeployment(String deploymentId) throws Exception
	List getDeploymentResource(String deploymentId) throws Exception
}
