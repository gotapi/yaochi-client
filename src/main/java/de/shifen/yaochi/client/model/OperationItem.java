package de.shifen.yaochi.client.model;

import de.shifen.yaochi.client.pojo.OperationType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ms404 <yaochi.github@404.ms>
 */
@Data
public class OperationItem {

    long id;

    String appName;

    /**
     * 操作人
     */
    Operator operator;
    /**
     * 操作ID,全局唯一，避免重记
     */
    String uuid;
    Long operationAt;
    /**
     * 操作类型：是一条普通的行为日志，还是一个变更记录;
     */
    @Enumerated(EnumType.STRING)
    OperationType operationType;

    /**
     * 如果是一条数据变更，则记录下变更的是哪个对象 ;
     */
    AmendTarget amendTarget;

    Operation operation;

    List<AttributeModifiedInAmendent> attributeModelList=new ArrayList<>();
    public void addModified(AttributeModifiedInAmendent amendent){
        attributeModelList.add(amendent);
    }

    public OperationRow toDbRow(){
        OperationRow row = new OperationRow();

        row.setOperationAt(this.getOperationAt());
        row.setAppName(this.getAppName());
        row.setOperationType(this.getOperationType());
        row.setUuid(this.getUuid());
        row.setAttributeModelList(this.getAttributeModelList());

        row.setComment(this.getOperation().getComment());
        row.setOperationName(this.getOperation().getOperationName());
        row.setDetailJson(this.getOperation().getDetailJson());
        row.setOperationEnglishAlias(this.getOperation().getOperationEnglishAlias());

        row.setOperatorId(this.getOperator().getOperatorId());
        row.setOperatorName(this.getOperator().getOperatorName());
        row.setOperatorAddonInformation(this.getOperator().getOperatorAddonInformation());

        row.setTargetId(this.getAmendTarget().getTargetId());
        row.setTargetName(this.getAmendTarget().getTargetName());

        return row;
    }

}
