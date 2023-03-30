package hello.hellospring.controller;

public class MemberForm {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
// name을 통해 사용자에게 입력을 받아 가입하기 위한 DTO 모델이라고 생각하면 된다.