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

    // <-------------------------------------- ROLE SUPER ADMIN -------------------------------------->

    // <---------- Salva Veicolo (ROLE SUPER ADMIN) ---------->
    @PostMapping("/salva")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<Veicolo> salvaVeicolo(@Validated @RequestBody VeicoloDTO veicoloDTO) {
        Veicolo veicolo = veicoloService.salvaVeicolo(veicoloDTO);
        return new ResponseEntity<>(veicolo, HttpStatus.CREATED);
    }

    // <---------- Modifica Veicolo (ROLE SUPER ADMIN) ---------->
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<Veicolo> updateVeicolo(
            @PathVariable UUID id,
            @Validated @RequestBody VeicoloDTO veicoloDTO
    ) {
        Veicolo veicolo = veicoloService.modificaVeicolo(id, veicoloDTO);
        return new ResponseEntity<>(veicolo, HttpStatus.OK);
    }

    // <---------- Delete Veicolo (ROLE SUPER ADMIN) ---------->
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void deleteVeicolo(@PathVariable UUID id) {
        veicoloService.deleteVeicolo(id);
    }


    // <---------- Get All Veicoli (ROLE SUPER ADMIN) ---------->
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
            @RequestParam(defaultValue = "dataCreazioneVeicolo,desc") String sortBy) {

        Specification<Veicolo> spec = Specification.where(null);

        if (tipoVeicolo != null) {
            spec = spec.and(VeicoloSpecification.hasTipoVeicolo(tipoVeicolo));
        }
        if (disponibilita != null) {
            spec = spec.and(VeicoloSpecification.hasDisponibilita(disponibilita));
        }
        if (posizioneVeicolo != null) {
            spec = spec.and(VeicoloSpecification.hasCittaSede(posizioneVeicolo));
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


    // <-------------------------------------- USER -------------------------------------->

    // <---------- GET Veicoli by Disponibilità (disponibili) ---------->
    @GetMapping("/disponibilita/disponibili")
    public Page<Veicolo> getVeicoliDisponibili(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return veicoloService.getVeicoliByDisponibilita(Disponibilita.DISPONIBILE, page, size, sortBy);
    }

    // <---------- GET Veicoli by Disponibilità (non disponibili) ---------->
    @GetMapping("/disponibilita/non-disponibili")
    public Page<Veicolo> getVeicoliNonDisponibili(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return veicoloService.getVeicoliByDisponibilita(Disponibilita.NON_DISPONIBILE, page, size, sortBy);
    }

    // <---------- GET Veicoli by Disponibilità (in manutenzione) ---------->
    @GetMapping("/disponibilita/manutenzione")
    public Page<Veicolo> getVeicoliInManutenzione(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return veicoloService.getVeicoliByDisponibilita(Disponibilita.MANUTENZIONE, page, size, sortBy);
    }

    // <---------- GET Veicolo by ID ---------->
    @GetMapping("/{id}")
    public ResponseEntity<Veicolo> getVeicoloById(@PathVariable UUID id) {
        Veicolo veicolo = veicoloService.getVeicoloById(id);
        return new ResponseEntity<>(veicolo, HttpStatus.OK);
    }

    // <---------- Filtra Veicoli ---------->
    @GetMapping("/search")
    public Page<Veicolo> searchVeicoli(
            @RequestParam(required = false) String posizioneVeicolo,
            @RequestParam(required = false) TipoVeicolo tipoVeicolo,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double minPrezzo,
            @RequestParam(required = false) Double maxPrezzo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "dataCreazioneVeicolo") String sortBy) {
            @RequestParam(defaultValue = "dataCreazioneVeicolo,desc") String sortBy) {

        Specification<Veicolo> spec = Specification.where(null);

        if (posizioneVeicolo != null) {
            spec = spec.and(VeicoloSpecification.hasCittaSede(posizioneVeicolo));
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
}
