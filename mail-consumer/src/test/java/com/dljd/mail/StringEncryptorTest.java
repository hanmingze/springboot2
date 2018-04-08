package com.dljd.mail;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringEncryptorTest {

	@Autowired
	StringEncryptor stringEncryptor;

	@Test
	public void encryptPwd() {
		String result = stringEncryptor.encrypt("Fr43edcv");
		System.out.println(result);



//		String pwd = stringEncryptor.decrypt("nx2JPzxMBfbciDGlwlCTtw==");
//		System.out.println(pwd);

		String pwd1 = stringEncryptor.decrypt("lymnUXjEjqVZ08YjjjSyrKkuRJphi+dB");
		System.out.println(pwd1);



	}

}