package com.unimol.developercodesupport;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.FileEditorManagerImpl;
import com.intellij.openapi.project.Project;

import forms.MainToolWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class BugFixing extends AnAction {



    private Project currentProject;
    private JTabbedPane tabbedPane1;

    @Override
    public void update(@NotNull AnActionEvent event) {
        // Using the event, evaluate the context,
        // and enable or disable the action.
    }


    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // Using the event, create and show a dialog

        this.currentProject = event.getProject();
        event.getPresentation().setEnabledAndVisible(this.currentProject != null);
        FileEditorManagerEx fileEditorManagerEx = new FileEditorManagerImpl(currentProject);
        Editor editor = event.getData(CommonDataKeys.EDITOR);

        if(editor.getSelectionModel().hasSelection()){
            System.out.println(editor.getSelectionModel().getSelectedText());
        }else{
            System.out.println("ahhhhhhhhhh");
        }

    }





    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }
}
