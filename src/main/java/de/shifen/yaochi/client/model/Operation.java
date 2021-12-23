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
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String operationName;
    String operationEnglishAlias;
    String comment;
    @Column(name="detailJson",columnDefinition = "longtext")
    String detailJson;
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Operation operation = (Operation) o;
        return id != null && Objects.equals(id, operation.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
