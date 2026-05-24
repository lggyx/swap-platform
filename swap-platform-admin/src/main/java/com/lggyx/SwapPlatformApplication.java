package com.lggyx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SwapPlatformApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("..")
                .load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(SwapPlatformApplication.class, args);
    }

}
