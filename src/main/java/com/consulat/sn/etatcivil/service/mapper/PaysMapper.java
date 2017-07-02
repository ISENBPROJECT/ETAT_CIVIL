package com.consulat.sn.etatcivil.service.mapper;

import com.consulat.sn.etatcivil.domain.*;
import com.consulat.sn.etatcivil.service.dto.PaysDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pays and its DTO PaysDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaysMapper extends EntityMapper <PaysDTO, Pays> {
    
    
    default Pays fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pays pays = new Pays();
        pays.setId(id);
        return pays;
    }
}
