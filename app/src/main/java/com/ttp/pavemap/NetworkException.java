package com.ttp.pavemap;


public class NetworkException extends Exception{
	
	public static final int CODE_NETWORK_DISABLE = 1;
	public static final int CODE_IO_EXCEPTION = 2;
	public static final int CODE_UNSUPPORTED_ENCODING = 4;
	public static final int CODE_PROTOCOL_ERROR = 5;
	public static final int CODE_UNKNOWN_ERROR = 6;
	
	public static final int CODE_BAD_REQUEST = 400;
	public static final int CODE_NOT_FOUND = 404;
	public static final int CODE_METHOD_NOT_ALLOWED = 405;
	public static final int CODE_INTERNAL_SERVER_ERROR = 500;
	public static final int CODE_REQUEST_TIMEOUT = 408;
	public static final int CODE_NETWORK_READ_TIMEOUT_ERROR = 598;
	public static final int CODE_NETWORK_CONNECT_TIMEOUT_ERROR = 599;
	public static final int CODE_BAD_GATEWAY = 502;
	public static final int CODE_SERVICE_UNAVAILABLE = 503;
	public static final int CODE_GATEWAY_TIMEOUT = 504;
	
	/**
	 */
	private static final long serialVersionUID = 1909L;

	public static final int NETWORK_TIMEOUT = 1; 
	
	private int _code;
	private String _cachedData;
	
	public NetworkException(int code, String message){
		super(message);
		_cachedData = "";
		_code = code;
	}
	
	public int getCode(){
		return _code;
	}
	
	public void setCachedData(String cachedData){
		_cachedData = cachedData;
	}
	
	public String getCachedData(){
		return _cachedData;
	}
}
