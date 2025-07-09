package ifmt.cba.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifmt.cba.restaurante.dto.PreparoProdutoDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.PreparoProdutoNegocio;

@RestController
@RequestMapping("/preparoproduto")
public class PreparoProdutoController {

    @Autowired
    private PreparoProdutoNegocio preparoProdutoNegocio;

    @GetMapping
    public ResponseEntity<List<PreparoProdutoDTO>> listarTodos() {
        try {
            List<PreparoProdutoDTO> lista = preparoProdutoNegocio.pesquisaTodos();
            return ResponseEntity.ok(lista);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreparoProdutoDTO> buscarPorId(@PathVariable int id) {
        try {
            PreparoProdutoDTO dto = preparoProdutoNegocio.pesquisaPorCodigo(id);
            return ResponseEntity.ok(dto);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody PreparoProdutoDTO dto) {
        try {
            PreparoProdutoDTO criado = preparoProdutoNegocio.inserir(dto);
            return ResponseEntity.ok(criado);
        } catch (NotValidDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable int id, @RequestBody PreparoProdutoDTO dto) {
        try {
            dto.setCodigo(id);
            PreparoProdutoDTO alterado = preparoProdutoNegocio.alterar(dto);
            return ResponseEntity.ok(alterado);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotValidDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable int id) {
        try {
            preparoProdutoNegocio.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotValidDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Opcional: Buscar por nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome) {
        try {
            PreparoProdutoDTO dto = preparoProdutoNegocio.pesquisaPorNome(nome);
            return ResponseEntity.ok(dto);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Outros filtros podem ser expostos aqui se quiser (por produto, tipo preparo, etc)
}
