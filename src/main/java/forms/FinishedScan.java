package forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public FinishedScan() {
        viewResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    okButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }

    }
    );
    }

    public JTabbedPane getTabs(){
        return this.tabs;
    }
}
