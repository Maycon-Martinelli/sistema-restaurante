package ifmt.cba.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import ifmt.cba.restaurante.dto.ClienteDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.ClienteNegocio;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteNegocio clienteNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClienteDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        List<ClienteDTO> listaClienteDTO = clienteNegocio.pesquisaTodos();

        for (ClienteDTO clienteDTO : listaClienteDTO) {
            addHateoasLinksCRUD(clienteDTO);
        }

        return listaClienteDTO;
    }

    @GetMapping(value = "/codigo/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClienteDTO buscarPorID(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        ClienteDTO clienteDTO = clienteNegocio.pesquisaCodigo(codigo);
        addHateoasLinksCRUD(clienteDTO);
        return clienteDTO;
    }

    @GetMapping(value = "/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClienteDTO buscarPorNome(@PathVariable("nome") String nome) throws NotFoundException, NotValidDataException {
        ClienteDTO clienteDTO = clienteNegocio.pesquisaPorNome(nome);
        addHateoasLinksCRUD(clienteDTO);
        return clienteDTO;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ClienteDTO inserir(@RequestBody ClienteDTO clienteDTO) throws NotValidDataException, NotFoundException {
        ClienteDTO novoCliente = clienteNegocio.inserir(clienteDTO);
        addHateoasLinksCRUD(novoCliente);
        return novoCliente;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ClienteDTO alterar(@RequestBody ClienteDTO clienteDTO) throws NotValidDataException, NotFoundException {
        ClienteDTO clienteAtualizado = clienteNegocio.alterar(clienteDTO);
        addHateoasLinksCRUD(clienteAtualizado);
        return clienteAtualizado;
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<?> excluir(@PathVariable("codigo") int codigo) throws NotValidDataException, NotFoundException {
        clienteNegocio.excluir(codigo);
        return ResponseEntity.noContent().build();
    }

    // ✅ Corrigido com throws NotValidDataException
    private void addHateoasLinksCRUD(ClienteDTO clienteDTO) throws NotFoundException, NotValidDataException {
        ClienteDTO dummy = new ClienteDTO();

        clienteDTO.add(linkTo(methodOn(ClienteController.class).buscarPorID(clienteDTO.getCodigo()))
                .withSelfRel().withType("GET"));

        clienteDTO.add(linkTo(methodOn(ClienteController.class).buscarPorNome(clienteDTO.getNome()))
                .withRel("buscarPorNome").withType("GET"));

        clienteDTO.add(linkTo(methodOn(ClienteController.class).inserir(dummy))
                .withRel("inserir").withType("POST"));

        clienteDTO.add(linkTo(methodOn(ClienteController.class).alterar(dummy))
                .withRel("alterar").withType("PUT"));

        clienteDTO.add(linkTo(methodOn(ClienteController.class).excluir(clienteDTO.getCodigo()))
                .withRel("excluir").withType("DELETE"));
    }
}
