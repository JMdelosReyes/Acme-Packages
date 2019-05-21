/*
 * HashPassword.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class HashPasswordParameter {

	public static String generateHashPassword(final String pass) {
		final Md5PasswordEncoder encoder;
		final String hash;
		encoder = new Md5PasswordEncoder();
		hash = encoder.encodePassword(pass, null);
		return hash;

	}

}