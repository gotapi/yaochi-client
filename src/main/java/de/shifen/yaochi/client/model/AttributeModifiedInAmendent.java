package de.shifen.yaochi.client.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ms404 <yaochi.github@404.ms>
 */


@Getter
@Setter
@ToString
@Table
@Entity
public class AttributeModifiedInAmendent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String attributeType;
    private String attributeName;
    private String attributeAlias;
    private String oldValue;
    private String newValue;
    private String diffValue;

    public AttributeModifiedInAmendent() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)){
            return false;
        }
        AttributeModifiedInAmendent that = (AttributeModifiedInAmendent) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
