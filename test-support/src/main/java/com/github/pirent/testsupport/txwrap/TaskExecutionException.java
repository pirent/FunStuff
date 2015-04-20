package com.github.pirent.testsupport.txwrap;

import javax.ejb.ApplicationException;

/**
 * Indicates that an exception has occurred while submitting a task to
 * {@link TxWrappingLocalBusiness#wrapInTx(java.util.concurrent.Callable)
 *
 */
@ApplicationException(rollback=true)
public class TaskExecutionException extends Exception {

	//-------------------------------------------------------------------------------------||
	// Class members ----------------------------------------------------------------------||
	//-------------------------------------------------------------------------------------||
	
	private static final long serialVersionUID = 1L;
	
	//-------------------------------------------------------------------------------------||
	// Constructors -----------------------------------------------------------------------||
	//-------------------------------------------------------------------------------------||
	
	/**
	 * Create a new instance with the specific root cause.
	 */
	public TaskExecutionException(final Throwable t) {
		super(t);
	}
	
	/**
	 * Create a new instance with a specific message.
	 */
	public TaskExecutionException(final String message) {
		super(message);
	}
}
