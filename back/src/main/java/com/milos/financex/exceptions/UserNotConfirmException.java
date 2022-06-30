package com.milos.financex.exceptions;

public class UserNotConfirmException extends Exception{
	private static final long serialVersionUID = 1L;

	@Override
	  public String getMessage(){
	    return "Usuário precisa ser verificado";
	 }
}
