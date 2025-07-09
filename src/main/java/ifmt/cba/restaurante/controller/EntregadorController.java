package ifmt.cba.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import ifmt.cba.restaurante.dto.EntregadorDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.EntregadorNegocio;

@RestController
@RequestMapping("/entregador")
public class EntregadorController {

    @Autowired
    private EntregadorNegocio entregadorNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EntregadorDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        List<EntregadorDTO> lista = entregadorNegocio.pesquisaTodos();

        for (EntregadorDTO entregadorDTO : lista) {
            addHateoasLinksCRUD(entregadorDTO);
        }

        return lista;
    }

    @GetMapping(value = "/codigo/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntregadorDTO buscarPorID(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        EntregadorDTO entregadorDTO = entregadorNegocio.pesquisaCodigo(codigo);
        addHateoasLinksCRUD(entregadorDTO);
        return entregadorDTO;
    }

    @GetMapping(value = "/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntregadorDTO buscarPorNome(@PathVariable("nome") String nome) throws NotFoundException, NotValidDataException {
        EntregadorDTO entregadorDTO = entregadorNegocio.pesquisaPorNome(nome);
        addHateoasLinksCRUD(entregadorDTO);
        return entregadorDTO;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntregadorDTO inserir(@RequestBody EntregadorDTO entregadorDTO) throws NotValidDataException, NotFoundException {
        EntregadorDTO novo = entregadorNegocio.inserir(entregadorDTO);
        addHateoasLinksCRUD(novo);
        return novo;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntregadorDTO alterar(@RequestBody EntregadorDTO entregadorDTO) throws NotValidDataException, NotFoundException {
        EntregadorDTO atualizado = entregadorNegocio.alterar(entregadorDTO);
        addHateoasLinksCRUD(atualizado);
        return atualizado;
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<?> excluir(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        entregadorNegocio.excluir(codigo);
        return ResponseEntity.noContent().build();
    }

    private void addHateoasLinksCRUD(EntregadorDTO entregadorDTO) throws NotFoundException, NotValidDataException {
        EntregadorDTO dummy = new EntregadorDTO();

        entregadorDTO.add(linkTo(methodOn(EntregadorController.class).buscarPorID(entregadorDTO.getCodigo()))
                .withSelfRel().withType("GET"));

        entregadorDTO.add(linkTo(methodOn(EntregadorController.class).buscarPorNome(entregadorDTO.getNome()))
                .withRel("buscarPorNome").withType("GET"));

        entregadorDTO.add(linkTo(methodOn(EntregadorController.class).inserir(dummy))
                .withRel("inserir").withType("POST"));

        entregadorDTO.add(linkTo(methodOn(EntregadorController.class).alterar(dummy))
                .withRel("alterar").withType("PUT"));

        entregadorDTO.add(linkTo(methodOn(EntregadorController.class).excluir(entregadorDTO.getCodigo()))
                .withRel("excluir").withType("DELETE"));
    }
}
