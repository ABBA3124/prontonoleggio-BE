package davideabbadessa.prontonoleggio_BE.veicolo.components;

import davideabbadessa.prontonoleggio_BE.veicolo.entities.Veicolo;
import davideabbadessa.prontonoleggio_BE.veicolo.enums.Disponibilita;
import davideabbadessa.prontonoleggio_BE.veicolo.enums.TipoVeicolo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class VeicoloSpecification {

    public static Specification<Veicolo> hasMarca(String marca) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("marca")), "%" + marca.toLowerCase() + "%");
    }

    public static Specification<Veicolo> hasPosizione(String posizione) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("posizione")), "%" + posizione.toLowerCase() + "%");
    }

    public static Specification<Veicolo> hasModello(String modello) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("modello")), "%" + modello.toLowerCase() + "%");
    }

    public static Specification<Veicolo> hasTipoVeicolo(TipoVeicolo tipoVeicolo) {
        return (root, query, criteriaBuilder) ->
                tipoVeicolo == null ? null : criteriaBuilder.equal(root.get("tipoVeicolo"), tipoVeicolo);
    }

    public static Specification<Veicolo> hasDisponibilita(Disponibilita disponibilita) {
        return (root, query, criteriaBuilder) ->
                disponibilita == null ? null : criteriaBuilder.equal(root.get("disponibilita"), disponibilita);
    }

    public static Specification<Veicolo> hasTarga(String targa) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("targa")), "%" + targa.toLowerCase() + "%");
    }


    public Specification<Veicolo> hasCategoria(String categoria) {
        return (root, query, criteriaBuilder) ->
                categoria == null ? null : criteriaBuilder.equal(root.get("categoria"), categoria);
    }

    public Specification<Veicolo> hasPrezzoBetween(Double minPrezzo, Double maxPrezzo) {
        return (root, query, criteriaBuilder) -> {
            if (minPrezzo != null && maxPrezzo != null) {
                return criteriaBuilder.between(root.get("tariffaGiornaliera"), minPrezzo, maxPrezzo);
            } else if (minPrezzo != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("tariffaGiornaliera"), minPrezzo);
            } else if (maxPrezzo != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("tariffaGiornaliera"), maxPrezzo);
            } else {
                return null;
            }
        };
    }
}
