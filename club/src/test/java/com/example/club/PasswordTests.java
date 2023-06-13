package com.example.club;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode(){
        String pass = "1111";

        String enPw = passwordEncoder.encode(pass);

        System.out.println("enPw = " + enPw);

        boolean matchResult = passwordEncoder.matches(pass,enPw);

        System.out.println("matchResult = " + matchResult);
    }
}
