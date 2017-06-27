package com.jd.quant.core.web.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * JDQuant Core Application Bootloader
 *
 * @author Zhiguo.Chen
 */
//@EnableCircuitBreaker
@SpringBootApplication(scanBasePackages = {"com.jd.quant.core"})
public class JdquantCoreWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdquantCoreWebApplication.class, args);
    }
}
