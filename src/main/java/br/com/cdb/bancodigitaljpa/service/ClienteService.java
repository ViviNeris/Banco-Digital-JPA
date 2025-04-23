package br.com.cdb.bancodigitaljpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cdb.bancodigitaljpa.entity.Cliente;
import br.com.cdb.bancodigitaljpa.repository.ClienteRepository;
import br.com.cdb.bancodigitaljpa.entity.Endereco;
import br.com.cdb.bancodigitaljpa.entity.CategoriaCliente;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;	

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	// Métodos salvarCliente, listarClientes, buscarCientesPorId e removerCliente
	public Cliente salvarCliente(Cliente cliente) {
        // Verificar se será necessário aplicar validações
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        return clienteOptional.orElse(null);
    }
    public void removerCliente(Long id) {
        clienteRepository.deleteById(id);
    }

}
