package com.github.pirent.testsupport.txwrap;

import java.util.concurrent.Callable;

/**
 * Contract of an EJB which wraps arbitrary {@link Callable} task inside of a
 * new transaction.
 *
 */
public interface TxWrappingLocalBusiness {

	//-------------------------------------------------------------------------------------||
	// Contracts --------------------------------------------------------------------------||
	//-------------------------------------------------------------------------------------||
	
	/**
	 * Wrap the specified tasks in a new transaction
	 * 
	 * @param tasks
	 * @throws IllegalArgumentException
	 *             If no task is specified
	 * @throws TaskExecutionException
	 *             If an error occurred in invoking {@link Callable#call()}
	 */
	void wrapInTx(Callable<?>... tasks) throws IllegalArgumentException, TaskExecutionException;
	
	/**
	 * Wraps a specified task in a new transaction, return the value
	 * 
	 * @param task
	 * @return
	 * @throws IllegalArgumentException
	 *             If no task is specified
	 * @throws TaskExecutionException
	 *             If an error occurred in invoking {@link Callable#call()}
	 */
	<T> T wrapInTx(Callable<T> task) throws IllegalArgumentException, TaskExecutionException;
}
