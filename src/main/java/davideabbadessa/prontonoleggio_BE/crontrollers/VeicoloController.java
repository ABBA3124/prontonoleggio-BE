package davideabbadessa.prontonoleggio_BE.crontrollers;

import davideabbadessa.prontonoleggio_BE.entities.veicolo.Veicolo;
import davideabbadessa.prontonoleggio_BE.enums.veicolo.Disponibilita;
import davideabbadessa.prontonoleggio_BE.payloads.veicolo.VeicoloDTO;
import davideabbadessa.prontonoleggio_BE.services.VeicoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    //<--------------------------------------Start Filtra Per Diponibilità-------------------------------------->//
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

    //<--------------------------------------Start Salva Veicolo-------------------------------------->//
    @PostMapping("/salva")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<Veicolo> salvaVeicolo(@Validated @RequestBody VeicoloDTO veicoloDTO) {
        Veicolo veicolo = veicoloService.salvaVeicolo(veicoloDTO);
        return new ResponseEntity<>(veicolo, HttpStatus.CREATED);
    }
    //<--------------------------------------Start get all veicoli -------------------------------------->//
    //<--------------------------------------Start Modifica Veicolo-------------------------------------->//
    //<--------------------------------------Start Delete Veicolo-------------------------------------->//


    //<--------------------------------------Start get veicolo by id -------------------------------------->//
    @GetMapping("/{id}")
    public ResponseEntity<Veicolo> getVeicoloById(@PathVariable UUID id) {
        Veicolo veicolo = veicoloService.getVeicoloById(id);
        return new ResponseEntity<>(veicolo, HttpStatus.OK);
    }

    //cerca veicoli
    

}
