package co.edu.uniquindio.unicine.filtro;

import co.edu.uniquindio.unicine.util.EncriptacionUtil;
import co.edu.uniquindio.unicine.util.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        response.setContentType("application/json");
        Map<String, Object> respuesta = new HashMap<>();
        String tokenFromRequest = request.getHeader("Authorization");
        String encryptedJwtToken = tokenFromRequest.substring(7);

        if(encryptedJwtToken.equals("null")){
            respuesta.put("mensaje", "No estas autorizado para hacer esta transaccion.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            String jwtToken = EncriptacionUtil.decrypt(encryptedJwtToken);
            try{
                Boolean estadoExpiracion = jwtTokenUtil.isTokenExpired(jwtToken);
                logger.info("Estado de expiracion: " + estadoExpiracion);
                respuesta.put("mensaje", "No estas autorizado para hacer esta transaccion.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }catch(ExpiredJwtException e){
                respuesta.put("mensaje", "La sesion ha expirado, vuelva a iniciar sesion");
                respuesta.put("estadoExpiracion", true);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        response.getWriter().write(convertObjectToJson(respuesta));
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AccessDeniedException accessDeniedException) throws IOException {
        logger.error("AccessDenied error: {}", accessDeniedException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        Map<String, Object> respuesta = new HashMap<>();
        String tokenFromRequest = request.getHeader("Authorization");
        String encryptedJwtToken = tokenFromRequest.substring(7);

        if(encryptedJwtToken.equals("null")){
            respuesta.put("mensaje", "No estas autorizado para hacer esta transaccion.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            String jwtToken = EncriptacionUtil.decrypt(encryptedJwtToken);
            try{
                Boolean estadoExpiracion = jwtTokenUtil.isTokenExpired(jwtToken);
                logger.info("Estado de expiracion: " + estadoExpiracion);
                respuesta.put("mensaje", "No estas autorizado para hacer esta transaccion.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }catch(ExpiredJwtException e){
                respuesta.put("mensaje", "La sesion ha expirado, vuelva a iniciar sesion");
                respuesta.put("estadoExpiracion", true);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        response.getWriter().write(convertObjectToJson(respuesta));
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
