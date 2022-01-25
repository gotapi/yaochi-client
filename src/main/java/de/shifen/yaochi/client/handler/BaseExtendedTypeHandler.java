package de.shifen.yaochi.client.handler;


import de.shifen.yaochi.client.model.AttributeModifiedInAmendent;
import org.springframework.stereotype.Component;

/**
 *  @author ms404 <yaochi.github@404.ms>
 */
@Component
public interface BaseExtendedTypeHandler {
    AttributeModifiedInAmendent handleAttributeChange(String extendedType, String attributeName, String attributeAlias, Object oldValue, Object newValue);
}
