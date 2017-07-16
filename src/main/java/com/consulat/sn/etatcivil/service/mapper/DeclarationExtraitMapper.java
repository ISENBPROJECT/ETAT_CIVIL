package com.consulat.sn.etatcivil.service.mapper;

import com.consulat.sn.etatcivil.domain.Extrait;
import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitRechercheDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Extrait and its DTO ExtraitDTO.
 */
@Mapper(componentModel = "spring", uses = {VilleMapper.class, PersonneMapper.class, UserMapper.class,PieceJointeMapper.class,})
public interface DeclarationExtraitMapper extends EntityMapper<Extrait, DeclarationExtraitRechercheDTO> {

    @Mapping(target = "piecesJointes", ignore = false)
    Extrait toDto(DeclarationExtraitRechercheDTO declarationExtraitRechercheDTO);

    @Mapping(target = "piecesJointes", ignore = false)
    DeclarationExtraitRechercheDTO toEntity(Extrait extrait);

    default DeclarationExtraitRechercheDTO fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeclarationExtraitRechercheDTO extrait = new DeclarationExtraitRechercheDTO();
        extrait.setId(id);
        return extrait;
    }
}
