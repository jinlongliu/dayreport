package cn.cct.webapp.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 15/12/16.
 */
@Controller("loginFailureHandler")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LoginFailureHandler.class);

    private String defaultFailureUrl = "/login?error=true";

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {

        LOG.debug("User login failure");


        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + defaultFailureUrl);

    }
}
