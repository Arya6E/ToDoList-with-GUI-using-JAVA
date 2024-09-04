import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class toDoList extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskField;
    private JButton addButton;
    private JButton removeButton;
    private JButton markCompletedButton;
    private int taskCounter = 1; // Counter for serial numbers

    public toDoList() {
        setTitle("To-Do List");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color to #C70039
        getContentPane().setBackground(Color.decode("#C70039"));

        // Initialize components
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskField = new JTextField(20);

        // Initialize buttons with color #FFC300
        addButton = new JButton("Add Task");
        addButton.setBackground(Color.decode("#FFC300"));
        removeButton = new JButton("Remove Task");
        removeButton.setBackground(Color.decode("#FFC300"));
        markCompletedButton = new JButton("Mark as Completed");
        markCompletedButton.setBackground(Color.decode("#FFC300"));

        // Set panel backgrounds to #C70039
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.decode("#C70039"));
        inputPanel.add(taskField);
        inputPanel.add(addButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode("#C70039"));
        buttonPanel.add(removeButton);
        buttonPanel.add(markCompletedButton);

        // Add components to the frame
        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });

        markCompletedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markTaskAsCompleted();
            }
        });

        setVisible(true);
    }

    private void addTask() {
        String task = taskField.getText();
        if (!task.isEmpty()) {
            taskListModel.addElement(taskCounter + ". " + task);
            taskField.setText("");
            taskCounter++;
        } else {
            JOptionPane.showMessageDialog(this, "Task cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
            renumberTasks();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markTaskAsCompleted() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String task = taskListModel.getElementAt(selectedIndex);
            task = task.replaceFirst("\\. ", ". ✓ "); // Replace the serial number with ✓
            taskListModel.set(selectedIndex, task);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as completed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void renumberTasks() {
        taskCounter = 1;
        for (int i = 0; i < taskListModel.size(); i++) {
            String task = taskListModel.getElementAt(i);
            task = task.replaceFirst("^\\d+\\. ", taskCounter + ". ");
            taskListModel.set(i, task);
            taskCounter++;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new toDoList());
    }
}

