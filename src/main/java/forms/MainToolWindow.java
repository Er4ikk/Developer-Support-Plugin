package forms;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.unimol.model.Model;
import com.unimol.modelManager.ModelManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MainToolWindow {
    private  ModelManager modelManage;
    private JTabbedPane tabs;
    private JPanel panel1;
    private JPanel settings;
    private JPanel startScan;
    private JPanel stopScan;
    private JLabel programmingLanguageLabel;
    private JComboBox selectProgrammingLanguageDropDown;
    private JLabel selectCodingTaskLabel;
    private JComboBox scanScapeDropDown;
    private JLabel scanScopeLabel;
    private JButton startButton;
    private JLabel stopScanLabel;
    private JComboBox selectCodingTaskDropDown;
    private  JFrame frame ;
    public MainToolWindow(Project currentProject) {
            addingStartButtonBehaviour(currentProject);
    }

    public void addingStartButtonBehaviour(Project currentProject){
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String programmingLanguage=selectProgrammingLanguageDropDown.getSelectedItem().toString();
                String codingTask=selectCodingTaskDropDown.getSelectedItem().toString();
                String scanScope=scanScapeDropDown.getSelectedItem().toString();
                String modelName="test";

                createModel(modelName,codingTask,programmingLanguage);


                if(scanScope.contains("Directory")){
                    openFileChooser(currentProject,modelName);
                }

                    modelManage.setCurrentProject(currentProject);
                    //openBottomToolBar();

                    RunningApplication runningApplication= new RunningApplication(frame,modelName);
                    frame.setContentPane(runningApplication.getTabs());
                    frame.validate();


           }
        });

    }



    public void createModel(String modelName,String codingTask,String programmingLanguage){
        Model model = new Model(modelName,codingTask,programmingLanguage);
        modelManage= ModelManager.getInstance();
        modelManage.getAvailableModels().put(modelName,model);
    }


    public void openFileChooser(Project currentProject,String modelName){
        FileChooserDescriptor fileChooserDescriptor =
                new FileChooserDescriptor(false,
                        true,
                        false,
                        false,
                        false,
                        false);

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



    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void startTool(){
        frame = new JFrame("Developer Code Support");
        frame.setContentPane(tabs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setWindowDimensionAndPosition();
        frame.pack();
        frame.setVisible(true);
    }


    public void setWindowDimensionAndPosition(){
        frame.setPreferredSize(new Dimension(400,300));
        Dimension screenDimension= new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        int screenSize=(int)screenDimension.getWidth();
        frame.setLocation(screenSize,100);
    }
}
