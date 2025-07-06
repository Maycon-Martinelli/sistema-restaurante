package ifmt.cba.restaurante.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table (name = "grupo_alimentar")
public class GrupoAlimentar {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(name = "nome", length = 40)
    private String nome;
 
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public String validar(){
        if (this.nome == null || this.nome.isEmpty()) {
            return "O nome do grupo alimentar não pode ser nulo ou vazio";
        }
        return "";
    }
}
