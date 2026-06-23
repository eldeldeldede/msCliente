package cl.duoc.msCliente.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipo_licencia")
@Schema(description = "Representa el tipo de licencia de conducir de un cliente en el sistema")
public class TipoLicencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del tipo de licencia",
            examples = {"1"}
    )
    private Integer id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre del tupo de licencia, el cual no debe ser nulo y debe ser único",
            examples = {"A", "B", "C"}
    )
    private String nombre;
}
