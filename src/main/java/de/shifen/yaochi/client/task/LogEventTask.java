package de.shifen.yaochi.client.task;

import com.google.gson.Gson;
import de.shifen.yaochi.client.config.YaochiConfig;
import de.shifen.yaochi.client.http.HttpUtil;
import de.shifen.yaochi.client.model.Operation;
import de.shifen.yaochi.client.model.OperationItem;
import de.shifen.yaochi.client.model.Operator;
import de.shifen.yaochi.client.pojo.OperationType;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

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

        sendOpItem(operationItem, yaochiConfig, httpUtil);
    }

    static void sendOpItem(OperationItem operationItem, YaochiConfig yaochiConfig, HttpUtil httpUtil) {
        if(yaochiConfig.getBasicAuthName().length()>0){
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials
                    = new UsernamePasswordCredentials(yaochiConfig.getBasicAuthName(), yaochiConfig.getBasicAuthPass());
            provider.setCredentials(AuthScope.ANY, credentials);
            httpUtil.sendLog(new Gson().toJson(operationItem),provider);

        }else {
            httpUtil.sendLog(new Gson().toJson(operationItem));
        }
    }
}
