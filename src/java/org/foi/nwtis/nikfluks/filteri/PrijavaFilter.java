package org.foi.nwtis.nikfluks.filteri;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 *
 * @author Nikola
 */
@WebFilter(filterName = "PrijavaFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class PrijavaFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*try {
            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            String reqURI = reqt.getRequestURI();
            if (reqURI.indexOf("/prijava.xhtml") >= 0
                    || (ses != null && ses.getAttribute("korisnik") != null)
                    || reqURI.indexOf("/jezik.xhtml") >= 0
                    || reqURI.indexOf("/registracija.xhtml") >= 0
                    || reqURI.contains("javax.faces.resource")) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(reqt.getContextPath() + "/faces/prijava.xhtml");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
    }

    @Override
    public void destroy() {
    }

}
