package forms;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainToolWindow {
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
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String programmingLanguage=selectProgrammingLanguageDropDown.getSelectedItem().toString();
                String codingTask=selectCodingTaskDropDown.getSelectedItem().toString();
                String scanScope=scanScapeDropDown.getSelectedItem().toString();
                System.out.println(selectProgrammingLanguageDropDown.getSelectedItem().toString());
                System.out.println(selectCodingTaskDropDown.getSelectedItem().toString());
                System.out.println(scanScapeDropDown.getSelectedItem().toString());

                if(scanScope.contains("Directory")){
                    openFileChooser(currentProject);
                }
                RunningApplication runningApplication= new RunningApplication(frame);
                frame.setContentPane(runningApplication.getTabs());
                frame.validate();



            }
        });
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



    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void startTool(){
        frame = new JFrame("Developer Code Support");
        frame.setContentPane(tabs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,300));
        Dimension screenDimension= new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        int screenSize=(int)screenDimension.getWidth();
        frame.setLocation(screenSize,100);
        frame.pack();
        frame.setVisible(true);
    }
}
