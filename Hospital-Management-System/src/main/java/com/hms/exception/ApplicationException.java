package com.hms.exception;

/**
 * @author Avnish Upadhyay
 */
public class ApplicationException extends Exception {
   
	private static final long serialVersionUID = 1L;

public ApplicationException(String msg){
	   super(msg);
   }
}
