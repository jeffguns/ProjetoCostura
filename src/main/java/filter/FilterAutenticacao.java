package filter;


import costurautil.CosturaUtil;
import entidades.Pessoa;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(urlPatterns = { "/*" })
public class FilterAutenticacao implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        CosturaUtil.getEntityManager();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

        String url = req.getServletPath();

        if (!url.equalsIgnoreCase("/index.xhtml") && usuarioLogado == null){
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/index.xhtml");
            dispatcher.forward(servletRequest,servletResponse);
            return ;
        }else {
            chain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
