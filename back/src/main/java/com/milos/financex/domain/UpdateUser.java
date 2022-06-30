package com.milos.financex.domain;

import lombok.Data;

@Data
public class UpdateUser {
	private TokenData token;
	private String phone;
}
