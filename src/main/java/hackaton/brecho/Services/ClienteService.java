package hackaton.brecho.Services;

import org.springframework.stereotype.Service;

import hackaton.brecho.Models.Cliente;
import hackaton.brecho.Repositories.ClienteRepository;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRep;

    public ClienteService(ClienteRepository clienteRep) {
        this.clienteRep = clienteRep;
    }


    public List<Cliente> findAll() {
        return clienteRep.findAll();
    }

    public Cliente findById(Long id) {
        return clienteRep.findById(id).orElse(null);
    }

    public Cliente save(Cliente cliente) {
        return clienteRep.save(cliente);
    }

    public void delete(Cliente cliente) {
        clienteRep.delete(cliente);
    }

}
