package com.unimol.developercodesupport;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.pom.Navigatable;

import com.intellij.util.ui.JBUI;
import forms.MainToolWindow;
import org.jetbrains.annotations.NotNull;

import com.intellij.ui.components.JBTabbedPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainAction extends AnAction {



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

        StringBuilder message =
                new StringBuilder(event.getPresentation().getText() + " Selected!");
        // If an element is selected in the editor, add info about it.
        Navigatable selectedElement = event.getData(CommonDataKeys.NAVIGATABLE);
        if (selectedElement != null) {
            message.append("\nSelected Element: ").append(selectedElement);
        }
        String title = event.getPresentation().getDescription();

        showTabMenu(this.currentProject,getSourceFilePath(),title);
        //openFileChooser(currentProject);
    }

    public void showTabMenu(Project currentProject,String message, String title){

        MainToolWindow mainToolWindow = new MainToolWindow();
        mainToolWindow.startTool();



/*
        Messages.showMessageDialog(
                currentProject,
                message,
                title,
                Messages.getInformationIcon());

 */

    }

    public String getProjectPath(){
        return this.currentProject.getBasePath();
    }

    public String getSourceFilePath(){
        return currentProject.getPresentableUrl();
    }

    public void openFileChooser(Project currentProject){
        FileChooserDescriptor fileChooserDescriptor =
                new FileChooserDescriptor(false,
                        true,
                        false,
                        false,
                        false,
                        true);

        fileChooserDescriptor.setTitle("Select Folder(S) to Inspect");

        fileChooserDescriptor.setDescription("Items selected:");
        FileChooser.chooseFile(fileChooserDescriptor,
                                 currentProject,
                                    null,virtualFile ->
                                            Messages.showMessageDialog(currentProject,
                                            virtualFile.getPath(),
                                            "Path",
                                            Messages.getInformationIcon())
        );
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }
}
