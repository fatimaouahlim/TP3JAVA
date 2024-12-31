package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Enum.HolidayType;
import java.awt.*;


public class HolidayView extends JFrame {
    private JTabbedPane tabbedPane;

    
    private JComboBox<String> employeeNameComboBox;

  
   
   

    // Holiday Panel Components
    private JTextField startField;
    private JTextField endField;
    private JComboBox<HolidayType> typeBox;

    // Button
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton listButton;
    public JButton importButton;
    public JButton exportButton;

	public JButton appliquer;

    // Table and Display
    private JTable holidayTable;
    private JTextArea displayArea;

    // Employee Panel Components
    private EmployeView employeView;

    public HolidayView() {
        // Set up the JFrame properties
        setTitle("Gestion des Employes et les  Congés");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window

        // Layout setup
        setLayout(new BorderLayout());

        // Initialize TabbedPane
        tabbedPane = new JTabbedPane();

        // Holiday Panel
        JPanel holidayPanel = createHolidayPanel();
        tabbedPane.addTab("Gestion des Congés", holidayPanel);

        // Employee Panel
        employeView = new EmployeView();
        tabbedPane.addTab("Gestion des Employés", employeView);

        // Add the tabbed pane to the frame
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createHolidayPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        startField = new JTextField();
        endField = new JTextField();
     
        typeBox = new JComboBox<>(HolidayType.values());
        employeeNameComboBox = new JComboBox<>();

        inputPanel.add(new JLabel("Employé:"));
        inputPanel.add(employeeNameComboBox);
        inputPanel.add(new JLabel("Date début (yyyy-MM-dd):"));
        inputPanel.add(startField);
        inputPanel.add(new JLabel("Date fin (yyyy-MM-dd):"));
        inputPanel.add(endField);
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeBox);

        // Table setup
        String[] columnNames = {"ID", "Nom de l'employe ", "Date Début", "Date Fin", "Type"};

        holidayTable = new JTable(new DefaultTableModel(columnNames, 0));
        JScrollPane tableScrollPane = new JScrollPane(holidayTable);

        // Display area
        displayArea = new JTextArea(5, 50);
        displayArea.setEditable(false);
        JScrollPane displayScrollPane = new JScrollPane(displayArea);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Ajouter");
        deleteButton = new JButton("Supprimer");
        updateButton = new JButton("Mettre à jour");
        listButton = new JButton("Refrecher");
        appliquer= new JButton("Appliquer");
        exportButton= new JButton("Exporter");
        importButton= new JButton("Importer");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(listButton);
        buttonPanel.add(appliquer);
        buttonPanel.add(exportButton);
        buttonPanel.add(importButton);
        // Top panel layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);

        // Combine components
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(displayScrollPane, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Add buttons below the table

        return mainPanel;
    }


    // Getter methods
    public JTextField getStartField() { return startField; }
    public JTextField getEndField() { return endField; }
    public JComboBox<HolidayType> getTypeBox() { return typeBox; }
    public JButton getAddButton() { return addButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getListButton() { return listButton; }
    public JTable getHolidayTable() { return holidayTable; }
    public JTextArea getDisplayArea() { return displayArea; }
    public EmployeView getEmployeView() { return employeView; }
    public JComboBox<String> getEmployeeNameComboBox() {
        return employeeNameComboBox;
    }

}
