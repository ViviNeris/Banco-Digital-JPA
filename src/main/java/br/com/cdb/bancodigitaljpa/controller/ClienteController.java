	package br.com.cdb.bancodigitaljpa.controller;
	
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.DeleteMapping;
	
	import br.com.cdb.bancodigitaljpa.entity.Cliente;
	import br.com.cdb.bancodigitaljpa.service.ClienteService;
	import java.util.List;
	
	
	
	@RestController
	@RequestMapping("/cliente")
	public class ClienteController {
		
		@Autowired
		private ClienteService clienteService;
		
		@PostMapping("/add")  // adicionarCliente alterado em 04/04
	    public ResponseEntity<String> adicionarCliente(@RequestBody Cliente cliente) {
	        Cliente clienteAdicionado = clienteService.salvarCliente(cliente);
	
	        if (clienteAdicionado != null) {
	            return new ResponseEntity<>("Cliente " + cliente.getNome() + " adicionado com sucesso!", HttpStatus.CREATED);
	        } else {
	            return new ResponseEntity<>("Erro ao adicionar cliente.", HttpStatus.INTERNAL_SERVER_ERROR); // Altere a mensagem de erro para algo mais genérico
	        }
	    }
		
		@GetMapping("/{id}")
	    public ResponseEntity<Cliente> obterCliente(@PathVariable Long id) {
	        Cliente cliente = clienteService.buscarClientePorId(id);
	        if (cliente != null) {
	            return new ResponseEntity<>(cliente, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
		@PutMapping("/{id}")
	    public ResponseEntity<String> atualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
	        Cliente clienteExistente = clienteService.buscarClientePorId(id);
	        if (clienteExistente != null) {
	            clienteAtualizado.setId(id); // Garante que o ID a ser atualizado é o correto
	            Cliente clienteSalvo = clienteService.salvarCliente(clienteAtualizado);
	            return new ResponseEntity<>("Cliente com ID " + id + " atualizado com sucesso!", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Cliente com ID " + id + " não encontrado.", HttpStatus.NOT_FOUND);
	        }
	    }
		@DeleteMapping("/{id}")
	    public ResponseEntity<String> removerCliente(@PathVariable Long id) {
	        Cliente clienteExistente = clienteService.buscarClientePorId(id);
	        if (clienteExistente != null) {
	            clienteService.removerCliente(id);
	            return new ResponseEntity<>("Cliente com ID " + id + " removido com sucesso!", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Cliente com ID " + id + " não encontrado.", HttpStatus.NOT_FOUND);
	        }
	    }
		@GetMapping
	    public ResponseEntity<List<Cliente>> listarClientes() {
	        List<Cliente> clientes = clienteService.listarClientes();
	        return new ResponseEntity<>(clientes, HttpStatus.OK);
	    }
		
	}
