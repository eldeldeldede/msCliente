package cl.duoc.msCliente.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 @Data
 @AllArgsConstructor
 @NoArgsConstructor
 @Entity
 @Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String rut;

    @Column(nullable = true)
    private String correo;

    @Column(nullable = true)
    private String telefono;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonBackReference
    private Licencia licencia;
}
