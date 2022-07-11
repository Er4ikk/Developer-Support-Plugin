package forms;

import com.unimol.model.Model;
import com.unimol.modelManager.ModelManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunningApplication {
    private JPanel panel1;
    private JTabbedPane tabs;
    private JPanel settings;
    private JLabel programmingLanguageLabel;
    private JLabel selectCodingTaskLabel;
    private JPanel startScan;
    private JLabel scanScopeLabel;
    private JPanel stopScan;
    private JLabel languageSelectedLabel;
    private JLabel taskSelectedLabel;
    private JProgressBar progressBar1;
    private JLabel loadingLabel;
    private JLabel detailsLabel;
    private JLabel detailsExplainedLabel;
    private JLabel timePassedLabel;
    private JLabel timePassed;
    private JButton startButton;
    private JLabel scanningLabel;
    private JLabel loadingLabelStopScan;
    private JFrame frame;


    private String modelName;



    public RunningApplication(JFrame frame, String modelName) {


        setFrame(frame);
        showOptionsModelSelected(modelName);
        addStartButtonBehaviour();

    }

    public void showOptionsModelSelected(String modelName){
        Model model = ModelManager.getInstance().retrievingModel(modelName);
        languageSelectedLabel.setText(model.getProgrammingLanguage());
        taskSelectedLabel.setText(model.getCodingTask());
        setModelName(modelName);
    }



    public void addStartButtonBehaviour(){
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FinishedScan finishedScan = new FinishedScan(getModelName());

                frame.setContentPane(finishedScan.getTabs());
                frame.validate();
            }
        });
    }


    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }


    public JTabbedPane getTabs(){
        return this.tabs;
    }

}
