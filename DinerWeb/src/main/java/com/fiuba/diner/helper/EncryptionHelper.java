package com.fiuba.diner.helper;

import static com.fiuba.diner.constant.Constants.ENCRYPTION_SALT;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptionHelper {

	private EncryptionHelper() {
	}

	public static String generateHash(String str) {
		return DigestUtils.sha1Hex(ENCRYPTION_SALT + str);
	}

}