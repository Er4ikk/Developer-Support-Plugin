package com.unimol.model;

import javax.swing.*;

public class Model {

    private String modelName;
    private String codingTask;
    private String programmingLanguage;
    public Model(String name, String codingTask, String programmingLanguage) {
        this.modelName = name;
        this.codingTask = codingTask;
        this.programmingLanguage = programmingLanguage;
    }

    public String getName() {
        return modelName;
    }

    public void setName(String name) {
        this.modelName = name;
    }
    public String getCodingTask() {
        return codingTask;
    }

    public void setCodingTask(String codingTask) {
        this.codingTask = codingTask;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }
}
