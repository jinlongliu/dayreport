package cn.cct.webapp.filter;

import cn.cct.model.User;
import cn.cct.service.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;


/**
 * Created by admin on 15/12/16.
 */
@Controller("loginSuccessHandler")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LoginSuccessHandler.class);

    private Map<String, String> map;

    @Autowired
    private UserManager userManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        LOG.debug("User login success");
        User user = (User) authentication.getPrincipal();
        if(user != null){
            httpServletRequest.getSession().setAttribute("loginUserId", user.getId());
        }
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/home");
    }

    public void setLastLoginTime(HttpServletRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        LOG.debug(user.toString());
        LOG.debug(user.toString() + "last login time is :" );
    }



}
