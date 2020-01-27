class  myCustomException extends Exception{
	/**
	 * Custom Exception class inheriting from Exception class
	 */
	private static final long serialVersionUID = 1L;

	myCustomException(String message){
		super(message);													// calling super class constructor
	}	
}