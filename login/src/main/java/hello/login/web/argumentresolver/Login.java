package hello.login.web.argumentresolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // annotation 을 붙일 수 있는 대상 지정
@Retention(RetentionPolicy.RUNTIME) // 어느 시점까지 어노테이션의 메모리를 가져갈 지 설정
public @interface Login {

}
