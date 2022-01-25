package de.shifen.yaochi.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

/**
 * @author ms404 <yaochi.github@404.ms>
 */
@Getter
@Setter
@ToString

public class Operation {

    String operationName;
    String operationEnglishAlias;
    String comment;
    @Column(name="detailJson",columnDefinition = "longtext")
    String detailJson;

    public Operation(String operationName, String operationEnglishAlias, String comment, String detailJson) {
        this.operationName = operationName;
        this.operationEnglishAlias = operationEnglishAlias;
        this.comment = comment;
        this.detailJson = detailJson;
    }

    public Operation() {
    }
}
