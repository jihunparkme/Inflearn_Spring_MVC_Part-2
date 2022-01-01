package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if (ex instanceof UserException) {
                log.info("UserException resolver to 400");
                String acceptHeader = request.getHeader("accept");
                //예외를 HTTP 상태 코드 400으로 전달
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                if ("application/json".equals(acceptHeader)) {
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());
                    String result = objectMapper.writeValueAsString(errorResult);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    //1. 빈 ModelAndView 반환 시 뷰 렌더링을 하지 않고 정상 흐름으로 서블릿 반환
                    return new ModelAndView(); //예외를 response 로 반환
                } else { //TEXT/HTML
                    //2. ModelAndView 에 View, Model 정보를 지정하여 반환하면 뷰 렌더링
                    return new ModelAndView("error/500"); //예외를 ModelAndView 로 반환
                }
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        //3. null 반환 시
        //다음 ExceptionResolver 찾아서 실행
        //처리 가능한 ExceptionResolver 가 없을 경우 기존 발생한 예외를 서블릿 밖으로 전달
        return null;
    }
}
