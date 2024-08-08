package davideabbadessa.prontonoleggio_BE.veicolo.services;

import davideabbadessa.prontonoleggio_BE.exceptions.NotFoundException;
import davideabbadessa.prontonoleggio_BE.prenotazioni.repositories.PrenotazioneRepository;
import davideabbadessa.prontonoleggio_BE.veicolo.entities.Auto;
import davideabbadessa.prontonoleggio_BE.veicolo.entities.Moto;
import davideabbadessa.prontonoleggio_BE.veicolo.entities.Veicolo;
import davideabbadessa.prontonoleggio_BE.veicolo.enums.Disponibilita;
import davideabbadessa.prontonoleggio_BE.veicolo.enums.TipoVeicolo;
import davideabbadessa.prontonoleggio_BE.veicolo.payloads.VeicoloDTO;
import davideabbadessa.prontonoleggio_BE.veicolo.repositories.VeicoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VeicoloService {

    @Autowired
    private VeicoloRepository veicoloRepository;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    // <---------- Salva Veicolo ---------->
    public Veicolo salvaVeicolo(VeicoloDTO veicoloDTO) {
        Veicolo veicolo;

        if (veicoloDTO.tipoVeicolo() == TipoVeicolo.AUTO) {
            veicolo = new Auto(
                    veicoloDTO.porte(),
                    veicoloDTO.capacitaBagagliaio(),
                    veicoloDTO.airbag(),
                    veicoloDTO.controlloStabilita(),
                    veicoloDTO.ariaCondizionata(),
                    veicoloDTO.sistemaNavigazione(),
                    veicoloDTO.sistemaAudio(),
                    veicoloDTO.bluetooth(),
                    veicoloDTO.sediliRiscaldati()
            );
        } else if (veicoloDTO.tipoVeicolo() == TipoVeicolo.MOTO) {
            veicolo = new Moto(
                    veicoloDTO.bauletto(),
                    veicoloDTO.parabrezza()
            );
        } else {
            throw new IllegalArgumentException("Tipo veicolo non supportato");
        }

        // <---------- Set Veicolo important ---------->
        veicolo.setDataCreazioneVeicolo(LocalDateTime.now());
        veicolo.setTipoVeicolo(veicoloDTO.tipoVeicolo());
        veicolo.setDisponibilita(veicoloDTO.disponibilita());

        // <---------- Sede Veicolo ---------->
        veicolo.setNomeSede(veicoloDTO.nomeSede());
        veicolo.setCittaSede(veicoloDTO.cittaSede());
        veicolo.setViaSede(veicoloDTO.viaSede());
        veicolo.setProvinciaSede(veicoloDTO.provinciaSede());
        veicolo.setTelefonoSede(veicoloDTO.telefonoSede());
        veicolo.setEmailSede(veicoloDTO.emailSede());
        veicolo.setOrariSede(veicoloDTO.orariSede());

        // <---------- Dati Veicolo ---------->
        veicolo.setTarga(veicoloDTO.targa());
        veicolo.setImmagini(veicoloDTO.immagini());
        veicolo.setMarca(veicoloDTO.marca());
        veicolo.setModello(veicoloDTO.modello());
        veicolo.setAnno(veicoloDTO.anno());
        veicolo.setCategoria(veicoloDTO.categoria());
        veicolo.setAlimentazione(veicoloDTO.alimentazione());
        veicolo.setCambio(veicoloDTO.cambio());
        veicolo.setTrazione(veicoloDTO.trazione());
        veicolo.setCilindrata(veicoloDTO.cilindrata());
        veicolo.setPotenzaKw(veicoloDTO.potenzaKw());
        veicolo.setConsumoCarburante(veicoloDTO.consumoCarburante());
        veicolo.setPosti(veicoloDTO.posti());
        veicolo.setTariffaGiornaliera(veicoloDTO.tariffaGiornaliera());
        veicolo.setChilometraggio(veicoloDTO.chilometraggio());
        veicolo.setDocumentiAssicurativi(veicoloDTO.documentiAssicurativi());
        veicolo.setRevisione(veicoloDTO.revisione());
        veicolo.setAbs(veicoloDTO.abs());

        return veicoloRepository.save(veicolo);
    }

    // <---------- Modifica Veicolo ---------->
    public Veicolo modificaVeicolo(UUID id, VeicoloDTO veicoloDTO) {
        Veicolo veicolo = getVeicoloById(id);

        if (veicoloDTO.tipoVeicolo() == TipoVeicolo.AUTO && veicolo instanceof Auto) {
            Auto auto = (Auto) veicolo;
            auto.setPorte(veicoloDTO.porte());
            auto.setCapacitaBagagliaio(veicoloDTO.capacitaBagagliaio());
            auto.setAirbag(veicoloDTO.airbag());
            auto.setControlloStabilita(veicoloDTO.controlloStabilita());
            auto.setAriaCondizionata(veicoloDTO.ariaCondizionata());
            auto.setSistemaNavigazione(veicoloDTO.sistemaNavigazione());
            auto.setSistemaAudio(veicoloDTO.sistemaAudio());
            auto.setBluetooth(veicoloDTO.bluetooth());
            auto.setSediliRiscaldati(veicoloDTO.sediliRiscaldati());

        } else if (veicoloDTO.tipoVeicolo() == TipoVeicolo.MOTO && veicolo instanceof Moto) {
            Moto moto = (Moto) veicolo;
            moto.setBauletto(veicoloDTO.bauletto());
            moto.setParabrezza(veicoloDTO.parabrezza());
        } else {
            throw new IllegalArgumentException("Tipo veicolo non supportato o mismatch con l'entità veicolo esistente");
        }

        // <---------- Set Veicolo important ---------->
        veicolo.setDataCreazioneVeicolo(LocalDateTime.now());
        veicolo.setTipoVeicolo(veicoloDTO.tipoVeicolo());
        veicolo.setDisponibilita(veicoloDTO.disponibilita());

        // <---------- Sede Veicolo ---------->
        veicolo.setNomeSede(veicoloDTO.nomeSede());
        veicolo.setCittaSede(veicoloDTO.cittaSede());
        veicolo.setViaSede(veicoloDTO.viaSede());
        veicolo.setProvinciaSede(veicoloDTO.provinciaSede());
        veicolo.setTelefonoSede(veicoloDTO.telefonoSede());
        veicolo.setEmailSede(veicoloDTO.emailSede());
        veicolo.setOrariSede(veicoloDTO.orariSede());

        // <---------- Dati Veicolo ---------->
        veicolo.setTarga(veicoloDTO.targa());
        veicolo.setImmagini(veicoloDTO.immagini());
        veicolo.setMarca(veicoloDTO.marca());
        veicolo.setModello(veicoloDTO.modello());
        veicolo.setAnno(veicoloDTO.anno());
        veicolo.setCategoria(veicoloDTO.categoria());
        veicolo.setAlimentazione(veicoloDTO.alimentazione());
        veicolo.setCambio(veicoloDTO.cambio());
        veicolo.setTrazione(veicoloDTO.trazione());
        veicolo.setCilindrata(veicoloDTO.cilindrata());
        veicolo.setPotenzaKw(veicoloDTO.potenzaKw());
        veicolo.setConsumoCarburante(veicoloDTO.consumoCarburante());
        veicolo.setPosti(veicoloDTO.posti());
        veicolo.setTariffaGiornaliera(veicoloDTO.tariffaGiornaliera());
        veicolo.setChilometraggio(veicoloDTO.chilometraggio());
        veicolo.setDocumentiAssicurativi(veicoloDTO.documentiAssicurativi());
        veicolo.setRevisione(veicoloDTO.revisione());
        veicolo.setAbs(veicoloDTO.abs());

        return veicoloRepository.save(veicolo);
    }

    // <---------- Get All Veicoli ---------->
    public Page<Veicolo> getAllVeicoli(Specification<Veicolo> spec, int pageNumber, int pageSize, String sortBy) {
        String[] sortParams = sortBy.split(",");
        String sortField = sortParams[0];
        Sort.Direction sortDirection = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1]) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField));
        return veicoloRepository.findAll(spec, pageable);
    }


    // <---------- Cerca Veicolo by ID ---------->
    public Veicolo getVeicoloById(UUID id) {
        return veicoloRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Veicolo non trovato"));
    }

    // <---------- Delete Veicoli by id ---------->
    public void deleteVeicolo(UUID id) {
        veicoloRepository.deleteById(id);
    }

    // <---------- Filtra Veicolo in base alla disponibilità enum ---------->
    public Page<Veicolo> getVeicoliByDisponibilita(Disponibilita disponibilita, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return veicoloRepository.findByDisponibilita(disponibilita, pageable);
    }

    // <---------- Ricerca Veicoli in base a parametri di filtro + Controllo se il veicolo è disponibile in base alle date di prenotazione ---------->
    public Page<Veicolo> searchVeicoli(Specification<Veicolo> spec, LocalDate dataInizio, LocalDate dataFine, int pageNumber, int pageSize, String sortBy) {
        if (dataInizio != null && dataFine != null) {
            List<UUID> veicoliNonDisponibili = prenotazioneRepository.findVeicoliNonDisponibili(dataInizio, dataFine);
            spec = spec.and(notInIds(veicoliNonDisponibili));
        }

        String[] sortParams = sortBy.split(",");
        String sortField = sortParams[0];
        Sort.Direction sortDirection = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1]) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField));

        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("disponibilita"), Disponibilita.DISPONIBILE));
        return veicoloRepository.findAll(spec, pageable);
    }

    // <---------- Controllo se il veicolo è disponibile in base alle date di prenotazione ---------->
    public Specification<Veicolo> notInIds(List<UUID> ids) {
        return (root, query, criteriaBuilder) -> {
            if (ids == null || ids.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("id")
                       .in(ids)
                       .not();
        };
    }
}


