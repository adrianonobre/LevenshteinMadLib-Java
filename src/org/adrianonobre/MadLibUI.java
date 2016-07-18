package org.adrianonobre;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by adriano on 2016-06-28.
 */
public class MadLibUI {

    private static final int WINDOW_HEIGHT = 500;

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("MadLib");
        frame.setSize(WINDOW_HEIGHT * 2, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel textLabel = new JLabel("Text:");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.EAST;
        pane.add(textLabel, c);

        JTextField textField = new JTextField("The counselour is short and the food is blend");
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        pane.add(textField, c);

        JLabel distanceLabel = new JLabel("Distance:");
        c = new GridBagConstraints();
        c.gridx = 6;
        c.gridy = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.EAST;
        pane.add(distanceLabel, c);

        SpinnerListModel monthModel = new SpinnerListModel(new Integer[]{1, 2, 3, 4, 5, 6});
        JSpinner spinner = new JSpinner(monthModel);
        spinner.setValue(3);
        c = new GridBagConstraints();
        c.gridx = 7;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 0, 100);
        pane.add(spinner, c);

        JButton goButton = new JButton("Go");
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 0, 0, 0);
        pane.add(goButton, c);

        JLabel result = new JLabel();
        result.setFont(new Font("Courier New", Font.PLAIN, 24));
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 0, 0, 0);
        pane.add(result, c);

        MadLib madLib = new MadLib("W");

        goButton.addActionListener(e -> {
            final String text = textField.getText();
            result.setText(text);

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    int distance = (Integer) spinner.getValue();
                    String newText = madLib.process(text, distance);

                    String[] originalSentence = text.split(" ");
                    String[] mutatedSentence = newText.split(" ");

                    int diff = -1;
                    for (int i = 0; i < originalSentence.length; i++) {
                        if (!originalSentence[i].equals(mutatedSentence[i])) {
                            diff = i;
                            break;
                        }
                    }

                    String preamble = diff == 0 ? "" : String.join(" ", Arrays.copyOfRange(originalSentence, 0, diff));
                    String epilogue = diff == originalSentence.length - 1 ? "" : String.join(" ", Arrays.copyOfRange(originalSentence, diff + 1, originalSentence.length));
                    String mutatedWord = originalSentence[diff];


                    for (int i =9 ; i < 50; i++) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e1) {
                        }

                        mutatedWord = munge(mutatedWord);

                        result.setText(preamble + " " + mutatedWord + " " + epilogue);
                    }

                    result.setText(newText);
                    return null;
                }
            }.execute();

        });


        frame.add(pane);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static String munge(String textRoll) {
        StringBuilder munged = new StringBuilder();
        for (char c1 : textRoll.toCharArray()) {
            if (c1 == 32) {
                munged.append(' ');
                continue;
            }
            int randomChar = (int) (((System.currentTimeMillis() + c1)) % 27);
            munged.append(Character.valueOf((char) (randomChar + 97)));
        }
        return munged.toString();
    }


}
