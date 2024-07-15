package davideabbadessa.prontonoleggio_BE.repositories;

import davideabbadessa.prontonoleggio_BE.entities.veicolo.Veicolo;
import davideabbadessa.prontonoleggio_BE.enums.veicolo.Disponibilita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface VeicoloRepository extends JpaRepository<Veicolo, UUID> {

    Page<Veicolo> findByDisponibilita(Disponibilita disponibilita, Pageable pageable);


    Page<Veicolo> findAll(Specification<Veicolo> spec, Pageable pageable);
}
