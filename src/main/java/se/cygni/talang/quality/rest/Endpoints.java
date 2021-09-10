package se.cygni.talang.quality.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Endpoints {

    record Result(String name) {
    }

    @GetMapping(path = "/result", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getResult() {
        return new Result("A great result!");
    }
}
