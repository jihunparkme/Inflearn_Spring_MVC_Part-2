package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 필터 사용을 위해 필터 인터페이스 구현
 */
@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    /**
     * HTTP 요청이 오면 호출
     * - 고객의 요청 응답 정보를 한 번에 확인 가능
     * - 시간 정보를 추가해서 요청 시간 확인 및 성능 최적화 가능
     */
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        log.info("log filter doFilter");
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String requestURI = httpRequest.getRequestURI(); //요청 URI 정보
//
//        String uuid = UUID.randomUUID().toString(); //요청 구분을 위한 uuid
//
//        try {
//            log.info("REQUEST [{}][{}]", uuid, requestURI);
//            //다음 필터가 있으면 필터 호출, 필터가 없으면 서블릿 호출
//            chain.doFilter(request, response);
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            log.info("RESPONSE [{}][{}]", uuid, requestURI);
//        }
//    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UUID uniqueId = UUID.randomUUID();
        MDC.put("requestId", uniqueId.toString());
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        log.info("Request IP address is {}", httpRequest.getRemoteAddr());
        log.info("Request URI address is {}", httpRequest.getRequestURI());
        log.info("Request content type is {}", httpRequest.getContentType());
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
                httpServletResponse
        );
        filterChain.doFilter(httpRequest, responseWrapper);
        responseWrapper.setHeader("requestId", uniqueId.toString());
        responseWrapper.copyBodyToResponse();
        log.info("Response header is set with uuid {}", responseWrapper.getHeader("requestId"));
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
