package com.consulat.sn.etatcivil.service.mapper;

import com.consulat.sn.etatcivil.domain.*;
import com.consulat.sn.etatcivil.service.dto.VilleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ville and its DTO VilleDTO.
 */
@Mapper(componentModel = "spring", uses = {PaysMapper.class, })
public interface VilleMapper extends EntityMapper <VilleDTO, Ville> {

    @Mapping(source = "pays.id", target = "paysId")
    VilleDTO toDto(Ville ville); 

    @Mapping(source = "paysId", target = "pays")
    Ville toEntity(VilleDTO villeDTO); 
    default Ville fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ville ville = new Ville();
        ville.setId(id);
        return ville;
    }
}
