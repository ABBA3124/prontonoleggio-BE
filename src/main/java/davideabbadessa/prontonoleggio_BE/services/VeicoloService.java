package davideabbadessa.prontonoleggio_BE.services;

import davideabbadessa.prontonoleggio_BE.entities.veicolo.Auto;
import davideabbadessa.prontonoleggio_BE.entities.veicolo.Moto;
import davideabbadessa.prontonoleggio_BE.entities.veicolo.Veicolo;
import davideabbadessa.prontonoleggio_BE.enums.veicolo.Disponibilita;
import davideabbadessa.prontonoleggio_BE.enums.veicolo.TipoVeicolo;
import davideabbadessa.prontonoleggio_BE.exceptions.NotFoundException;
import davideabbadessa.prontonoleggio_BE.payloads.veicolo.VeicoloDTO;
import davideabbadessa.prontonoleggio_BE.repositories.VeicoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VeicoloService {

    @Autowired
    private VeicoloRepository veicoloRepository;


    public Page<Veicolo> getVeicoliByDisponibilita(Disponibilita disponibilita, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return veicoloRepository.findByDisponibilita(disponibilita, pageable);
    }

    // salvaVeicolo
    public Veicolo salvaVeicolo(VeicoloDTO veicoloDTO) {
        Veicolo veicolo;

        if (veicoloDTO.tipoVeicolo() == TipoVeicolo.AUTO) {
            veicolo = new Auto(
                    veicoloDTO.motorizzazione(),
                    veicoloDTO.trasmissione(),
                    veicoloDTO.trazione(),
                    veicoloDTO.porte(),
                    veicoloDTO.capacitaBagagliaio(),
                    veicoloDTO.airbag(),
                    veicoloDTO.abs(),
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
                    veicoloDTO.parabrezza(),
                    veicoloDTO.abs(),
                    veicoloDTO.controlloTrattamento()
            );
        } else {
            throw new IllegalArgumentException("Tipo veicolo non supportato");
        }

        veicolo.setMarca(veicoloDTO.marca());
        veicolo.setModello(veicoloDTO.modello());
        veicolo.setAnno(veicoloDTO.anno());
        veicolo.setTarga(veicoloDTO.targa());
        veicolo.setTipoVeicolo(veicoloDTO.tipoVeicolo());
        veicolo.setCategoria(veicoloDTO.categoria());
        veicolo.setCilindrata(veicoloDTO.cilindrata());
        veicolo.setPotenza(veicoloDTO.potenza());
        veicolo.setConsumoCarburante(veicoloDTO.consumoCarburante());
        veicolo.setPosti(veicoloDTO.posti());
        veicolo.setTariffaGiornaliera(veicoloDTO.tariffaGiornaliera());
        veicolo.setDisponibilita(veicoloDTO.disponibilita());
        veicolo.setChilometraggio(veicoloDTO.chilometraggio());
        veicolo.setPosizione(veicoloDTO.posizione());
        veicolo.setDocumentiAssicurativi(veicoloDTO.documentiAssicurativi());
        veicolo.setRevisione(veicoloDTO.revisione());
        veicolo.setImmagini(veicoloDTO.immagini());

        return veicoloRepository.save(veicolo);
    }

    public Veicolo getVeicoloById(UUID id) {
        return veicoloRepository.findById(id).orElseThrow(() -> new NotFoundException("Veicolo non trovato"));
    }

    public Page<Veicolo> searchVeicoli(Specification<Veicolo> spec, Pageable pageable) {
        return veicoloRepository.findAll(spec, pageable);
    }

}
