package de.shifen.yaochi.client.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author xurenlu
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table
@Entity
public class AmendTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String targetId;
    String targetName;

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        AmendTarget that = (AmendTarget) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
