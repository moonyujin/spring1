package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // Controller는 화면과 비즈니스 로직을 연결시키는 다리 역할을 한다.
public class HelloController {
    @GetMapping("hello") // GetMapping은 요청 url에 대한 GET 요청을 메소드와 매핑시키는 것이다.
    public String hello(Model model) {
        model.addAttribute("data","hello!!");
//        model.addAttribute은  value를 추가한다. value의 패키지 이름을
//        제외한 단순 클래스 이름을 모델 이름으로 사용한다. 이 때 첫 글자는 소문자로 처리한다.
        return "hello"; // hello를 반환한다.
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
//        RequestParam은 HTTP 요청 파라미터를 컨트롤러 메소드 파라미터로 전달받을 때 사용한다.
//        HTTP는 네트워크 상에서 하이퍼텍스트를 주고 받기 위해 정의된 통신 규약이다.
//        하이퍼텍스트는 인터넷 상에서 서로 연결될 수 있는 형태를 지닌 문서
        model.addAttribute("name", name);
        return "hello-template"; // hello-template를 반환한다.
    }

    @GetMapping("hello-string")
    @ResponseBody
//    @ResponseBody란 자바객체를 JSON 형태로 변환해서 HTTP BODY에 담는 어노테이션이다.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // "hello spring"
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {

            return name;
        }

        public void setName(String name) {

            this.name = name;
        }
    }
 }
