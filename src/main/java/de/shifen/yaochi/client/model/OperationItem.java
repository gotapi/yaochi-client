package de.shifen.yaochi.client.model;

import de.shifen.yaochi.client.pojo.OperationType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xurenlu
 */
@Data
@Table(uniqueConstraints = {@UniqueConstraint(name = "idx_uuid",columnNames = "uuid")})
@Entity
public class OperationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;


    @Column(name="appName",columnDefinition = "varchar(32)")
    String appName;

    /**
     * 操作人
     */
    @OneToOne(cascade=CascadeType.ALL)
    Operator operator;
    /**
     * 操作ID,全局唯一，避免重记
     */
    @Column(name="uuid",columnDefinition = "varchar(64)")
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
    @OneToOne(cascade = CascadeType.ALL)
    AmendTarget amendTarget;

    @OneToOne(cascade = CascadeType.ALL)
    Operation operation;

    @OneToMany(cascade = CascadeType.ALL)
    List<AttributeModifiedInAmendent> attributeModelList=new ArrayList<>();

    public void addModified(AttributeModifiedInAmendent amendent){
        attributeModelList.add(amendent);
    }
}
