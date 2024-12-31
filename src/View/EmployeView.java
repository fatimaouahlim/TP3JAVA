
package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Enum.Poste;
import Enum.Role;
import java.awt.*;

public class EmployeView extends JPanel {
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;

    public JLabel nomLabel, prenomLabel, telephoneLabel, emailLabel, salaireLabel, roleLabel, posteLabel;
    public JTextField nomField;
    public JTextField prenomField;
    public JTextField telephoneField;
    public JTextField emailField;
    public JTextField salaireField;
    public JComboBox<Role> roleDropdown;
    public JComboBox<Poste> posteDropdown;
    public JTable employeTable;
    public DefaultTableModel tableModel;
    public JScrollPane tableScrollPane;
    public JButton addButton, updateButton, deleteButton, displayButton,appliquerButton,chargerButton,importerButton;

    public EmployeView() {
        setLayout(new BorderLayout());

        // Panels
        mainPanel = new JPanel(new BorderLayout());
        formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        buttonPanel = new JPanel(new FlowLayout());

        // Labels
        nomLabel = new JLabel("Nom:");
        prenomLabel = new JLabel("Prénom:");
        telephoneLabel = new JLabel("Téléphone:");
        emailLabel = new JLabel("Email:");
        salaireLabel = new JLabel("Salaire:");
        roleLabel = new JLabel("Rôle:");
        posteLabel = new JLabel("Poste:");


        // Text Fields
        nomField = new JTextField(10);
        prenomField = new JTextField(10);
        telephoneField = new JTextField(10);
        emailField = new JTextField(10);
        salaireField = new JTextField(10);

        // Dropdowns
        roleDropdown = new JComboBox<>(Role.values());
        posteDropdown = new JComboBox<>(Poste.values());

        // Add components to form panel
        formPanel.add(nomLabel);
        formPanel.add(nomField);
        formPanel.add(prenomLabel);
        formPanel.add(prenomField);
        formPanel.add(telephoneLabel);
        formPanel.add(telephoneField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(salaireLabel);
        formPanel.add(salaireField);
        formPanel.add(roleLabel);
        formPanel.add(roleDropdown);
        formPanel.add(posteLabel);
        formPanel.add(posteDropdown);

        // Table
        String[] columnNames = {"ID", "Nom", "Prénom", "Téléphone", "Email", "Salaire", "Rôle", "Poste","Jours de congé restants"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(employeTable);

        // Buttons
        addButton = new JButton("Ajouter");
        updateButton = new JButton("Modifier");
        appliquerButton = new JButton("Appliquer");
        deleteButton = new JButton("Supprimer");
        displayButton = new JButton("Refrecher");
        chargerButton = new JButton("Exporter");
        importerButton = new JButton("Importer");

        // Add buttons to button panel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(appliquerButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(chargerButton);
        buttonPanel.add(importerButton);

        // Main panel layout
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Final setup
        add(mainPanel);
    }

    // Getter methods for all components
    public JTextField getNomField() { return nomField; }
    public JTextField getPrenomField() { return prenomField; }
    public JTextField getTelephoneField() { return telephoneField; }
    public JTextField getEmailField() { return emailField; }
    public JTextField getSalaireField() { return salaireField; }
    public JComboBox<Role> getRoleDropdown() { return roleDropdown; }
    public JComboBox<Poste> getPosteDropdown() { return posteDropdown; }
    public JTable getEmployeTable() { return employeTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getAddButton() { return addButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getDisplayButton() { return displayButton; }
}
