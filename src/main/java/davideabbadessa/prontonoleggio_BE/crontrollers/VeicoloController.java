package davideabbadessa.prontonoleggio_BE.crontrollers;

import davideabbadessa.prontonoleggio_BE.components.VeicoloSpecification;
import davideabbadessa.prontonoleggio_BE.entities.veicolo.Veicolo;
import davideabbadessa.prontonoleggio_BE.enums.veicolo.Disponibilita;
import davideabbadessa.prontonoleggio_BE.enums.veicolo.TipoVeicolo;
import davideabbadessa.prontonoleggio_BE.payloads.veicolo.VeicoloDTO;
import davideabbadessa.prontonoleggio_BE.services.VeicoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/veicoli")
@Validated
public class VeicoloController {

    @Autowired
    private VeicoloService veicoloService;

    @Autowired
    private VeicoloSpecification veicoloSpecification;

    //<--------------------------------------Filtra Per Diponibilità-------------------------------------->//
    @GetMapping("/disponibilita/disponibili")
    public Page<Veicolo> getVeicoliDisponibili(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return veicoloService.getVeicoliByDisponibilita(Disponibilita.DISPONIBILE, page, size, sortBy);
    }

    @GetMapping("/disponibilita/non-disponibili")
    public Page<Veicolo> getVeicoliNonDisponibili(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return veicoloService.getVeicoliByDisponibilita(Disponibilita.NON_DISPONIBILE, page, size, sortBy);
    }

    @GetMapping("/disponibilita/manutenzione")
    public Page<Veicolo> getVeicoliInManutenzione(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return veicoloService.getVeicoliByDisponibilita(Disponibilita.MANUTENZIONE, page, size, sortBy);
    }

    //<--------------------------------------Salva Veicolo-------------------------------------->//
    @PostMapping("/salva")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<Veicolo> salvaVeicolo(@Validated @RequestBody VeicoloDTO veicoloDTO) {
        Veicolo veicolo = veicoloService.salvaVeicolo(veicoloDTO);
        return new ResponseEntity<>(veicolo, HttpStatus.CREATED);
    }
    //<--------------------------------------get all veicoli-------------------------------------->//
    //<--------------------------------------Modifica Veicolo-------------------------------------->//
    //<--------------------------------------Delete Veicolo-------------------------------------->//


    //<--------------------------------------get veicolo by id -------------------------------------->//
    @GetMapping("/{id}")
    public ResponseEntity<Veicolo> getVeicoloById(@PathVariable UUID id) {
        Veicolo veicolo = veicoloService.getVeicoloById(id);
        return new ResponseEntity<>(veicolo, HttpStatus.OK);
    }

    //<--------------------------------------Ricerca Veicolo-------------------------------------->//
    //cerca veicoli
    @GetMapping("/search")
    public ResponseEntity<Page<Veicolo>> searchVeicoli(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String posizione,
            @RequestParam(required = false) String modello,
            @RequestParam(required = false) Integer anno,
            @RequestParam(required = false) TipoVeicolo tipoVeicolo,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double minPrezzo,
            @RequestParam(required = false) Double maxPrezzo,
            Pageable pageable
    ) {
        Specification<Veicolo> spec = Specification.where(
                veicoloSpecification.hasMarca(marca)
                        .and(veicoloSpecification.hasPosizione(posizione))
                        .and(veicoloSpecification.hasModello(modello))
                        .and(veicoloSpecification.hasAnno(anno))
                        .and(veicoloSpecification.hasTipoVeicolo(tipoVeicolo))
                        .and(veicoloSpecification.hasCategoria(categoria))
                        .and(veicoloSpecification.hasPrezzoBetween(minPrezzo, maxPrezzo))
        );

        Page<Veicolo> veicoli = veicoloService.searchVeicoli(spec, pageable);
        return new ResponseEntity<>(veicoli, HttpStatus.OK);
    }


}
