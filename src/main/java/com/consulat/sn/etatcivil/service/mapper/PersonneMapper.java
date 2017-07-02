package com.consulat.sn.etatcivil.service.mapper;

import com.consulat.sn.etatcivil.domain.*;
import com.consulat.sn.etatcivil.service.dto.PersonneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Personne and its DTO PersonneDTO.
 */
@Mapper(componentModel = "spring", uses = {VilleMapper.class, })
public interface PersonneMapper extends EntityMapper <PersonneDTO, Personne> {

    @Mapping(source = "adresse.id", target = "adresseId")

    @Mapping(source = "pere.id", target = "pereId")

    @Mapping(source = "mere.id", target = "mereId")

    @Mapping(source = "lieuNaissance.id", target = "lieuNaissanceId")
    PersonneDTO toDto(Personne personne); 

    @Mapping(source = "adresseId", target = "adresse")

    @Mapping(source = "pereId", target = "pere")

    @Mapping(source = "mereId", target = "mere")

    @Mapping(source = "lieuNaissanceId", target = "lieuNaissance")
    Personne toEntity(PersonneDTO personneDTO); 
    default Personne fromId(Long id) {
        if (id == null) {
            return null;
        }
        Personne personne = new Personne();
        personne.setId(id);
        return personne;
    }
}
