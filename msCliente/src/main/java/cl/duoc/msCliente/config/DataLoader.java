package cl.duoc.msCliente.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msCliente.model.Cliente;
import cl.duoc.msCliente.model.Licencia;
import cl.duoc.msCliente.model.TipoLicencia;
import cl.duoc.msCliente.repository.ClienteRepository;
import cl.duoc.msCliente.repository.LicenciaRepository;
import cl.duoc.msCliente.repository.TipoLicenciaRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(ClienteRepository clienteRepo,
                                   LicenciaRepository licenciaRepo,
                                   TipoLicenciaRepository tipoLicenciaRepo){
                                    return args ->{

                                        if (tipoLicenciaRepo.count() > 0) {
                                            System.out.println("Ya hay datos cargados");
                                        }else{
                                            TipoLicencia tipo1 = new TipoLicencia(1, "Tipo A");
                                            TipoLicencia tipo2 = new TipoLicencia(2, "Tipo B");
                                            TipoLicencia tipo3 = new TipoLicencia(3, "Tipo C");

                                            Licencia licencia1 = new Licencia(null, "ABC123", new java.util.Date(), tipo1);
                                            Licencia licencia2 = new Licencia(null, "DEF456", new java.util.Date(), tipo2 );
                                            Licencia licencia3 = new Licencia(null, "GHI789", new java.util.Date(), tipo3 );
                                            

                                            Cliente cliente1 = new Cliente(null, "Juan Perez", "12345678-9", "juan.perez@duoc.cl", "+56912345678", 5, licencia1);
                                            Cliente cliente2 = new Cliente(null, "María González", "98765432-1", "maria.gonzalez@duoc.cl", "+56987654321", 6, licencia2);
                                            Cliente cliente3 = new Cliente(null, "Pedro Ramírez", "55555555-5", "pedro.ramirez@duoc.cl", "+56955555555", 7, licencia3);

                                            tipoLicenciaRepo.save(tipo1);
                                            tipoLicenciaRepo.save(tipo2);
                                            tipoLicenciaRepo.save(tipo3);

                                            licenciaRepo.save(licencia1);
                                            licenciaRepo.save(licencia2);
                                            licenciaRepo.save(licencia3);

                                            clienteRepo.save(cliente1);
                                            clienteRepo.save(cliente2);
                                            clienteRepo.save(cliente3);
                                        }
                                    };
                                   }
    )
}
