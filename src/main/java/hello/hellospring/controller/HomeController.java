package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // http:localhost:8080/을 입력하면 home를 출력한다.
    public String home() {
        return "home";
    }
}
