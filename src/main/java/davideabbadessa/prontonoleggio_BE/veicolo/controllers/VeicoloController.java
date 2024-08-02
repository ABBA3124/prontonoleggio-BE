package davideabbadessa.prontonoleggio_BE.veicolo.controllers;

import davideabbadessa.prontonoleggio_BE.veicolo.components.VeicoloSpecification;
import davideabbadessa.prontonoleggio_BE.veicolo.entities.Veicolo;
import davideabbadessa.prontonoleggio_BE.veicolo.enums.Disponibilita;
import davideabbadessa.prontonoleggio_BE.veicolo.enums.TipoVeicolo;
import davideabbadessa.prontonoleggio_BE.veicolo.payloads.VeicoloDTO;
import davideabbadessa.prontonoleggio_BE.veicolo.services.VeicoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/veicoli")
@Validated
public class VeicoloController {

    @Autowired
    private VeicoloService veicoloService;

    @Autowired
    private VeicoloSpecification veicoloSpecification;

    // <--------------------------------------Ricerca Veicoli in base a parametri di filtro-------------------------------------->
    // Questo metodo consente di cercare veicoli basandosi su diversi parametri di filtro facoltativi:
//    @GetMapping("/search")
//    public ResponseEntity<Page<Veicolo>> searchVeicoli(
//            @RequestParam(required = false) String posizione,
//            @RequestParam(required = false) TipoVeicolo tipoVeicolo,
//            @RequestParam(required = false) String categoria,
//            @RequestParam(required = false) Double minPrezzo,
//            @RequestParam(required = false) Double maxPrezzo,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine,
//            @RequestParam(defaultValue = "dataCreazioneVeicolo") String sortBy,
//            Pageable pageable
//    ) {
//        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, sortBy));
//        Specification<Veicolo> spec = Specification.where(
//                veicoloSpecification.hasPosizione(posizione)
//                                    .and(veicoloSpecification.hasTipoVeicolo(tipoVeicolo))
//                                    .and(veicoloSpecification.hasCategoria(categoria))
//                                    .and(veicoloSpecification.hasPrezzoBetween(minPrezzo, maxPrezzo))
//        );
//
//        Page<Veicolo> veicoli = veicoloService.searchVeicoli(spec, dataInizio, dataFine, pageable);
//        return new ResponseEntity<>(veicoli, HttpStatus.OK);
//    }
    @GetMapping("/search")
    public Page<Veicolo> searchVeicoli(
            @RequestParam(required = false) String posizione,
            @RequestParam(required = false) TipoVeicolo tipoVeicolo,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double minPrezzo,
            @RequestParam(required = false) Double maxPrezzo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataCreazioneVeicolo") String sortBy) {

        Specification<Veicolo> spec = Specification.where(null);

        if (posizione != null) {
            spec = spec.and(VeicoloSpecification.hasPosizione(posizione));
        }
        if (tipoVeicolo != null) {
            spec = spec.and(VeicoloSpecification.hasTipoVeicolo(tipoVeicolo));
        }
        if (categoria != null) {
            spec = spec.and(veicoloSpecification.hasCategoria(categoria));
        }
        if (minPrezzo != null || maxPrezzo != null) {
            spec = spec.and(veicoloSpecification.hasPrezzoBetween(minPrezzo, maxPrezzo));
        }

        return this.veicoloService.searchVeicoli(spec, dataInizio, dataFine, page, size, sortBy);

    }

    // <--------------------------------------Filtra Veicolo Per Disponibilità-------------------------------------->
    @GetMapping("/disponibilita/disponibili")
    public Page<Veicolo> getVeicoliDisponibili(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return veicoloService.getVeicoliByDisponibilita(Disponibilita.DISPONIBILE, page, size, sortBy);
    }

    @GetMapping("/disponibilita/non-disponibili")
    public Page<Veicolo> getVeicoliNonDisponibili(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return veicoloService.getVeicoliByDisponibilita(Disponibilita.NON_DISPONIBILE, page, size, sortBy);
    }

    @GetMapping("/disponibilita/manutenzione")
    public Page<Veicolo> getVeicoliInManutenzione(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return veicoloService.getVeicoliByDisponibilita(Disponibilita.MANUTENZIONE, page, size, sortBy);
    }

    // <--------------------------------------GET Veicolo by ID-------------------------------------->
    @GetMapping("/{id}")
    public ResponseEntity<Veicolo> getVeicoloById(@PathVariable UUID id) {
        Veicolo veicolo = veicoloService.getVeicoloById(id);
        return new ResponseEntity<>(veicolo, HttpStatus.OK);
    }

    // <--------------------------------------Salva Veicolo-------------------------------------->
    @PostMapping("/salva")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<Veicolo> salvaVeicolo(@Validated @RequestBody VeicoloDTO veicoloDTO) {
        Veicolo veicolo = veicoloService.salvaVeicolo(veicoloDTO);
        return new ResponseEntity<>(veicolo, HttpStatus.CREATED);
    }

    // <--------------------------------------Get All Veicoli-------------------------------------->
//    @GetMapping
//    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
//    public Page<Veicolo> getAllVeicoli(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return veicoloService.getAllVeicoli(pageable);
//    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public Page<Veicolo> getAllVeicoli(
            @RequestParam(required = false) TipoVeicolo tipoVeicolo,
            @RequestParam(required = false) Disponibilita disponibilita,
            @RequestParam(required = false) String posizioneVeicolo,
            @RequestParam(required = false) String targaVeicolo,
            @RequestParam(required = false) String marcaVeicolo,
            @RequestParam(required = false) String modelloVeicolo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataCreazioneVeicolo") String sortBy) {

        Specification<Veicolo> spec = Specification.where(null);

        if (tipoVeicolo != null) {
            spec = spec.and(VeicoloSpecification.hasTipoVeicolo(tipoVeicolo));
        }
        if (disponibilita != null) {
            spec = spec.and(VeicoloSpecification.hasDisponibilita(disponibilita));
        }
        if (posizioneVeicolo != null) {
            spec = spec.and(VeicoloSpecification.hasPosizione(posizioneVeicolo));
        }
        if (targaVeicolo != null) {
            spec = spec.and(VeicoloSpecification.hasTarga(targaVeicolo));
        }
        if (marcaVeicolo != null) {
            spec = spec.and(VeicoloSpecification.hasMarca(marcaVeicolo));
        }
        if (modelloVeicolo != null) {
            spec = spec.and(VeicoloSpecification.hasModello(modelloVeicolo));
        }
        return this.veicoloService.getAllVeicoli(spec, page, size, sortBy);
    }

    // <--------------------------------------Modifica Veicolo-------------------------------------->
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<Veicolo> updateVeicolo(
            @PathVariable UUID id,
            @Validated @RequestBody VeicoloDTO veicoloDTO
    ) {
        Veicolo veicolo = veicoloService.modificaVeicolo(id, veicoloDTO);
        return new ResponseEntity<>(veicolo, HttpStatus.OK);
    }

    // <--------------------------------------Delete Veicolo-------------------------------------->
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void deleteVeicolo(@PathVariable UUID id) {
        veicoloService.deleteVeicolo(id);
    }

}
