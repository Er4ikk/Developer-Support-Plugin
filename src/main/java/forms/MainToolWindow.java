package forms;

import javax.swing.*;

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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void startTool(){
        JFrame frame = new JFrame("Developer Code Support");
        frame.setContentPane(tabs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
