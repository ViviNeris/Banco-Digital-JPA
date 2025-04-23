package br.com.cdb.bancodigitaljpa.controller;

import br.com.cdb.bancodigitaljpa.entity.Conta;
import br.com.cdb.bancodigitaljpa.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contas")
public class ContaController {
	
	@Autowired
    private ContaService contaService;
	
	   @PostMapping
	    public ResponseEntity<Conta> criarConta(@RequestBody Conta conta) {
	        Conta novaConta = contaService.salvarConta(conta);
	        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
	    }

	    @GetMapping
	    public ResponseEntity<List<Conta>> listarContas() {
	        List<Conta> contas = contaService.listarContas();
	        return new ResponseEntity<>(contas, HttpStatus.OK);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Conta> buscarContaPorId(@PathVariable Long id) {
	        Conta conta = contaService.buscarContaPorId(id);
	        if (conta != null) {
	            return new ResponseEntity<>(conta, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        
	    }
	    @PostMapping("/{id}/transferencia")
	    public ResponseEntity<String> transferir(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
	    	Long contaDestinoId = ((Integer) payload.get("contaDestinoId")).longValue();
	        BigDecimal valor = null;
	        try {
	            valor = new BigDecimal(payload.get("valor").toString());
	        } catch (Exception e) {
	            return new ResponseEntity<>("Valor da transferência inválido.", HttpStatus.BAD_REQUEST);
	        }

	        if (contaDestinoId == null || valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
	            return new ResponseEntity<>("Dados de transferência inválidos.", HttpStatus.BAD_REQUEST);
	        }

	        boolean transferenciaRealizada = contaService.transferir(id, contaDestinoId, valor);

	        if (transferenciaRealizada) {
	            return new ResponseEntity<>("Transferência realizada com sucesso!", HttpStatus.OK);
	        } else {
	            // Refinando a resposta que já existe na classe ContaService
	            Conta contaOrigem = contaService.buscarContaPorId(id);
	            Conta contaDestino = contaService.buscarContaPorId(contaDestinoId);

	            if (contaOrigem == null) {
	                return new ResponseEntity<>("Conta de origem não encontrada.", HttpStatus.NOT_FOUND);
	            }
	            if (contaDestino == null) {
	                return new ResponseEntity<>("Conta de destino não encontrada.", HttpStatus.NOT_FOUND);
	            }
	            if (contaOrigem.getSaldo() == null || contaOrigem.getSaldo().compareTo(valor) < 0) {
	                return new ResponseEntity<>("Saldo insuficiente na conta de origem.", HttpStatus.BAD_REQUEST);
	            }
	            return new ResponseEntity<>("Falha ao realizar transferência.", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    @PostMapping("/{id}/deposito")
	    public ResponseEntity<String> depositar(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
	        BigDecimal valor = null;
	        try {
	            valor = new BigDecimal(payload.get("valor").toString());
	        } catch (Exception e) {
	            return new ResponseEntity<>("Valor do depósito inválido.", HttpStatus.BAD_REQUEST);
	        }

	        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
	            return new ResponseEntity<>("Valor do depósito deve ser positivo.", HttpStatus.BAD_REQUEST);
	        }

	        boolean depositoRealizado = contaService.depositar(id, valor);

	        if (depositoRealizado) {
	            return new ResponseEntity<>("Depósito realizado com sucesso!", HttpStatus.OK);
	        } else {
	            Conta conta = contaService.buscarContaPorId(id);
	            if (conta == null) {
	                return new ResponseEntity<>("Conta não encontrada.", HttpStatus.NOT_FOUND);
	            }
	            return new ResponseEntity<>("Falha ao realizar depósito.", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    @PostMapping("/{id}/saque")
	    public ResponseEntity<String> sacar(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
	        BigDecimal valor = null;
	        try {
	            valor = new BigDecimal(payload.get("valor").toString());
	        } catch (Exception e) {
	            return new ResponseEntity<>("Valor do saque inválido.", HttpStatus.BAD_REQUEST);
	        }

	        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
	            return new ResponseEntity<>("Valor do saque deve ser positivo.", HttpStatus.BAD_REQUEST);
	        }

	        boolean saqueRealizado = contaService.sacar(id, valor);

	        if (saqueRealizado) {
	            return new ResponseEntity<>("Saque realizado com sucesso!", HttpStatus.OK);
	        } else {
	            Conta conta = contaService.buscarContaPorId(id);
	            if (conta == null) {
	                return new ResponseEntity<>("Conta não encontrada.", HttpStatus.NOT_FOUND);
	            }
	            if (conta.getSaldo() == null || conta.getSaldo().compareTo(valor) < 0) {
	                return new ResponseEntity<>("Saldo insuficiente para saque.", HttpStatus.BAD_REQUEST);
	            }
	            return new ResponseEntity<>("Falha ao realizar saque.", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    @GetMapping("/{id}/saldo")
	    public ResponseEntity<?> consultarSaldo(@PathVariable Long id) {
	        // Recebe o ID da conta pela variável de caminho (@PathVariable)

	        //Chamando o método consultarSaldo da ContaService, passando o ID
	        BigDecimal saldo = contaService.consultarSaldo(id);

	        // Verificando se o saldo foi retornado (a conta existe)
	        if (saldo != null) {
	            // Se a conta existe, retorna o saldo com status HTTP OK (200)
	            // Podemos retornar o saldo diretamente ou dentro de um objeto JSON
	            return new ResponseEntity<>(Map.of("saldo", saldo), HttpStatus.OK);
	        } else {
	            // Se o saldo for null (a conta não existe), retorna status HTTP Not Found (404)
	            return new ResponseEntity<>("Conta não encontrada.", HttpStatus.NOT_FOUND);
	        }
	    }

}
