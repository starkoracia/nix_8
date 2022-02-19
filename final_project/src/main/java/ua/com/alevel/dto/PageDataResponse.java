package ua.com.alevel.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.dto.entities.BaseDto;

import java.util.List;

@Getter
@Setter
@ToString
public class PageDataResponse<DTO extends BaseDto> {
    List<DTO> dtoEntities;
    Integer amountOfElements;
}
