package ua.com.alevel.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.entities.enums.Position;

import javax.persistence.*;

@Table(name = "employees")
@Entity
@Getter
@Setter
@ToString(callSuper = true)
public class Employee extends Person {

    @Column(name = "position")
    @Enumerated(EnumType.ORDINAL)
    private Position position;

}
