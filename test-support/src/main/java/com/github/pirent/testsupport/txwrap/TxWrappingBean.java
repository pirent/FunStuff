package com.github.pirent.testsupport.txwrap;

import java.util.concurrent.Callable;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * EJB wraps a series of {@link Callable} tasks within the context of a new
 * Transaction.
 * <p>
 * Default transaction type is {@link TransactionAttributeType#REQUIRES_NEW}. 
 */
@Stateless
@Local(TxWrappingLocalBusiness.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TxWrappingBean implements TxWrappingLocalBusiness {

	//-------------------------------------------------------------------------------------||
	// Required Implementation ------------------------------------------------------------||
	//-------------------------------------------------------------------------------------||

	@Override
	public void wrapInTx(Callable<?>... tasks) throws IllegalArgumentException,
			TaskExecutionException
	{
		// Precondition check
		if (tasks == null)
		{
			throw new IllegalArgumentException("Tasks must be specified");
		}

		// Delegate each task to be executed in a new transaction
		for (Callable<?> task : tasks)
		{
			wrapInTx(task);
		}

	}

	@Override
	public <T> T wrapInTx(Callable<T> task) throws IllegalArgumentException,
			TaskExecutionException
	{
		try
		{
			return task.call();
		}
		/*
		 * Every problem is encountered here becomes an ApplicationExecution to
		 * be unwrapped later by the caller
		 */
		catch (Throwable t)
		{
			throw new TaskExecutionException(t);
		}
	}

}
