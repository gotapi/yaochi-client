package de.shifen.yaochi.client.handler;

import de.shifen.yaochi.client.model.AttributeModifiedInAmendent;
import de.shifen.yaochi.client.richtext.Html2Text;
import de.shifen.yaochi.client.wrapper.FieldWrapper;
import de.shifen.yaochi.client.richtext.RichTextHandler;

/**
 * @author ms404 <yaochi.github@404.ms>
 */

public enum BuiltinTypeHandler {
    /**
     * 普通字段
     */
    NORMAL {
        @Override
        public AttributeModifiedInAmendent handlerAttributeChange(FieldWrapper fieldWrapper) {
            AttributeModifiedInAmendent baseAttributeModel = new AttributeModifiedInAmendent();
            baseAttributeModel.setOldValue(fieldWrapper.getOldValueString());
            baseAttributeModel.setNewValue(fieldWrapper.getNewValueString());
            return baseAttributeModel;
        }
    },
    /**
     * 富文本字段;
     */
    RICHTEXT {
        @Override
        public AttributeModifiedInAmendent handlerAttributeChange(FieldWrapper fieldWrapper) {
            String simpleOldValue = Html2Text.simpleHtml(fieldWrapper.getOldValueString());
            String simpleNewValue = Html2Text.simpleHtml(fieldWrapper.getNewValueString());
            // Delete the format and leave the main content behind.
            if (simpleOldValue == null || simpleNewValue == null || simpleOldValue.equals(simpleNewValue)) {
                // The main content is the same, the same
                return null;
            } else {
                AttributeModifiedInAmendent baseAttributeModel = new AttributeModifiedInAmendent();
                baseAttributeModel.setOldValue(fieldWrapper.getOldValueString());
                baseAttributeModel.setNewValue(fieldWrapper.getNewValueString());
                baseAttributeModel.setDiffValue(RichTextHandler.diffText(fieldWrapper.getOldValueString(), fieldWrapper.getNewValueString()));
                return baseAttributeModel;
            }
        }
    };

    public abstract AttributeModifiedInAmendent handlerAttributeChange(FieldWrapper fieldWrapper);
}
