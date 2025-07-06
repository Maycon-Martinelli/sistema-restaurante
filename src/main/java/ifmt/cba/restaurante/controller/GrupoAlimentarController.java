package ifmt.cba.restaurante.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifmt.cba.restaurante.dto.GrupoAlimentarDTO;
import ifmt.cba.restaurante.negocio.GrupoAlimentarNegocio;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;

@RestController
@RequestMapping("/api/grupos-alimentares")
public class GrupoAlimentarController {

    @Autowired
    private GrupoAlimentarNegocio grupoAlimentarNegocio;

    @PostMapping
    public ResponseEntity<GrupoAlimentarDTO> inserirGrupo(@RequestBody GrupoAlimentarDTO dto) 
            throws NotValidDataException {
        GrupoAlimentarDTO dtoSalvo = grupoAlimentarNegocio.inserir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalvo);
    }

    @PutMapping
    public ResponseEntity<GrupoAlimentarDTO> alterarGrupo(@RequestBody GrupoAlimentarDTO dto) 
            throws NotValidDataException, NotFoundException {
        GrupoAlimentarDTO dtoAlterado = grupoAlimentarNegocio.alterar(dto);
        return ResponseEntity.ok(dtoAlterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirGrupo(@PathVariable int id) 
            throws NotValidDataException, NotFoundException {
        grupoAlimentarNegocio.excluir(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GrupoAlimentarDTO>> listarTodos() throws NotFoundException {
        return ResponseEntity.ok(grupoAlimentarNegocio.pesquisaTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoAlimentarDTO> pesquisarPorId(@PathVariable int id) throws NotFoundException {
        return ResponseEntity.ok(grupoAlimentarNegocio.pesquisaCodigo(id));
    }
}