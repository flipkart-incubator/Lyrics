package com.flipkart.lyrics.android.handlers;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.helper.Helper;
import com.flipkart.lyrics.model.FieldType;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.VariableModel;
import com.flipkart.lyrics.processor.fields.ObjectTypeHandler;

import java.util.function.Function;

/**
 * In android, we are not using enums and instead stringdefs are used. So all enum usages should be replaced with
 * {@link java.lang.String} type. When enums are used as member variables, it will be converted to
 * {@link java.lang.String} type by {@link AndroidEnumTypeHandler} and {@link AndroidEnumParameterTypeHandler}. When
 * enums are used as type inside any collections like {@link java.util.Map} or {@link java.util.List} it will be
 * handled in this object type handler. For this in the json schema mention the fieldType as ENUM.
 * <p />
 * Following sample json represents the declaration of {@link java.util.List} member variable with enum as type.
 *
 * <pre>
 * {@code
 * {
 *   "type": "CLASS",
 *   "fields": {
 *     "listOfShapes": {
 *       "fieldType": "OBJECT",
 *       "type": {
 *         "type": "java.util.List",
 *         "types": [
 *            {
 *                "type": "com.myexample.enum.Shape",
 *                "fieldType": "ENUM"
 *            }
 *         ]
 *       }
 *     }
 *   }
 * }
 * }
 * </pre>
 * <p />
 * Generated code will be,
 * <pre>
 * {@code
 * // Android
 * List<String> listOfShapes;
 *
 * // Other platforms
 * List<Shape> listOfShapes;
 * }
 * </pre>
 */
public class AndroidObjectTypeHandler extends ObjectTypeHandler {

    public AndroidObjectTypeHandler(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    @Override
    protected Function<VariableModel, String> getTypeFromVariableModelFunction() {
        return inlinedVariableModel -> {
            if (FieldType.ENUM == inlinedVariableModel.getFieldType()) {
                return tune.getChords().handleString();
            } else {
                return Helper.getTypeFromVariableModel(inlinedVariableModel, tune.getChords());
            }
        };
    }
}
