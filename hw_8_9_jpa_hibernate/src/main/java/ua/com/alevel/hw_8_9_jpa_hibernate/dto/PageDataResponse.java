package ua.com.alevel.hw_8_9_jpa_hibernate.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities.BaseDto;

import java.util.List;

@Getter
@Setter
@ToString
public class PageDataResponse<D extends BaseDto> {
    List<D> dtoEntities;
    Integer amountOfElements;
}
