package de.shifen.yaochi.client.handler;


import de.shifen.yaochi.client.model.AttributeModifiedInAmendent;
import org.springframework.stereotype.Component;

/**
 *  @author ms404 <yaochi.github@404.ms>
 */
@Component
public interface BaseExtendedTypeHandler {
    /**
     * 处理属性变更
     * @param extendedType
     * @param attributeName
     * @param attributeAlias
     * @param oldValue
     * @param newValue
     * @return
     */
    AttributeModifiedInAmendent handleAttributeChange(String extendedType, String attributeName, String attributeAlias, Object oldValue, Object newValue);
}
