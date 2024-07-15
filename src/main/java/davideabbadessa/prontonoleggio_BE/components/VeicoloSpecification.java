package davideabbadessa.prontonoleggio_BE.components;

import davideabbadessa.prontonoleggio_BE.entities.veicolo.Veicolo;
import davideabbadessa.prontonoleggio_BE.enums.veicolo.TipoVeicolo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class VeicoloSpecification {

    public Specification<Veicolo> hasMarca(String marca) {
        return (root, query, criteriaBuilder) ->
                marca == null ? null : criteriaBuilder.equal(root.get("marca"), marca);
    }

    public Specification<Veicolo> hasPosizione(String posizione) {
        return (root, query, criteriaBuilder) ->
                posizione == null ? null : criteriaBuilder.equal(root.get("posizione"), posizione);
    }

    public Specification<Veicolo> hasModello(String modello) {
        return (root, query, criteriaBuilder) ->
                modello == null ? null : criteriaBuilder.equal(root.get("modello"), modello);
    }

    public Specification<Veicolo> hasAnno(Integer anno) {
        return (root, query, criteriaBuilder) ->
                anno == null ? null : criteriaBuilder.equal(root.get("anno"), anno);
    }

    public Specification<Veicolo> hasTipoVeicolo(TipoVeicolo tipoVeicolo) {
        return (root, query, criteriaBuilder) ->
                tipoVeicolo == null ? null : criteriaBuilder.equal(root.get("tipoVeicolo"), tipoVeicolo);
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
