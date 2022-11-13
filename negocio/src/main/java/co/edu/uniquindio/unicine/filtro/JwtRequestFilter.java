package co.edu.uniquindio.unicine.filtro;

import co.edu.uniquindio.unicine.servicios.JwtUserDetailsServicio;
import co.edu.uniquindio.unicine.util.EncriptacionUtil;
import co.edu.uniquindio.unicine.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    JwtUserDetailsServicio jwtUserDetailsServicio;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String tokenFromRequest = request.getHeader("Authorization");
        String userName = null;
        String encryptedJwtToken = null;
        String jwtToken = null;

        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if(tokenFromRequest != null && tokenFromRequest.startsWith("Bearer ")) {
            encryptedJwtToken = tokenFromRequest.substring(7);
            jwtToken = EncriptacionUtil.decrypt(encryptedJwtToken);
            try {
                userName = jwtTokenUtil.getUserNameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("No se ha podido obtener el Token");
            } catch (ExpiredJwtException e) {
                logger.error("El Token ha expirado");
            }
        }else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        //  Once we get the token validate it and extract username(principal/subject) from it.
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsServicio.loadUserByUsername(userName);

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}