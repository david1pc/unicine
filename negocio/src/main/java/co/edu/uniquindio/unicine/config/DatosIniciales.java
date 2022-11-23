package co.edu.uniquindio.unicine.config;

import co.edu.uniquindio.unicine.entidades.Administrador;
import co.edu.uniquindio.unicine.entidades.AdministradorTeatro;
import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Rol;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatosIniciales implements CommandLineRunner {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private AdminServicio adminServicio;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Override
    public void run(String... args) throws Exception {

        List<Ciudad> ciudades = adminServicio.obtener_ciudades();

        if(ciudades.isEmpty()){
            Rol rol_admin = adminServicio.crearRol(new Rol("ROLE_ADMIN"));
            Rol rol_admin_teatro = adminServicio.crearRol(new Rol("ROLE_ADMIN_TEATRO"));
            adminServicio.crearRol(new Rol("ROLE_CLIENTE"));

            adminServicio.crearCiudad(Ciudad.builder().nombre("Armenia").build());
            adminServicio.crearCiudad(Ciudad.builder().nombre("Pereira").build());
            adminServicio.crearCiudad(Ciudad.builder().nombre("Cali").build());
            adminServicio.crearCiudad(Ciudad.builder().nombre("Bogota").build());
            adminServicio.crearCiudad(Ciudad.builder().nombre("Manizales").build());

            adminServicio.crearAdministradorTeatro(AdministradorTeatro.builder().primerNombre("Daniel").primerApellido("Jaramillo").rol(rol_admin_teatro).correo("daniel@email.com").password("12345").username("daniel").build());
            adminServicio.crearAdministradorTeatro(AdministradorTeatro.builder().primerNombre("Alison").primerApellido("Liseth").rol(rol_admin_teatro).correo("alison@email.com").password("12345").username("alison").build());
            adminServicio.crearAdministrador(Administrador.builder().primerNombre("David").primerApellido("Rodriguez").rol(rol_admin).correo("david@email.com").password("12345").username("david").build());
        }

    }
}
