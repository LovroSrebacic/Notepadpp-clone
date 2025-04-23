package main.java.components;

import main.java.local.ILocalizationProvider;
import main.java.local.LJLabel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatusBar extends JPanel {
    private JLabel lengthLabel;
    private JLabel caretInfoLabel;
    private JLabel clockLabel;

    private final SimpleDateFormat dateTimeFormat;
    private final ILocalizationProvider provider;

    public StatusBar(ILocalizationProvider provider) {
        super(new GridLayout(1, 2));
        this.provider = provider;
        this.dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        initComponents();
    }

    private void initComponents() {
        lengthLabel = new LJLabel("lengthEmpty", provider);

        JPanel rightPanel = new JPanel(new GridLayout(1, 2));

        caretInfoLabel = new LJLabel("infoLineEmpty", provider);

        clockLabel = new JLabel();
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        rightPanel.add(caretInfoLabel);
        rightPanel.add(clockLabel);

        add(lengthLabel);
        add(rightPanel);
    }

    public void updateDocumentLength(int length) {
        lengthLabel.setText(String.format(provider.getString("length"), length));
    }

    public void updateCaretInfo(int line, int column, int selectionLength) {
        caretInfoLabel.setText(String.format(provider.getString("infoLine"), line, column, selectionLength));
    }

    public void setEmptyCaretInfo() {
        caretInfoLabel.setText(provider.getString("infoLineEmpty"));
    }

    public void setEmptyInfo() {
        lengthLabel.setText(provider.getString("lengthEmpty"));
        setEmptyCaretInfo();
    }

    public void startClock() {
        updateClock();

        Thread clockThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                SwingUtilities.invokeLater(this::updateClock);
            }
        });
        clockThread.setDaemon(true);
        clockThread.start();
    }

    private void updateClock() {
        String time = dateTimeFormat.format(new Date());
        clockLabel.setText(time);
        repaint();
    }
}
