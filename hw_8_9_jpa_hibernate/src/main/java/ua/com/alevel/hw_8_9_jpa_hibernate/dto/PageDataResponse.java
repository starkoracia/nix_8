package ua.com.alevel.hw_8_9_jpa_hibernate.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.BaseEntity;

import java.util.List;

@Getter
@Setter
@ToString
public class PageDataResponse<E extends BaseEntity> {
    List<E> entities;
    Long amountOfElements;

}
