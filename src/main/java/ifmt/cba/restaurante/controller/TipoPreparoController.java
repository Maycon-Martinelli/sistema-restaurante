package ifmt.cba.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifmt.cba.restaurante.dto.TipoPreparoDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.TipoPreparoNegocio;

@RestController
@RequestMapping("/tipopreparo")
public class TipoPreparoController {

    @Autowired
    private TipoPreparoNegocio tipoPreparoNegocio;

    @PostMapping
    public ResponseEntity<TipoPreparoDTO> inserir(@RequestBody TipoPreparoDTO tipoPreparoDTO) {
        try {
            TipoPreparoDTO dto = tipoPreparoNegocio.inserir(tipoPreparoDTO);
            return ResponseEntity.ok(dto);
        } catch (NotValidDataException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<TipoPreparoDTO> alterar(@PathVariable int codigo, @RequestBody TipoPreparoDTO tipoPreparoDTO) {
        try {
            tipoPreparoDTO.setCodigo(codigo);
            TipoPreparoDTO dto = tipoPreparoNegocio.alterar(tipoPreparoDTO);
            return ResponseEntity.ok(dto);
        } catch (NotValidDataException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluir(@PathVariable int codigo) {
        try {
            tipoPreparoNegocio.excluir(codigo);
            return ResponseEntity.noContent().build();
        } catch (NotValidDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<TipoPreparoDTO> buscarPorCodigo(@PathVariable int codigo) {
        try {
            TipoPreparoDTO dto = tipoPreparoNegocio.pesquisaCodigo(codigo);
            return ResponseEntity.ok(dto);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TipoPreparoDTO>> listarTodos() {
        try {
            List<TipoPreparoDTO> lista = tipoPreparoNegocio.pesquisaTodos();
            return ResponseEntity.ok(lista);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/descricao")
    public ResponseEntity<TipoPreparoDTO> buscarPorDescricao(@RequestParam String descricao) {
        try {
            TipoPreparoDTO dto = tipoPreparoNegocio.pesquisaPorDescricao(descricao);
            return ResponseEntity.ok(dto);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
