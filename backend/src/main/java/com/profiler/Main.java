package com.profiler;

import com.profiler.customer.Customer;
import com.profiler.customer.CustomerRepository;
import com.profiler.customer.Gender;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.profiler.s3.S3Buckets;
import com.profiler.s3.S3Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder ,
            S3Service s3Service, S3Buckets s3Buckets) {
        return args -> {
            //createRandomCustomer(customerRepository, passwordEncoder);
            s3Service.putObject(
                    s3Buckets.getCustomer(),
                    "testKey",
                    "helloWorld".getBytes()
            );

            byte[] obj = s3Service.getObject(
                    s3Buckets.getCustomer(),
                     "testKey"
            );
            System.out.println("FINALLLLY : " + new String(obj));
        };
    }

    private void createRandomCustomer(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        var faker = new Faker();
        Random random = new Random();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        int age = random.nextInt(16, 99);
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com";
        Customer customer = new Customer(
                firstName +  " " + lastName,
                email,
                passwordEncoder.encode("password"),
                age,
                gender);
        customerRepository.save(customer);
        System.out.println(email);
    }

}
