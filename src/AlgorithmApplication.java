import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class AlgorithmApplication extends JFrame implements ActionListener {
    private JTextArea inputArea;
    private JButton sortButton, searchButton;
    private JComboBox<String> algorithmComboBox;
    private JTextArea outputArea;
// Github Repo -- https://github.com/SulemanaAbu/DSA-11355543--Assignment3.git
    public AlgorithmApplication() {
        setTitle("11355543 Algorithm Application");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inputArea = new JTextArea(10, 40);
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);

        sortButton = new JButton("Sort");
        searchButton = new JButton("Search");

        String[] algorithms = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort",
                "Breadth-First Search", "Depth-First Search", "Dijkstra's Algorithm"};
        algorithmComboBox = new JComboBox<>(algorithms);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Input List:"));
        panel.add(new JScrollPane(inputArea));
        panel.add(new JLabel("Algorithm:"));
        panel.add(algorithmComboBox);
        panel.add(sortButton);
        panel.add(searchButton);
        panel.add(new JLabel("Output:"));
        panel.add(new JScrollPane(outputArea));

        sortButton.addActionListener(this);
        searchButton.addActionListener(this);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AlgorithmApplication().setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = inputArea.getText().trim();
        String[] inputArray = input.split("\\s+");
        List<Integer> list = new ArrayList<>();

        try {
            for (String s : inputArray) {
                list.add(Integer.parseInt(s));
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please enter integers separated by spaces.");
            return;
        }

        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        long startTime, endTime;

        if (e.getSource() == sortButton) {
            if (selectedAlgorithm.contains("Sort")) {
                startTime = System.nanoTime();
                switch (selectedAlgorithm) {
                    case "Bubble Sort":
                        bubbleSort(list);
                        break;
                    case "Selection Sort":
                        selectionSort(list);
                        break;
                    case "Insertion Sort":
                        insertionSort(list);
                        break;
                    case "Merge Sort":
                        list = mergeSort(list);
                        break;
                }
                endTime = System.nanoTime();
                outputArea.setText("Sorted List: " + list + "\nTime taken: " + (endTime - startTime) + " ns");
                promptUser();
            } else {
                outputArea.setText("Please select a sorting algorithm.");
            }
        } else if (e.getSource() == searchButton) {
            if (selectedAlgorithm.contains("Search")) {
                // Implement search functionality here based on the selected algorithm.
                outputArea.setText("Search functionality not implemented in this example.");
                promptUser();
            } else {
                outputArea.setText("Please select a searching algorithm.");
            }
        }
    }

    // Bubble Sort
    private void bubbleSort(List<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    Collections.swap(list, j, j + 1);
                }
            }
        }
    }

    // Selection Sort
    private void selectionSort(List<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j) < list.get(minIdx)) {
                    minIdx = j;
                }
            }
            Collections.swap(list, i, minIdx);
        }
    }

    // Insertion Sort
    private void insertionSort(List<Integer> list) {
        int n = list.size();
        for (int i = 1; i < n; ++i) {
            int key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j) > key) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }
    }

    // Merge Sort
    private List<Integer> mergeSort(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }
        int mid = list.size() / 2;
        List<Integer> left = new ArrayList<>(list.subList(0, mid));
        List<Integer> right = new ArrayList<>(list.subList(mid, list.size()));

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }
        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }
        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }
        return result;
    }

    private void promptUser() {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to perform another operation?", "Continue",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION) {
            System.exit(0);
        } else {
            inputArea.setText("");
            outputArea.setText("");
        }
    }
}
