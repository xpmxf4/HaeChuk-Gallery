package hailyounghan.board.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class PostRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isPostPath(request)) {
            long startTime = System.currentTimeMillis();
            request.setAttribute("startTime", startTime);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (isPostPath(request)) {
            long startTime = (Long) request.getAttribute("startTime");
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            log.info("[" + request.getMethod() + " " + request.getRequestURI() + "] execution Time : " + executionTime + "ms");
        }
    }

    private boolean isPostPath(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/posts")
                && (request.getMethod().equalsIgnoreCase("GET")
                || request.getMethod().equalsIgnoreCase("POST")
        );
    }
}
