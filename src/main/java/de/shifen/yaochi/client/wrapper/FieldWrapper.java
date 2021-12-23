package de.shifen.yaochi.client.wrapper;

import de.shifen.yaochi.client.annotation.LogTag;
import lombok.Data;

import java.lang.reflect.Field;

/**
 *
 * @author xurenlu
 */
@Data
public class FieldWrapper {
    /**
     *  属性名称
     */
    private String attributeName;
    /**
     * 注解的属性名称,如果不存在则使用attributeName
     */
    private String attributeAlias;
    /**
     *  属性的旧值
     */
    private Object oldValue;
    /**
     * 属性的新值
     */
    private Object newValue;
    /**
     *  属性旧值字符串
     */
    private String oldValueString;
    /**
     * 属性新值字符串
     */
    private String newValueString;
    /**
     * 是否有注解
     */
    private boolean withLogTag;
    /**
     * 属性注解
     */
    private LogTag logTag;
    /**
     * 是否是外部类型
     */
    private boolean withExtendedType;
    /**
     * 外部类型具体值
     */
    private String extendedType;

    public FieldWrapper(Field field, Object oldValue, Object newValue) {
        this.attributeName = field.getName();
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.oldValueString = oldValue == null ? "" : oldValue.toString();
        this.newValueString = newValue == null ? "" : newValue.toString();
        this.logTag = field.getAnnotation(LogTag.class);
        this.withLogTag = logTag != null;
        this.attributeAlias = (withLogTag && logTag.alias().length() != 0) ? logTag.alias() : field.getName();
        this.withExtendedType = withLogTag && logTag.extendedType().length() != 0;
        this.extendedType = withExtendedType ? logTag.extendedType() : null;
    }
}
