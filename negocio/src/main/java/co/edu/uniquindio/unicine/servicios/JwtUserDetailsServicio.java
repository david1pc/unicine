package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Administrador;
import co.edu.uniquindio.unicine.entidades.AdministradorTeatro;
import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.repo.AdministradorRepo;
import co.edu.uniquindio.unicine.repo.AdministradorTeatroRepo;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Transactional
@Service
public class JwtUserDetailsServicio implements UserDetailsService {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private ClienteRepo clienteRepo;

    @Autowired
    private AdministradorRepo administradorRepo;

    @Autowired
    private AdministradorTeatroRepo administradorTeatroRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Cliente cliente = null;
        AdministradorTeatro administradorTeatro = null;
        Administrador administrador = null;

        cliente = this.clienteRepo.findByUsername(username).orElse(null);

        if(cliente == null){
            administrador = this.administradorRepo.findByUsername(username).orElse(null);
        }

        if(cliente == null && administrador == null){
            administradorTeatro = this.administradorTeatroRepo.findByUsername(username).orElse(null);
        }

        try {
            User user = asignarUser(cliente, administrador, administradorTeatro);
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private User asignarUser(Cliente cliente, Administrador administrador, AdministradorTeatro administradorTeatro) throws Exception {
        if(cliente != null){
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(cliente.getRol().getNombre()));
            return new org.springframework.security.core.userdetails.User(cliente.getUsername(), cliente.getPassword(), authorities);
        }else if(administrador != null){
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(administrador.getRol().getNombre()));
            return new org.springframework.security.core.userdetails.User(administrador.getUsername(), administrador.getPassword(), authorities);
        }else if(administradorTeatro != null){
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(administradorTeatro.getRol().getNombre()));
            return new org.springframework.security.core.userdetails.User(administradorTeatro.getUsername(), administradorTeatro.getPassword(), authorities);
        }else{
            throw new Exception("El username o contraseña es incorrecto");
        }
    }
}
