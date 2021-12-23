package de.shifen.yaochi.client.task;

import com.google.gson.Gson;
import de.shifen.yaochi.client.config.YaochiConfig;
import de.shifen.yaochi.client.http.HttpUtil;
import de.shifen.yaochi.client.model.Operation;
import de.shifen.yaochi.client.model.OperationItem;
import de.shifen.yaochi.client.model.Operator;
import de.shifen.yaochi.client.pojo.OperationType;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;

/**
 * @author xurenlu
 */
public class LogEventTask implements Runnable{

    private final Operation operation;
    private final Operator operator;
    private final Long operateAt;
    @Resource
    HttpUtil httpUtil;
    @Resource
    YaochiConfig yaochiConfig;
    private final String uuid;



    public LogEventTask(String uuid,  Operator operator,Operation operation, Long operateAt) {
        this.operation = operation;
        this.operator = operator;
        this.operateAt = operateAt;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        OperationItem operationItem = new OperationItem();
        operationItem.setOperation(operation);
        operationItem.setOperator(operator);
        operationItem.setOperationType(OperationType.Amend);
        operationItem.setOperationAt(operateAt);
        operationItem.setOperationType(OperationType.Action);
        operationItem.setUuid(uuid);
        operationItem.setAppName(yaochiConfig.getBusinessAppName());

        httpUtil.sendLog(new Gson().toJson(operationItem));
    }
}
