package de.shifen.yaochi.client.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ms404 <yaochi.github@404.ms>
 */
@Data
public class Operator {

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
