package co.edu.uniquindio.unicine.controladores;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/")
    public List<Cliente> index(){
        return this.clienteServicio.listarClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener_cliente(@PathVariable Long id) {
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        try {
            cliente = this.clienteServicio.obtenerCliente(id.intValue());
        }catch(Exception e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(cliente == null) {
            response.put("mensaje", "El cliente con el ID ".concat(id.toString().concat(" no éxiste en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("cliente", cliente);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
        Cliente clienteNuevo = null;

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            clienteNuevo = this.clienteServicio.registrarCliente(cliente);
        }catch(Exception e) {
            response.put("mensaje", "Error al realizar el insert a la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("mensaje", "El cliente ha sido creado con éxito!");
        response.put("cliente", clienteNuevo);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> actualizarCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Cliente clienteActual = null;

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            clienteActual = this.clienteServicio.actualizarCliente(cliente);
        } catch (Exception e) {
            response.put("mensaje", "No se pudo editar, el cliente con ID ".concat(cliente.getCodigo().toString()));
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(clienteActual == null) {
            response.put("mensaje", "No se pudo editar, el cliente con ID ".concat(cliente.getCodigo().toString().concat(" no éxiste en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("mensaje", "El cliente ha sido actualizado con éxito!");
        response.put("cliente", clienteActual);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            this.clienteServicio.eliminarCliente(id.intValue());
        }catch(Exception e) {
            response.put("mensaje", "Error al eliminar el cliente en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("mensaje", "El cliente ha sido eliminado con éxito!");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
