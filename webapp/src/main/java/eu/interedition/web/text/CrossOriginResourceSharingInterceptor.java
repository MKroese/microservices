package eu.interedition.web.text;

import com.google.common.base.Objects;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="http://gregor.middell.net/" title="Homepage">Gregor Middell</a>
 */
public class CrossOriginResourceSharingInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", Objects.firstNonNull(request.getHeader("Access-Control-Request-Method"), "GET, POST, PUT, DELETE, HEAD, OPTIONS"));
    response.setHeader("Access-Control-Allow-Headers", Objects.firstNonNull(request.getHeader("Access-Control-Request-Headers"), "Content-Type, Accept, X-Requested-With"));
    response.setHeader("Access-Control-Max-Age", "86400");
    return !("OPTIONS".equals(request.getMethod()));
  }
}
