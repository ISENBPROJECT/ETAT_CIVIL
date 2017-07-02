package com.consulat.sn.etatcivil.service.mapper;

import com.consulat.sn.etatcivil.domain.*;
import com.consulat.sn.etatcivil.service.dto.RegistreNaissanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RegistreNaissance and its DTO RegistreNaissanceDTO.
 */
@Mapper(componentModel = "spring", uses = {ExtraitMapper.class, })
public interface RegistreNaissanceMapper extends EntityMapper <RegistreNaissanceDTO, RegistreNaissance> {

    @Mapping(source = "extrait.id", target = "extraitId")
    RegistreNaissanceDTO toDto(RegistreNaissance registreNaissance); 

    @Mapping(source = "extraitId", target = "extrait")
    RegistreNaissance toEntity(RegistreNaissanceDTO registreNaissanceDTO); 
    default RegistreNaissance fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegistreNaissance registreNaissance = new RegistreNaissance();
        registreNaissance.setId(id);
        return registreNaissance;
    }
}
