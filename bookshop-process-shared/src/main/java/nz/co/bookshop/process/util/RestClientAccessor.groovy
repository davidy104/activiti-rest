package nz.co.bookshop.process.util

interface RestClientAccessor {
	/**
	 * 
	 * @param path
	 * @param restClientCallback
	 * @return
	 * @throws Exception
	 */
	String process(String path, int expectedStatus, RestClientExecuteCallback restClientCallback,RestClientCustomErrorHandler... customErrorHandlers) throws Exception
}
