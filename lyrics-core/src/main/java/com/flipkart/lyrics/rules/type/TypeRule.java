package com.flipkart.lyrics.rules.type;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.TypeModel;
import com.squareup.javapoet.TypeSpec;

/**
 * Created by shrey.garg on 30/12/16.
 */
public interface TypeRule {
    void process(TypeSpec.Builder typeSpec, TypeModel typeModel, Tune configuration);
}
