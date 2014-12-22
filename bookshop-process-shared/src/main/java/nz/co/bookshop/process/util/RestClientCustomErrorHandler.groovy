package nz.co.bookshop.process.util;

interface RestClientCustomErrorHandler {
	/**
	 * 
	 * @param statusCode
	 * @param responseString
	 * @return boolean if continue or stop
	 * @throws Exception
	 */
	void handle(int statusCode,String responseString) throws Exception
}
