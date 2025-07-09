package ifmt.cba.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import ifmt.cba.restaurante.dto.CardapioDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.CardapioNegocio;

@RestController
@RequestMapping("/cardapio")
public class CardapioController {

    @Autowired
    private CardapioNegocio cardapioNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CardapioDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        List<CardapioDTO> listaCardapioDTO = cardapioNegocio.pesquisaTodos();

        for (CardapioDTO cardapioDTO : listaCardapioDTO) {
            addHateoasLinksCRUD(cardapioDTO);
        }

        return listaCardapioDTO;
    }

    @GetMapping(value = "/codigo/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CardapioDTO buscarPorID(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        CardapioDTO cardapioDTO = cardapioNegocio.pesquisaCodigo(codigo);
        addHateoasLinksCRUD(cardapioDTO);
        return cardapioDTO;
    }

    @GetMapping(value = "/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CardapioDTO buscarPorNome(@PathVariable("nome") String nome) throws NotFoundException, NotValidDataException {
        CardapioDTO cardapioDTO = cardapioNegocio.pesquisaPorNome(nome);
        addHateoasLinksCRUD(cardapioDTO);
        return cardapioDTO;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CardapioDTO inserir(@RequestBody CardapioDTO cardapioDTO) throws NotFoundException, NotValidDataException {
        CardapioDTO novoCardapio = cardapioNegocio.inserir(cardapioDTO);
        addHateoasLinksCRUD(novoCardapio);
        return novoCardapio;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CardapioDTO alterar(@RequestBody CardapioDTO cardapioDTO) throws NotFoundException, NotValidDataException {
        CardapioDTO cardapioAtualizado = cardapioNegocio.alterar(cardapioDTO);
        addHateoasLinksCRUD(cardapioAtualizado);
        return cardapioAtualizado;
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<?> excluir(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        cardapioNegocio.excluir(codigo);
        return ResponseEntity.noContent().build();
    }

    private void addHateoasLinksCRUD(CardapioDTO cardapioDTO) throws NotFoundException, NotValidDataException {
        cardapioDTO.add(linkTo(methodOn(CardapioController.class).buscarPorID(cardapioDTO.getCodigo())).withSelfRel().withType("GET"));
        cardapioDTO.add(linkTo(methodOn(CardapioController.class).buscarPorNome(cardapioDTO.getNome())).withRel("buscarPorNome").withType("GET"));
        cardapioDTO.add(linkTo(methodOn(CardapioController.class).inserir(cardapioDTO)).withRel("inserir").withType("POST"));
        cardapioDTO.add(linkTo(methodOn(CardapioController.class).alterar(cardapioDTO)).withRel("alterar").withType("PUT"));
        cardapioDTO.add(linkTo(methodOn(CardapioController.class).excluir(cardapioDTO.getCodigo())).withRel("excluir").withType("DELETE"));
    }
}
