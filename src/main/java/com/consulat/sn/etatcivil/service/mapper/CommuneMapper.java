package com.consulat.sn.etatcivil.service.mapper;

import com.consulat.sn.etatcivil.domain.*;
import com.consulat.sn.etatcivil.service.dto.CommuneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Commune and its DTO CommuneDTO.
 */
@Mapper(componentModel = "spring", uses = {VilleMapper.class, })
public interface CommuneMapper extends EntityMapper <CommuneDTO, Commune> {

    @Mapping(source = "ville.id", target = "villeId")
    CommuneDTO toDto(Commune commune); 

    @Mapping(source = "villeId", target = "ville")
    Commune toEntity(CommuneDTO communeDTO); 
    default Commune fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commune commune = new Commune();
        commune.setId(id);
        return commune;
    }
}
