package com.consulat.sn.etatcivil.service.mapper;

import com.consulat.sn.etatcivil.domain.*;
import com.consulat.sn.etatcivil.service.dto.ExtraitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Extrait and its DTO ExtraitDTO.
 */
@Mapper(componentModel = "spring", uses = {VilleMapper.class, PersonneMapper.class, UserMapper.class, })
public interface ExtraitMapper extends EntityMapper <ExtraitDTO, Extrait> {

    @Mapping(source = "lieuDeclaration.id", target = "lieuDeclarationId")

    @Mapping(source = "enfant.id", target = "enfantId")

    @Mapping(source = "mere.id", target = "mereId")

    @Mapping(source = "pere.id", target = "pereId")

    @Mapping(source = "agent.id", target = "agentId")
    ExtraitDTO toDto(Extrait extrait); 

    @Mapping(source = "lieuDeclarationId", target = "lieuDeclaration")

    @Mapping(source = "enfantId", target = "enfant")

    @Mapping(source = "mereId", target = "mere")

    @Mapping(source = "pereId", target = "pere")

    @Mapping(source = "agentId", target = "agent")
    @Mapping(target = "piecesJointes", ignore = true)
    Extrait toEntity(ExtraitDTO extraitDTO); 
    default Extrait fromId(Long id) {
        if (id == null) {
            return null;
        }
        Extrait extrait = new Extrait();
        extrait.setId(id);
        return extrait;
    }
}
