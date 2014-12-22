package nz.co.bookshop.process.activiti

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static nz.co.bookshop.process.util.JerseyClientUtil.getResponsePayload
import groovy.util.logging.Slf4j
import nz.co.bookshop.process.util.GeneralRestClientAccessor
import nz.co.bookshop.process.util.RestClientCustomErrorHandler
import nz.co.bookshop.process.util.RestClientExecuteCallback

import com.sun.jersey.api.client.Client

@Slf4j
class ActivitiRestClientAccessor extends GeneralRestClientAccessor {

	ActivitiRestClientAccessor(Client jerseyClient,final String actvitiRestHostUri) {
		super(jerseyClient,actvitiRestHostUri)
	}
}
