package hello.login;

import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LoginCheckInterceptor;
import hello.login.web.interceptor.Loginterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 필터 등록
     * Spring Boot 는 WAS 를 들고 띄우기 때문에, WAS 를 띄울 때 필터를 같이 넣어 준다.
     * @return
     */
//    @Bean
    public FilterRegistrationBean logFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); //등록할 필터 설정
        filterRegistrationBean.setOrder(1); //필터 체인에서의 필터 순서
        filterRegistrationBean.addUrlPatterns("/*"); //필터를 적용할 URL Pattern 설정

        return filterRegistrationBean;
    }

//    @Bean
    public FilterRegistrationBean loginCheckFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); //등록할 필터 설정
        filterRegistrationBean.setOrder(2); //필터 체인에서의 필터 순서
        filterRegistrationBean.addUrlPatterns("/*"); //필터를 적용할 URL Pattern 설정

        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Loginterceptor()) //인터셉터 등록
                .order(1) //인터셉터 호출 순서
                .addPathPatterns("/**") //인터셉터 적용 URL 패턴
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 인터셉터에서 제외할 패턴

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }
}
