package com.alloymobile.product;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ProductApplicationTests {

    @Test
    void contextLoads() {
        List<String> hello = new ArrayList<>();
        hello.add("first");hello.add("second");hello.add("third");
        Flux<String> flux = Flux.fromIterable(hello);
        flux.flatMap(f-> {
            System.out.println(f);
            return Flux.empty();
        }).subscribe();
    }

}
