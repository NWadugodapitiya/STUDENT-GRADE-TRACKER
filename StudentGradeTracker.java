import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StudentGradeTracker extends JFrame {

    private DefaultListModel<String> studentListModel;
    private JList<String> studentList;
    private JTextField gradeTextField;
    private JLabel averageLabel, highestLabel, lowestLabel;

    public StudentGradeTracker() {
        setTitle("Student Grade Tracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        studentListModel = new DefaultListModel<>();
        studentList = new JList<>(studentListModel);
        JScrollPane scrollPane = new JScrollPane(studentList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel gradeLabel = new JLabel("Enter Grade:");
        gradeTextField = new JTextField(10);

        JButton addGradeButton = new JButton("Add Grade");
        addGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGradeButtonClicked();
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearButtonClicked();
            }
        });

        inputPanel.add(gradeLabel);
        inputPanel.add(gradeTextField);
        inputPanel.add(addGradeButton);
        inputPanel.add(clearButton);

        JPanel statsPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        averageLabel = new JLabel("Average: ");
        highestLabel = new JLabel("Highest: ");
        lowestLabel = new JLabel("Lowest: ");

        statsPanel.add(new JLabel()); // empty cell for spacing
        statsPanel.add(new JLabel()); // empty cell for spacing
        statsPanel.add(averageLabel);
        statsPanel.add(highestLabel);
        statsPanel.add(lowestLabel);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        add(statsPanel, BorderLayout.NORTH);

        // Add KeyListener to the gradeTextField
        gradeTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addGradeButtonClicked();
                }
            }
        });
    }

    private void addGradeButtonClicked() {
        try {
            String gradeText = gradeTextField.getText();
            double grade = Double.parseDouble(gradeText);

            // Add the grade to the list
            studentListModel.addElement(gradeText);

            // Calculate and display average, highest, and lowest
            calculateStatistics();

            // Clear the gradeTextField after adding a grade
            gradeTextField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Grade! Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearButtonClicked() {
        studentListModel.clear();
        gradeTextField.setText("");
        averageLabel.setText("Average: ");
        highestLabel.setText("Highest: ");
        lowestLabel.setText("Lowest: ");
    }

    private void calculateStatistics() {
        double sum = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;

        for (int i = 0; i < studentListModel.size(); i++) {
            double grade = Double.parseDouble(studentListModel.getElementAt(i));
            sum += grade;

            if (grade > highest) {
                highest = grade;
            }

            if (grade < lowest) {
                lowest = grade;
            }
        }

        double average = sum / studentListModel.size();

        averageLabel.setText("Average: " + String.format("%.2f", average));
        highestLabel.setText("Highest: " + highest);
        lowestLabel.setText("Lowest: " + lowest);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new StudentGradeTracker().setVisible(true);
            }
        });
    }
}