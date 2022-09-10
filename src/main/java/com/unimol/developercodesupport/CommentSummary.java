package com.unimol.developercodesupport;

import com.intellij.diff.DiffContentFactoryImpl;
import com.intellij.diff.DiffManagerImpl;
import com.intellij.diff.contents.DiffContent;
import com.intellij.diff.requests.DiffRequest;
import com.intellij.diff.requests.SimpleDiffRequest;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.unimol.connectionManager.ConnectionManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.net.http.HttpResponse;

public class CommentSummary extends AnAction {
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
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        String response="test";
        if(editor.getSelectionModel().hasSelection()){
            response=editor.getSelectionModel().getSelectedText();
            ConnectionManager connectionManager = ConnectionManager.getInstance();
            String summaryGenerated=connectionManager.makeRequest("generate comment summary",preProcessStrig(response));
            VirtualFile virtualFile=event.getData(PlatformDataKeys.VIRTUAL_FILE);
            showDiff(currentProject,"\n /* "+summaryGenerated+" */ \n"+response,virtualFile,editor);

        }else{
            JOptionPane.showMessageDialog(
                    tabbedPane1,
                    "Select Code First",
                    "No Code Selected",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void showDiff(Project currentProject, String response, VirtualFile virtualFile, Editor editor) {
        DiffContentFactoryImpl diffContentFactory = new DiffContentFactoryImpl();
        String title = "Diff for " +virtualFile.getName();
        String title1 = "My Version";
        String title2 =  "Suggestions";
        DiffContent content1 = diffContentFactory.createFragment(
                currentProject,
                editor.getDocument(),
                new TextRange(editor.getSelectionModel().getSelectionStart(),
                        editor.getSelectionModel().getSelectionEnd()));

        DiffContent content2 = diffContentFactory.create(response,virtualFile);
        DiffRequest request = new SimpleDiffRequest(title, content1, content2, title1, title2);
        DiffManagerImpl diffManager = new DiffManagerImpl();
        diffManager.showDiffBuiltin(currentProject,request);

    }

    public String preProcessStrig(String response){


        return response
                .replace('}',' ')
                .replace('{', ' ')
                .replace('(',' ')
                .replace(')',' ')
                .replace(']',' ')
                .replace('[',' ')
                .replace('"',' ')
                .replace('\'',' ')
                .toLowerCase();
    }
}
