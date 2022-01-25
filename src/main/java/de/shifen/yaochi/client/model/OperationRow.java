package de.shifen.yaochi.client.model;

import de.shifen.yaochi.client.pojo.OperationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ms404 <yaochi.github@404.ms>
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "java_operation_row")
@Entity
public class OperationRow {
    @Id
    long id;

    @Column(name = "app_name", columnDefinition = "varchar(32)")
    String appName;

    /**
     * 操作ID,全局唯一，避免重记
     */
    @Column(name = "uuid", columnDefinition = "varchar(64)")
    String uuid;
    /**
     * 行为发生时间
     */
    Long operationAt;
    /**
     * 操作类型：是一条普通的行为日志，还是一个变更记录;
     */
    @Enumerated(EnumType.STRING)
    OperationType operationType;

    @OneToMany(cascade = CascadeType.ALL)
    List<AttributeModifiedInAmendent> attributeModelList = new ArrayList<>();

    public void addModified(AttributeModifiedInAmendent amendent) {
        attributeModelList.add(amendent);
    }


    String operatorId;
    String operatorName;
    String operatorAddonInformation;

    String operationName;
    String operationEnglishAlias;
    String comment;
    String detailJson;

    String targetId;
    String targetName;


    public OperationItem toItem(){
        OperationItem item = new OperationItem();

        item.setOperationType(this.getOperationType());
        item.setOperationAt(this.getOperationAt());
        item.setAppName(this.getAppName());
        item.setUuid(this.getUuid());
        item.setAttributeModelList(this.getAttributeModelList());
        item.setId(this.getId());

        item.setOperator(new Operator(this.getOperatorId(),this.getOperatorName(),this.getOperatorAddonInformation()));
        item.setOperation(new Operation(this.getOperationName(),this.getOperationEnglishAlias(),this.getComment(),this.getDetailJson()));
        item.setAmendTarget(new AmendTarget(this.getTargetId(),this.getTargetName()));

        return item;
    }
}
