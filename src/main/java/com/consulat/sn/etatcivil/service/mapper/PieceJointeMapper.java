package com.consulat.sn.etatcivil.service.mapper;

import com.consulat.sn.etatcivil.domain.*;
import com.consulat.sn.etatcivil.service.dto.PieceJointeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PieceJointe and its DTO PieceJointeDTO.
 */
@Mapper(componentModel = "spring", uses = {ExtraitMapper.class, })
public interface PieceJointeMapper extends EntityMapper <PieceJointeDTO, PieceJointe> {

    @Mapping(source = "declaration.id", target = "declarationId")
    PieceJointeDTO toDto(PieceJointe pieceJointe); 

    @Mapping(source = "declarationId", target = "declaration")
    PieceJointe toEntity(PieceJointeDTO pieceJointeDTO); 
    default PieceJointe fromId(Long id) {
        if (id == null) {
            return null;
        }
        PieceJointe pieceJointe = new PieceJointe();
        pieceJointe.setId(id);
        return pieceJointe;
    }
}
