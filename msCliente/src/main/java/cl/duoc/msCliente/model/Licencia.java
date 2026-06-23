package cl.duoc.msCliente.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "licencia")
@Schema(description = "Representa la licencia de conducir de un cliente en el sistema")
public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description="ID unico de la licencia del cliente",
            examples={"1"})
    private Integer idLicencia;

    @Column(nullable = false)
    @Schema(description="numero de la licencia registrada")
    private String numLicencia;

    @Column(nullable = false)
    @Schema(description="Fecha en la cual la licencia de conducir del cliente vence")
    private Date fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "tipo_licencia_id", nullable = false)
    @Schema(description="segun el tipo de licencia, se muestra resultados especificos",
            examples={"a,b,c"})
    @JsonBackReference
    private TipoLicencia tipoLicencia;


}
