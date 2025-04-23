package br.com.cdb.bancodigitaljpa.controller;

import br.com.cdb.bancodigitaljpa.entity.Cartao;
import br.com.cdb.bancodigitaljpa.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping("/emitir")
    public ResponseEntity<Cartao> emitirNovoCartao(@RequestParam Long contaId,
                                                   @RequestParam String tipo) {
        Cartao novoCartao = cartaoService.emitirNovoCartao(contaId, tipo);
        if (novoCartao != null) {
            return new ResponseEntity<>(novoCartao, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Ou outro status mais adequado
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cartao> obterDetalhesCartao(@PathVariable Long id) {
        Cartao cartao = cartaoService.obterDetalhesCartao(id);
        if (cartao != null) {
            return new ResponseEntity<>(cartao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/pagamentos")
    public ResponseEntity<String> realizarPagamento(@PathVariable Long id,
                                                     @RequestParam BigDecimal valor) {
        if (cartaoService.realizarPagamento(id, valor)) {
            return new ResponseEntity<>("Pagamento realizado com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao realizar pagamento. Verifique o cartão e o limite/saldo.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/limite")
    public ResponseEntity<String> alterarLimiteCartao(@PathVariable Long id,
                                                      @RequestParam BigDecimal novoLimite) {
        cartaoService.alterarLimiteCartao(id, novoLimite);
        return new ResponseEntity<>("Limite do cartão alterado com sucesso!", HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> alterarStatusCartao(@PathVariable Long id,
                                                      @RequestParam boolean ativo) {
        cartaoService.alterarStatusCartao(id, ativo);
        return new ResponseEntity<>("Status do cartão alterado com sucesso!", HttpStatus.OK);
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<String> alterarSenhaCartao(@PathVariable Long id,
                                                     @RequestParam String novaSenha) {
        cartaoService.alterarSenhaCartao(id, novaSenha);
        return new ResponseEntity<>("Senha do cartão alterada com sucesso!", HttpStatus.OK);
    }

    // Endpoints específicos para cartão de crédito
    @GetMapping("/{id}/fatura")
    public ResponseEntity<String> consultarFaturaCartaoCredito(@PathVariable Long id) {
        String fatura = cartaoService.consultarFaturaCartaoCredito(id);
        if (fatura != null) {
            return new ResponseEntity<>(fatura, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Ou outro status adequado
        }
    }

    @PostMapping("/{id}/fatura/pagamentos")
    public ResponseEntity<String> realizarPagamentoFaturaCartaoCredito(@PathVariable Long id,
                                                                        @RequestParam BigDecimal valor) {
        if (cartaoService.realizarPagamentoFaturaCartaoCredito(id, valor)) {
            return new ResponseEntity<>("Pagamento da fatura realizado com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao realizar pagamento da fatura.", HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint específico para cartão de débito
    @PutMapping("/{id}/limite-diario")
    public ResponseEntity<String> alterarLimiteDiarioCartaoDebito(@PathVariable Long id,
                                                                   @RequestParam BigDecimal novoLimiteDiario) {
        cartaoService.alterarLimiteDiarioCartaoDebito(id, novoLimiteDiario);
        return new ResponseEntity<>("Limite diário do cartão de débito alterado com sucesso!", HttpStatus.OK);
    }
}