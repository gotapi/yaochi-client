package de.shifen.yaochi.client.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author xurenlu
 */
@Data
@Table
@Entity
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    private String operatorId;
    private String operatorName;
    private String operatorAddonInformation;

    public Operator(String operatorId, String operatorName, String operatorAddonInformation) {
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.operatorAddonInformation = operatorAddonInformation;
    }

    public Operator() {
    }
}
