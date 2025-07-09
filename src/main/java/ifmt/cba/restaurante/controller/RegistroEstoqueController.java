package ifmt.cba.restaurante.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifmt.cba.restaurante.dto.MovimentoEstoqueDTO;
import ifmt.cba.restaurante.dto.RegistroEstoqueDTO;
import ifmt.cba.restaurante.entity.Produto;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.RegistroEstoqueNegocio;

@RestController
@RequestMapping("/api/registro-estoque")
public class RegistroEstoqueController {

    @Autowired
    private RegistroEstoqueNegocio registroEstoqueNegocio;

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody RegistroEstoqueDTO registroEstoqueDTO) {
        try {
            RegistroEstoqueDTO novoRegistro = registroEstoqueNegocio.inserir(registroEstoqueDTO);
            return ResponseEntity.ok(novoRegistro);
        } catch (NotValidDataException | NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> excluir(@RequestBody RegistroEstoqueDTO registroEstoqueDTO) {
        try {
            RegistroEstoqueDTO excluido = registroEstoqueNegocio.excluir(registroEstoqueDTO);
            return ResponseEntity.ok(excluido);
        } catch (NotValidDataException | NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(@PathVariable int codigo) {
        try {
            RegistroEstoqueDTO registro = registroEstoqueNegocio.pesquisaCodigo(codigo);
            if (registro != null) {
                return ResponseEntity.ok(registro);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/movimento/{movimento}")
    public ResponseEntity<?> buscarPorMovimento(@PathVariable MovimentoEstoqueDTO movimento) {
        try {
            List<RegistroEstoqueDTO> lista = registroEstoqueNegocio.buscarPorMovimento(movimento);
            return ResponseEntity.ok(lista);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/movimento/{movimento}/data/{data}")
    public ResponseEntity<?> buscarPorMovimentoEData(
            @PathVariable MovimentoEstoqueDTO movimento,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<RegistroEstoqueDTO> lista = registroEstoqueNegocio.buscarPorMovimentoEData(movimento, data);
            return ResponseEntity.ok(lista);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/produto/{codigoProduto}")
    public ResponseEntity<?> buscarPorProduto(@PathVariable int codigoProduto) {
        try {
            Produto produto = new Produto();
            produto.setCodigo(codigoProduto);
            List<RegistroEstoqueDTO> lista = registroEstoqueNegocio.buscarPorProduto(produto);
            return ResponseEntity.ok(lista);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }
}
