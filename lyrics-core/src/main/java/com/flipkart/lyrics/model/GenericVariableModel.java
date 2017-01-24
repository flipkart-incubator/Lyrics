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

package com.flipkart.lyrics.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shrey.garg on 25/11/16.
 */
public class GenericVariableModel {
    private String name;
    private List<VariableModel> bounds = new ArrayList<>();

    public GenericVariableModel() {
    }

    public GenericVariableModel(String name) {
        this.name = name;
    }

    public GenericVariableModel(String name, List<VariableModel> bounds) {
        this.name = name;
        this.bounds = bounds;
    }

    public String getName() {
        return name;
    }

    public List<VariableModel> getBounds() {
        return bounds;
    }
}
