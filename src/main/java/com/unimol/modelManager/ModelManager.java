package com.unimol.modelManager;

import com.intellij.openapi.project.Project;
import com.unimol.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelManager {

    private HashMap<String,Model> availableModels;
    private Project currentProject;



    private static ModelManager instance= null;

    private ModelManager() {
        this.availableModels = new HashMap<>();
    }

    public static  ModelManager getInstance(){
        if (instance==null){
            instance= new ModelManager();
        }
        return  instance;
    }

    public Model retrievingModel(String modelName){
        ModelManager modelManager= ModelManager.getInstance();
        Model model = modelManager.getInstance().getAvailableModels().get(modelName);
       return model;
    }

    public HashMap<String,Model> getAvailableModels() {
        return availableModels;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }

    public void setAvailableModels(HashMap<String,Model> availableModels) {
        this.availableModels = availableModels;
    }
}
