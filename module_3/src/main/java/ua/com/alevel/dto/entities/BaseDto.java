package ua.com.alevel.dto.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.entities.BaseEntity;

@Getter
@Setter
@ToString
public abstract class BaseDto<ENTITY extends BaseEntity> {
    private Long id;
}
