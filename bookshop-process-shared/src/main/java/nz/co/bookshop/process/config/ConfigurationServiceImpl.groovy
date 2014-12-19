package nz.co.bookshop.process.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.google.inject.Inject
import com.google.inject.name.Named

class ConfigurationServiceImpl implements ConfigurationService{

	@Inject
	@Named("AWS.ACCESS_KEY_ID")
	String awsAccessKey

	@Inject
	@Named("AWS.SECRET_KEY")
	String awsSecretKey

	@Inject
	@Named("AWS.S3_BUCKET_NAME")
	String awsS3Bucket

	@Inject
	@Named("NEO4J.HOST_URI")
	String neo4jHostUri

	@Inject
	@Named("activiti.auth.userid")
	String activitiAuthUserId

	@Inject
	@Named("activiti.auth.password")
	String activitiAuthPassword

	@Inject
	@Named("activiti.api.baseurl")
	String activitiRestHostUri

	@Override
	public AWSCredentials getAWSCredentials() {
		return new BasicAWSCredentials(awsAccessKey, awsSecretKey)
	}

	@Override
	ActivitiRestConfig getActivitiRestConfig() {
		return new ActivitiRestConfig(authUserId:activitiAuthUserId,authPassword:activitiAuthPassword,restHostUri:activitiRestHostUri)
	}

	@Override
	public String getNeo4jRestHostUri() {
		return neo4jHostUri
	}

	@Override
	public String getAwsS3BucketName() {
		return awsS3Bucket
	}
}
