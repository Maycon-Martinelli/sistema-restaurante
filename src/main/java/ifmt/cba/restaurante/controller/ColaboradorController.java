package ifmt.cba.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import ifmt.cba.restaurante.dto.ColaboradorDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.ColaboradorNegocio;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

    @Autowired
    private ColaboradorNegocio colaboradorNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ColaboradorDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        List<ColaboradorDTO> lista = colaboradorNegocio.pesquisaTodos();

        for (ColaboradorDTO colaboradorDTO : lista) {
            addHateoasLinksCRUD(colaboradorDTO);
        }

        return lista;
    }

    @GetMapping(value = "/codigo/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ColaboradorDTO buscarPorID(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        ColaboradorDTO colaboradorDTO = colaboradorNegocio.pesquisaCodigo(codigo);
        addHateoasLinksCRUD(colaboradorDTO);
        return colaboradorDTO;
    }

    @GetMapping(value = "/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ColaboradorDTO buscarPorNome(@PathVariable("nome") String nome) throws NotFoundException, NotValidDataException {
        ColaboradorDTO colaboradorDTO = colaboradorNegocio.pesquisaParteNome(nome);
        addHateoasLinksCRUD(colaboradorDTO);
        return colaboradorDTO;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ColaboradorDTO inserir(@RequestBody ColaboradorDTO colaboradorDTO) throws NotValidDataException, NotFoundException {
        ColaboradorDTO novoColaborador = colaboradorNegocio.inserir(colaboradorDTO);
        addHateoasLinksCRUD(novoColaborador);
        return novoColaborador;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ColaboradorDTO alterar(@RequestBody ColaboradorDTO colaboradorDTO) throws NotValidDataException, NotFoundException {
        ColaboradorDTO atualizado = colaboradorNegocio.alterar(colaboradorDTO);
        addHateoasLinksCRUD(atualizado);
        return atualizado;
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<?> excluir(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        colaboradorNegocio.excluir(codigo);
        return ResponseEntity.noContent().build();
    }

    private void addHateoasLinksCRUD(ColaboradorDTO colaboradorDTO) throws NotFoundException, NotValidDataException {
        ColaboradorDTO dummy = new ColaboradorDTO();

        colaboradorDTO.add(linkTo(methodOn(ColaboradorController.class).buscarPorID(colaboradorDTO.getCodigo()))
                .withSelfRel().withType("GET"));

        colaboradorDTO.add(linkTo(methodOn(ColaboradorController.class).buscarPorNome(colaboradorDTO.getNome()))
                .withRel("buscarPorNome").withType("GET"));

        colaboradorDTO.add(linkTo(methodOn(ColaboradorController.class).inserir(dummy))
                .withRel("inserir").withType("POST"));

        colaboradorDTO.add(linkTo(methodOn(ColaboradorController.class).alterar(dummy))
                .withRel("alterar").withType("PUT"));

        colaboradorDTO.add(linkTo(methodOn(ColaboradorController.class).excluir(colaboradorDTO.getCodigo()))
                .withRel("excluir").withType("DELETE"));
    }
}
