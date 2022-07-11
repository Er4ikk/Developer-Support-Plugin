package forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.impl.InternalDecorator;
import com.intellij.openapi.wm.impl.InternalDecoratorImpl;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentFactoryImpl;
import com.intellij.ui.content.impl.ContentManagerImpl;
import com.intellij.util.ui.JBUI;
import com.unimol.model.Model;
import com.unimol.modelManager.ModelManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class FinishedScan {
    private JTabbedPane tabs;
    private JPanel settings;
    private JLabel programmingLanguageLabel;
    private JLabel selectCodingTaskLabel;
    private JLabel languageSelectedLabel;
    private JLabel taskSelectedLabel;
    private JPanel startScan;
    private JLabel scanScopeLabel;
    private JLabel timePassedLabel;
    private JLabel timePassed;
    private JPanel stopScan;
    private JButton viewResultsButton;
    private JLabel scanFinishedLabel;
    private JButton okButton;
    private JFrame frame;

    public FinishedScan(String modelName) {
        showOptionsModelSelected(modelName);
        addingOkButtonBehaviour();
        addingViewResultsBehaviour();

    }

    public void addingOkButtonBehaviour(){

        okButton.addActionListener(new ActionListener() {
                                       @Override
                                       public void actionPerformed(ActionEvent actionEvent) {

                                       }

                                   }
        );
    }

    public void addingViewResultsBehaviour(){
        viewResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openBottomToolBar();
            }
        });
    }

    public void showOptionsModelSelected(String modelName){
        Model model = ModelManager.getInstance().retrievingModel(modelName);
        languageSelectedLabel.setText(model.getProgrammingLanguage());
        taskSelectedLabel.setText(model.getCodingTask());
    }


    public void openBottomToolBar(){

        ModelManager modelManager= ModelManager.getInstance();
        Project currentProject=modelManager.getCurrentProject();

        ToolWindow toolWindow= ToolWindowManager.
                getInstance(currentProject).
                getToolWindow("TODO");


        // InternalDecoratorImpl bottomToolWindow =
        if(toolWindow!=null){
            createBottomTab(toolWindow);

            toolWindow.show();
        }else{
          printBottomTabIds();

        }
    }

    public void createBottomTab(ToolWindow toolWindow){
        ResultTab resultTab  = new ResultTab();
        ContentFactoryImpl contentFactory = new ContentFactoryImpl();
        toolWindow.setStripeTitle("Support");
        toolWindow.getContentManager().removeAllContents(true);
        toolWindow.getContentManager().
                addContent(contentFactory.
                        createContent(resultTab.getPanel1(),"prova",false));
    }

    public void printBottomTabIds(){
        ModelManager modelManager= ModelManager.getInstance();
        Project currentProject=modelManager.getCurrentProject();
        System.out.println("tool window null");
        Arrays.asList(ToolWindowManager.
                getInstance(currentProject).
                getToolWindowIds()).
                forEach(s -> System.out.println(s));
    }


    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }


    public JTabbedPane getTabs(){
        return this.tabs;
    }
}
