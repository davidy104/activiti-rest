package nz.co.bookshop.process.util

interface RestClientAccessor {
	/**
	 * 
	 * @param path
	 * @param restClientCallback
	 * @param queryParameters StringArray [parameterName,parameterValue]
	 * @return
	 * @throws Exception
	 */
	AbstractRestClientResponse process(String path, RestClientExecuteCallback restClientCallback, int expectedStatus,String[]... queryParameters) throws Exception
}
