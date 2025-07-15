package ifmt.cba.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifmt.cba.restaurante.dto.ProdutoDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.ProdutoNegocio;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoNegocio produtoNegocio;

    // Listar todos os produtos
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        try {
            return ResponseEntity.ok(produtoNegocio.pesquisaTodos());
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    // Buscar produto por código
    @GetMapping("/{codigo}")
    public ResponseEntity<ProdutoDTO> buscarPorCodigo(@PathVariable int codigo) {
        try {
            ProdutoDTO produto = produtoNegocio.pesquisaCodigo(codigo);
            return ResponseEntity.ok(produto);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar produto por nome (início)
    @GetMapping("/buscarPorNome")
    public ResponseEntity<ProdutoDTO> buscarPorNome(@RequestParam String nome) {
        try {
            ProdutoDTO produto = produtoNegocio.pesquisaPorNome(nome);
            return ResponseEntity.ok(produto);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Produtos abaixo do estoque mínimo
    @GetMapping("/estoqueMinimo")
    public ResponseEntity<List<ProdutoDTO>> produtosAbaixoEstoqueMinimo() {
        try {
            List<ProdutoDTO> lista = produtoNegocio.pesquisaProdutoAbaixoEstoqueMinimo();
            return ResponseEntity.ok(lista);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    // Inserir novo produto
    @PostMapping
    public ResponseEntity<?> inserirProduto(@RequestBody ProdutoDTO produtoDTO) {
        try {
            ProdutoDTO novoProduto = produtoNegocio.inserir(produtoDTO);
            return ResponseEntity.ok(novoProduto);
        } catch (NotValidDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Alterar produto existente
    @PutMapping("/{codigo}")
    public ResponseEntity<?> alterarProduto(@PathVariable int codigo, @RequestBody ProdutoDTO produtoDTO) {
        try {
            produtoDTO.setCodigo(codigo);
            ProdutoDTO produtoAlterado = produtoNegocio.alterar(produtoDTO);
            return ResponseEntity.ok(produtoAlterado);
        } catch (NotValidDataException | NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Excluir produto
    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> excluirProduto(@PathVariable int codigo) {
        try {
            produtoNegocio.excluir(codigo);
            return ResponseEntity.ok().build();
        } catch (NotValidDataException | NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
