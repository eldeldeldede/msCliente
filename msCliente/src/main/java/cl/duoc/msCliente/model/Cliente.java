package cl.duoc.msCliente.model;


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
 @Table(name = "cliente")
  @Schema(description="Representa un cliente en el sistema")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description="ID unico del cliente",
            examples={"1"})
    private Integer id;

    @Column(nullable = false)
    @Schema(description="Nombre registrado del cliente, el cual no debe ser nulo")
    private String nombre;

    @Column(nullable = false, unique = true)
    @Schema(description="RUT registrado con el fin de organizar la base de datos")
    private String rut;

    @Column(nullable = true)
    @Schema(description="Correo con el cual se le informara todo el procedimiento al cliente")
    private String correo;

    @Column(nullable = true)
    @Schema(description="telefon o en el cual se contactasra en caso de cualquier imprevisto o emergencia")
    private String telefono;

    @Column(name = "usuario_id", nullable = false)
    @Schema(description="id unico que se le asigna al usuario para organizar con sus respectivos datos")
    private Integer usuarioId;

    @ManyToOne
    @JoinColumn(name = "licencia_id", nullable = false)
    @Schema(description="ID de la licencia registrada por el clientem con el fin de organizar la estructura de la base de datos")
    @JsonBackReference
    private Licencia licencia;
}
