package de.shifen.yaochi.client.task;

import com.sun.istack.NotNull;
import de.shifen.yaochi.client.config.YaochiConfig;
import de.shifen.yaochi.client.handler.BaseExtendedTypeHandler;
import de.shifen.yaochi.client.handler.BuiltinTypeHandler;
import de.shifen.yaochi.client.http.HttpUtil;
import de.shifen.yaochi.client.model.*;
import de.shifen.yaochi.client.pojo.OperationType;
import de.shifen.yaochi.client.wrapper.ClazzWrapper;
import de.shifen.yaochi.client.wrapper.FieldWrapper;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author ms404 <yaochi.github@404.ms>
 */
public class LogObjectTask implements Runnable {
    private final Operation operation;
    private final Operator operator;
    private final AmendTarget amendTarget;
    private final Object oldObject;
    private final Object newObject;
    @Resource
    HttpUtil httpUtil;
    @Resource
    YaochiConfig yaochiConfig;
    private final Long operateAt;
    private final String uuid;

    public LogObjectTask(String uuid , Operation operation,
                         Operator operator, AmendTarget amendTarget,
                         @NotNull Object oldObject, @NotNull Object newObject,
                        Long operateAt) {
        this.operation = operation;
        this.operator = operator;
        this.amendTarget = amendTarget;
        this.oldObject = oldObject;
        this.newObject = newObject;
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
            operationItem.setAmendTarget(amendTarget);
            operationItem.setUuid(uuid);
            operationItem.setAppName(yaochiConfig.getBusinessAppName());

            // handle attributes of operation
            Class<?> modelClazz = newObject.getClass();
            Class<?> oldModelClazz = oldObject.getClass();
            if (!oldModelClazz.equals(modelClazz)) {
                throw new RuntimeException("class of old-object must be equal to class of new-object");
            }

            /*
             *  issue #1
             */
            ClazzWrapper clazzWrapper = new ClazzWrapper(modelClazz);
            List<Field> fieldList = clazzWrapper.getFieldList();
            for (Field field : fieldList) {
                field.setAccessible(true);
                FieldWrapper fieldWrapper = null;
                try {
                    fieldWrapper = new FieldWrapper(field, field.get(oldObject), field.get(newObject));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException("get field failed",e );
                }
                if (fieldWrapper.isWithLogTag() || "true".equals(yaochiConfig.getAutoLogAttributes())) {
                    if (!nullableEquals(fieldWrapper.getOldValue(), fieldWrapper.getNewValue())) {
                        AttributeModifiedInAmendent baseAttributeModel;
                        if (fieldWrapper.isWithExtendedType()) {
                            baseAttributeModel = handleExtendedTypeItem(fieldWrapper);
                        } else {
                            baseAttributeModel = handleBuiltinTypeItem(fieldWrapper);
                        }

                        if (baseAttributeModel != null) {
                            operationItem.addModified(baseAttributeModel);
                        }
                    }
                }
            }
            LogEventTask.sendOpItem(operationItem,yaochiConfig,httpUtil);
    }

    private AttributeModifiedInAmendent handleBuiltinTypeItem(FieldWrapper fieldWrapper) {
        BuiltinTypeHandler builtinType = BuiltinTypeHandler.NORMAL;
        if (fieldWrapper.getLogTag() != null) {
            builtinType = fieldWrapper.getLogTag().builtinType();
        }

        AttributeModifiedInAmendent handlerOutput = builtinType.handlerAttributeChange(fieldWrapper);

        if (handlerOutput != null) {
            // 固定值
            handlerOutput.setAttributeName(fieldWrapper.getAttributeName());
            handlerOutput.setAttributeAlias(fieldWrapper.getAttributeAlias());
            handlerOutput.setAttributeType(builtinType.name());
            return handlerOutput;
        } else {
            return null;
        }
    }

    private AttributeModifiedInAmendent handleExtendedTypeItem(FieldWrapper fieldWrapper) {
        AttributeModifiedInAmendent baseAttributeModel = BuiltinTypeHandler.NORMAL.handlerAttributeChange(
               fieldWrapper
        );

        if (baseAttributeModel != null) {
            if (baseAttributeModel.getAttributeType() == null) {
                baseAttributeModel.setAttributeType(fieldWrapper.getExtendedType());
            }
            if (baseAttributeModel.getAttributeName() == null) {
                baseAttributeModel.setAttributeName(fieldWrapper.getAttributeName());
            }
            if (baseAttributeModel.getAttributeAlias() == null) {
                baseAttributeModel.setAttributeAlias(fieldWrapper.getAttributeAlias());
            }
        }

        return baseAttributeModel;
    }

    // issue #2
    private boolean nullableEquals(Object a, Object b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }
}
