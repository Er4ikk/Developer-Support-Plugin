package com.unimol.developercodesupport;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.pom.Navigatable;

import org.jetbrains.annotations.NotNull;

public class MainAction extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent event) {
        // Using the event, evaluate the context,
        // and enable or disable the action.
    }


    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // Using the event, create and show a dialog
        Project currentProject = event.getProject();
        event.getPresentation().setEnabledAndVisible(currentProject != null);

        StringBuilder message =
                new StringBuilder(event.getPresentation().getText() + " Selected!");
        // If an element is selected in the editor, add info about it.
        Navigatable selectedElement = event.getData(CommonDataKeys.NAVIGATABLE);
        if (selectedElement != null) {
            message.append("\nSelected Element: ").append(selectedElement);
        }
        String title = event.getPresentation().getDescription();

        //showTabMenu(currentProject,message.toString(),title);
        //openFileChooser(currentProject);
    }

    public void showTabMenu(Project currentProject,String message, String title){
        Messages.showMessageDialog(
                currentProject,
                message,
                title,
                Messages.getInformationIcon());
    }

    public void openFileChooser(Project currentProject){
        FileChooserDescriptor fileChooserDescriptor =
                new FileChooserDescriptor(false,
                        true,
                        false,
                        false,
                        false,
                        true);

        fileChooserDescriptor.setTitle("Select folder(s) to inspect");

        fileChooserDescriptor.setDescription("items selected:");
        FileChooser.chooseFile(fileChooserDescriptor,
                                 currentProject,
                                    null,virtualFile -> {


                    Messages.showMessageDialog(currentProject,
                            virtualFile.getPath(),
                            "Path",
                            Messages.getInformationIcon());
                }
        );
    }
}
