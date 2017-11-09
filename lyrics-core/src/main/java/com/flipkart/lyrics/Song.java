/*
 * Copyright 2016 Flipkart Internet, pvt ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flipkart.lyrics;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.model.VariableModel;
import com.flipkart.lyrics.sets.HandlerSet;
import com.flipkart.lyrics.sets.RuleSet;
import com.flipkart.lyrics.specs.TypeSpec;

import java.io.File;
import java.io.IOException;

import static com.flipkart.lyrics.helper.Helper.getCreator;
import static com.flipkart.lyrics.helper.Helper.getTypeVariables;
import static com.flipkart.lyrics.helper.Injector.*;

/**
 * Created by shrey.garg on 27/11/16.
 */
public class Song {

    private Tune tune;

    public Song(Tune tune) {
        this.tune = tune;
    }

    public void createType(String name, String fullPackage, TypeModel typeModel, File targetFolder, TypeModel extendsTypeModel) throws IOException {
        MetaInfo metaInfo = new MetaInfo(name, fullPackage, getTypeVariables(typeModel.getGenericVariables()));
        if (extendsTypeModel != null) {
            metaInfo.setSuperClassFields(extendsTypeModel.getFields());
        }
        RuleSet ruleSet = processRuleSet(tune, metaInfo);
        HandlerSet handlerSet = processHandlerSet(tune, metaInfo, ruleSet);
        processFieldTypeHandlerSet(tune, metaInfo);
        processParameterTypeHandlerSet(tune, metaInfo);
        processFieldAdditionalHandlers(tune, metaInfo);
        processTypeAdditionalHandlers(tune, metaInfo);
        processFieldModificationHandlers(tune, metaInfo);

        TypeSpec.Builder typeBuilder = getCreator(typeModel.getType(), tune.getCreatorSet()).process(handlerSet, typeModel);
        tune.getFileWriter().writeToFile(typeBuilder.build(), fullPackage, targetFolder);
    }
}
