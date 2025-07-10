package ifmt.cba.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ifmt.cba.restaurante.dto.EstadoPedidoDTO;
import ifmt.cba.restaurante.dto.PedidoDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.PedidoNegocio;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoNegocio pedidoNegocio;

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody PedidoDTO pedidoDTO) throws NotValidDataException {
        PedidoDTO novoPedido = pedidoNegocio.inserir(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable int codigo, @RequestBody PedidoDTO pedidoDTO) throws NotValidDataException, NotFoundException {
        pedidoDTO.setCodigo(codigo);
        PedidoDTO pedidoAtualizado = pedidoNegocio.alterar(pedidoDTO);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @PatchMapping("/{codigo}/estado")
    public ResponseEntity<Void> mudarEstado(@PathVariable int codigo, @RequestParam EstadoPedidoDTO novoEstado) throws NotValidDataException, NotFoundException {
        PedidoDTO pedido = pedidoNegocio.pesquisaCodigo(codigo);
        switch (novoEstado) {
            case PRODUCAO:
                pedidoNegocio.mudarPedidoParaProducao(pedido);
                break;
            case PRONTO:
                pedidoNegocio.mudarPedidoParaPronto(pedido);
                break;
            case ENTREGA:
                pedidoNegocio.mudarPedidoParaEntrega(pedido);
                break;
            case CONCLUIDO:
                pedidoNegocio.mudarPedidoParaConcluido(pedido);
                break;
            default:
                throw new NotValidDataException("Estado inválido para transição");
        }
        return ResponseEntity.noContent().build();
    }

    // outros endpoints como deletar, buscar por código, data, cliente etc.

}
