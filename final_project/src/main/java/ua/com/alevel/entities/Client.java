package ua.com.alevel.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "clients")
@Entity
@Getter
@Setter
@ToString(callSuper = true)
public class Client extends Person {

    @Column(name = "is_supplier")
    private Boolean isSupplier;

    @Column(name = "recommendation")
    private String recommendation;

    @Column(name = "annotation")
    private String annotation;

}
