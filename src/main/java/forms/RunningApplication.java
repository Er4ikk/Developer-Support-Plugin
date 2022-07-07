package forms;

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

    public RunningApplication(JFrame frame) {

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FinishedScan finishedScan = new FinishedScan();

                frame.setContentPane(finishedScan.getTabs());
                frame.validate();
            }
        });
    }

    public JTabbedPane getTabs(){
        return this.tabs;
    }
}
