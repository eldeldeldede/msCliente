package cl.duoc.msCliente.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "licencia")
public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLicencia;

    

    @Column(nullable = false)
    private String numLicencia;

    @Column(nullable = false)
    private String tipoLicencia;

    @Column(nullable = false)
    private Date fechaVencimiento;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


}