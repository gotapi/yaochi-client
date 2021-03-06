package de.shifen.yaochi.client.task;

import de.shifen.yaochi.client.config.YaochiConfig;
import de.shifen.yaochi.client.handler.BaseExtendedTypeHandler;
import de.shifen.yaochi.client.http.HttpUtil;
import de.shifen.yaochi.client.model.*;
import com.google.gson.Gson;
import de.shifen.yaochi.client.pojo.OperationType;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author ms404 <yaochi.github@404.ms>
 */
public class LogAttributesTask implements Runnable {
    @Resource
    HttpUtil httpUtil;
    @Resource
    YaochiConfig yaochiConfig;
    private final Operation operation;
    private final Operator operator;
    private final AmendTarget amendTarget;
    private final Long operateAt;
    private final String uuid;


    private final List<AttributeModifiedInAmendent> baseAttributeModelList;

    public LogAttributesTask(String uuid, Operator operator,Operation operation,  AmendTarget amendTarget,
                              List<AttributeModifiedInAmendent> baseAttributeModelList, Long operateAt
    ) {
        this.operation = operation;
        this.operator = operator;
        this.amendTarget = amendTarget;
        this.operateAt = operateAt;
        this.uuid = uuid;
        this.baseAttributeModelList = baseAttributeModelList;
    }

    @Override
    public void run() {
        OperationItem operationItem = new OperationItem();
        operationItem.setOperation(operation);
        operationItem.setOperator(operator);
        operationItem.setOperationType(OperationType.Amend);
        operationItem.setOperationAt(operateAt);
        operationItem.setAmendTarget(amendTarget);
        operationItem.setUuid(uuid);
        operationItem.setAppName(yaochiConfig.getBusinessAppName());

        if (baseAttributeModelList != null && !baseAttributeModelList.isEmpty()) {
            for(AttributeModifiedInAmendent amendent:baseAttributeModelList){
                operationItem.addModified(amendent);
            }
        }
        LogEventTask.sendOpItem(operationItem, yaochiConfig, httpUtil);
    }
}
