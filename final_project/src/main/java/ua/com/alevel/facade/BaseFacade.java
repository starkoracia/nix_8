package ua.com.alevel.facade;

import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.dto.PageDataResponse;
import ua.com.alevel.dto.entities.BaseDto;

import java.util.List;
import java.util.Optional;

public interface BaseFacade<DTO extends BaseDto> {

    Boolean create(DTO dto);
    void update(DTO dto);
    void delete(DTO dto);
    boolean existById(Long id);
    Optional<DTO> findById(Long id);
    List<DTO> findAll();
    PageDataResponse<DTO> findAllFromRequest(PageDataRequest request);
    Long countNumberOfSearchMatches(PageDataRequest request);
    Long count();

}

