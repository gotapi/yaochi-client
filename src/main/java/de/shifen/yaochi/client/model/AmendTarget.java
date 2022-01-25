package de.shifen.yaochi.client.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ms404 <yaochi.github@404.ms>
 */
@Data
public class AmendTarget {
    String targetId;
    String targetName;

    public AmendTarget(String targetId, String targetName) {
        this.targetId = targetId;
        this.targetName = targetName;
    }

    public AmendTarget() {
    }
}
