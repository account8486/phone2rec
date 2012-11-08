package com.wirelesscity.exception;

/**
 * 
 * @version $Id: DAOException.java,v 1.1.1.1 2008-03-20 21:38:35 stefano_fornari
 *          Exp $
 */
public class ServiceException extends Exception {

	/**
	 * Creates a new instance of <code>DAOException</code> without detail
	 * message.
	 */
	public ServiceException() {

	}

	/**
	 * Constructs an instance of <code>DAOException</code> with the specified
	 * detail message.
	 * 
	 * @param msg
	 *            the detail message.
	 */
	public ServiceException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an instance of <code>DAOException</code> with the specified
	 * exception.
	 * 
	 * @param cause
	 *            Throwable
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an instance of <code>DAOException</code> with the specified
	 * detail message.
	 * 
	 * @param msg
	 *            String
	 * @param cause
	 *            Throwable
	 */
	public ServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

