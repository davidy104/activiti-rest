package nz.co.bookshop.process.activiti;

import nz.co.bookshop.process.activiti.convert.DeploymentConverter;
import nz.co.bookshop.process.activiti.convert.component.DeploymentMapToModel;
import nz.co.bookshop.process.activiti.convert.component.DeploymentResourceMapToModel;
import nz.co.bookshop.process.activiti.ds.DeploymentDS;
import nz.co.bookshop.process.activiti.ds.impl.DeploymentDSImpl;
import nz.co.bookshop.process.config.ActivitiRestConfig;
import nz.co.bookshop.process.config.ConfigurationService;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class ActivitiSupportModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ActivitiRestClientAccessor.class).annotatedWith(Names.named("activitiRestClientAccessor")).toProvider(ActivitiRestClientAccessorProvider.class)
				.asEagerSingleton();
		bind(DeploymentConverter.class).asEagerSingleton();
		bind(DeploymentDS.class).to(DeploymentDSImpl.class).asEagerSingleton();
	}

	public static class ActivitiRestClientAccessorProvider implements Provider<ActivitiRestClientAccessor> {
		@Inject
		Client jerseyClient;
		@Inject
		ConfigurationService configurationService;

		@Override
		public ActivitiRestClientAccessor get() {
			ActivitiRestConfig activitiRestConfig = configurationService.getActivitiRestConfig();
			jerseyClient.addFilter(new HTTPBasicAuthFilter(activitiRestConfig.getAuthUserId(), activitiRestConfig.getAuthPassword()));
			return new ActivitiRestClientAccessor(jerseyClient, activitiRestConfig.getRestHostUri());
		}
	}

	@Provides
	@Singleton
	@Named("deploymentMapToModel")
	public DeploymentMapToModel deploymentMapToModel() {
		return new DeploymentMapToModel();
	}

	@Provides
	@Singleton
	@Named("deploymentResourceMapToModel")
	public DeploymentResourceMapToModel deploymentResourceMapToModel() {
		return new DeploymentResourceMapToModel();
	}

}
