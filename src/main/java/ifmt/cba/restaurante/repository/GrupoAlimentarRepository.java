package ifmt.cba.restaurante.repository;

import java.util.List; // Importe a classe List
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ifmt.cba.restaurante.entity.GrupoAlimentar;

public interface GrupoAlimentarRepository extends JpaRepository<GrupoAlimentar, Integer> {

    List<GrupoAlimentar> findByNomeIgnoreCaseStartingWith(String nome);
    
    // Opcional, mas uma forma mais moderna de lidar com a busca por nome exato
    Optional<GrupoAlimentar> findByNome(String nome);
}