package nz.co.bookshop.process;

import groovy.json.JsonSlurper;
import nz.co.bookshop.process.config.ConfigurationService;
import nz.co.bookshop.process.ds.DeploymentDS;
import nz.co.bookshop.process.ds.impl.DeploymentDSImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import com.sun.jersey.api.client.Client;

public class ActivitiSupportModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ActivitiRestClientAccessor.class).annotatedWith(Names.named("activitiRestClientAccessor")).toProvider(ActivitiRestClientAccessorProvider.class)
				.asEagerSingleton();
		bind(DeploymentDS.class).to(DeploymentDSImpl.class).asEagerSingleton();
	}

	public static class ActivitiRestClientAccessorProvider implements Provider<ActivitiRestClientAccessor> {
		@Inject
		Client jerseyClient;
		@Inject
		JsonSlurper jsonSlurper;
		@Inject
		ConfigurationService configurationService;

		@Override
		public ActivitiRestClientAccessor get() {
			return new ActivitiRestClientAccessor(jerseyClient, configurationService.getActivitiRestConfig(), jsonSlurper);
		}
	}

}
