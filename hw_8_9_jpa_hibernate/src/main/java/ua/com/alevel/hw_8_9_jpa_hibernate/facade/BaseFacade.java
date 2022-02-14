package ua.com.alevel.hw_8_9_jpa_hibernate.facade;

import ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities.BaseDto;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataResponse;

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
