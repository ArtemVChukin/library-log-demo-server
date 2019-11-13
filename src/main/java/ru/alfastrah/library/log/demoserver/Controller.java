package ru.alfastrah.library.log.demoserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {
    private final SomeService someService;

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @PostMapping("/string")
    public String string(String param, @RequestBody String body) {
        return param + " " + body;
    }

    @PostMapping("/holder")
    public Holder holder(String param, @RequestBody Holder body) {
        return new Holder(param + " " + body.getValue());
    }

    @PostMapping("/service")
    public Holder service(String param, @RequestBody Holder body) {
        return someService.transform(new Holder(param + " " + body.getValue()));
    }
}
