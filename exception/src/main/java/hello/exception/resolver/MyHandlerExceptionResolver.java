package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if (ex instanceof IllegalArgumentException) { //예외를 HTTP 상태 코드 400으로 전달
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                return new ModelAndView(); //빈 ModelAndView 반환 시 뷰 렌더링을 하지 않고 정상 흐름으로 서블릿 반환
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        //다음 ExceptionResolver 찾아서 실행. 처리 가능한 ExceptionResolver 가 없을 경우 기존 발생한 예외를 서블릿 밖으로 전달
        return null;
    }
}
