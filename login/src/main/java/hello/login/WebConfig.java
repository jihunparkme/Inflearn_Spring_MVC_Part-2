package hello.login;

import hello.login.web.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    /**
     * 필터 등록
     * Spring Boot 는 WAS 를 들고 띄우기 때문에, WAS 를 띄울 때 필터를 같이 넣어 준다.
     * @return
     */
    @Bean
    public FilterRegistrationBean logFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); //등록할 필터 설정
        filterRegistrationBean.setOrder(1); //필터 체인에서의 필터 순서
        filterRegistrationBean.addUrlPatterns("/*"); //필터를 적용할 URL Pattern 설정

        return filterRegistrationBean;
    }
}
