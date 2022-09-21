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
//import edu.wm.cs.src2abs.AbstractorManager;
//import edu.wm.cs.src2abs.parser.Parser;
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
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        String textSelected="test";
        if(editor.getSelectionModel().hasSelection() ){
            textSelected=editor.getSelectionModel().getSelectedText();
            if(countWords(textSelected)<50){
                ConnectionManager connectionManager = ConnectionManager.getInstance();
                String bugFixGenerated=connectionManager.makeRequest("generate bug fix",preProcessStrig(textSelected));
                VirtualFile virtualFile=event.getData(PlatformDataKeys.VIRTUAL_FILE);
                showDiff(currentProject,bugFixGenerated,virtualFile,editor);
            }else if(countWords(textSelected)>=50 && countWords(textSelected)<=99){
                ConnectionManager connectionManager = ConnectionManager.getInstance();
                String bugFixGenerated=connectionManager.makeRequest("generate medium bug fix",preProcessStrig(textSelected));
                VirtualFile virtualFile=event.getData(PlatformDataKeys.VIRTUAL_FILE);
                showDiff(currentProject,bugFixGenerated,virtualFile,editor);
            }else if (countWords(textSelected)>=100){
                JOptionPane.showMessageDialog(
                        tabbedPane1,
                        "Maximium amount of words  is 100",
                        "Too many words",
                        JOptionPane.ERROR_MESSAGE);
            }


        }else{
            JOptionPane.showMessageDialog(
                                            tabbedPane1,
                                    "Select Code First",
                                        "No Code Selected",
                                    JOptionPane.ERROR_MESSAGE);
        }
    }

     /**
      *  this method opens a show differences window (the same from GitHub showDiff tool)
      *  it takes
      *  @param currentProject the current Project opened in Intellij
      *  @param response that is the suggestion from the model
      *  @param virtualFile that is the current file open in the editor
      *  @param editor mages the content of the virtual file
     */

    public void showDiff(Project currentProject,String response,VirtualFile virtualFile,Editor editor) {
        DiffContentFactoryImpl diffContentFactory = new DiffContentFactoryImpl();
        String title = "Diff for " +virtualFile.getName();
        String title1 = "My Version";
        String title2 =  "Suggestion";
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


    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }


    /**
     * this method takes
     * @param response that is the raw selected code from the user
     * @return the string pre-processed for the model
     */
    public String preProcessStrig(String response){

        //AbstractorManager abstractionManager = new AbstractorManager();
        //abstractionManager.abstractCode(Parser.CodeGranularity.METHOD,"single","boh","bod");
        return response
                .replaceAll("\\\\n", "")
                .replaceAll("\\\\t","").trim()
                .toLowerCase();
    }

    public static int countWords(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] words = input.split("\\s+");
        return words.length;
    }



}
