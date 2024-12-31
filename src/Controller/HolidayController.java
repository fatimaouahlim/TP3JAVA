package Controller;

import View.HolidayView;
import Model.Employe;
import Model.Holiday;
import Model.HolidayModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import Enum.HolidayType;

import java.io.IOException;
import java.util.List;

public class HolidayController {
    private HolidayModel model;
    private HolidayView view;

    public HolidayController(HolidayModel model, HolidayView view) {
        this.model = model;
        this.view = view;
        displayHolidays();
        this.view.getAddButton().addActionListener(e -> addHoliday());
        this.view.getDeleteButton().addActionListener(e -> deleteHoliday());
        this.view.getUpdateButton().addActionListener(e ->prepareForHolidayUpdate ());
        this.view.getListButton().addActionListener(e -> displayHolidays());
        this.view.appliquer.addActionListener(e -> updateHoliday());
        this.view.exportButton.addActionListener(e -> handleExport());
        this.view.importButton.addActionListener(e -> handleImport());
        loadEmployeeNames();
    }

    private void addHoliday() {
        try {
            String employeeName = (String) view.getEmployeeNameComboBox().getSelectedItem();
            int employeeId = model.getEmployeeIdByName(employeeName);

            String dateDebut = view.getStartField().getText();
            String dateFin = view.getEndField().getText();
            HolidayType type = (HolidayType) view.getTypeBox().getSelectedItem();

            boolean success = model.add(employeeId, dateDebut, dateFin, type);
            if (success) {
                JOptionPane.showMessageDialog(view, "Congé ajouté avec succès!");
                clearInputFields();
                displayHolidays();
                displayHolidays();
            } else {
                JOptionPane.showMessageDialog(view, "Erreur : Solde insuffisant ou dates invalides.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Veuillez vérifier les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteHoliday() {
        int selectedRow = view.getHolidayTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un congé à supprimer!");
            return;
        }
        int id = (int) view.getHolidayTable().getValueAt(selectedRow, 0);
        model.delete(id);

       
        JOptionPane.showMessageDialog(view, "Congé supprimé avec succès!");
        displayHolidays();
    }

    private void prepareForHolidayUpdate() {
        int selectedRow = view.getHolidayTable().getSelectedRow();

        if (selectedRow != -1) {
            // Remplir les champs de texte avec les données de la ligne sélectionnée
            view.getStartField().setText((String) view.getHolidayTable().getValueAt(selectedRow, 2)); // Date de début
            view.getEndField().setText((String) view.getHolidayTable().getValueAt(selectedRow, 3));   // Date de fin
            view.getTypeBox().setSelectedItem(view.getHolidayTable().getValueAt(selectedRow, 4));    // Type de congé
            view.getEmployeeNameComboBox().setSelectedItem(view.getHolidayTable().getValueAt(selectedRow, 1)); // Nom de l'employé
        } else {
            // Afficher un message si aucune ligne n'est sélectionnée
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner une ligne à modifier.");
        }
    }
    private void updateHoliday() {
        int selectedRow = view.getHolidayTable().getSelectedRow();

        if (selectedRow != -1) {
            try {
                // Récupérer les données des champs
                int id = (int) view.getHolidayTable().getValueAt(selectedRow, 0); // ID de la ligne
                String start = view.getStartField().getText();
                String end = view.getEndField().getText();
                HolidayType type = (HolidayType) view.getTypeBox().getSelectedItem();
                String employeeName = (String) view.getEmployeeNameComboBox().getSelectedItem();

                // Récupérer l'ID de l'employé à partir de son nom
                int employeeId = model.getEmployeeIdByName(employeeName);

                // Validation basique des champs (optionnelle)
                if (start.isEmpty() || end.isEmpty() || employeeName == null || type == null) {
                    JOptionPane.showMessageDialog(view, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Appeler le modèle pour mettre à jour la base de données
                boolean success = model.update(id, employeeId, type, start, end);

                if (success) {
                    // Réactualiser l'affichage de la table
                    displayHolidays();

                    // Afficher un message de succès
                    JOptionPane.showMessageDialog(view, "La mise à jour a réussi.");
                } else {
                    // Afficher un message d'erreur
                    JOptionPane.showMessageDialog(view, "Erreur lors de la mise à jour.");
                }
            } catch (Exception ex) {
                // Gérer les exceptions et afficher un message d'erreur
                JOptionPane.showMessageDialog(view, "Une erreur est survenue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Afficher un message si aucune ligne n'est sélectionnée
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner une ligne à modifier.");
        }
    }


    private void clearInputFields() {
        view.getStartField().setText("");
        view.getEndField().setText("");
        view.getTypeBox().setSelectedIndex(0);
    }

    private void displayHolidays() {
        List<Holiday> holidays = model.getAllHolidays();
        DefaultTableModel tableModel = (DefaultTableModel) view.getHolidayTable().getModel();
        tableModel.setRowCount(0); 

        for (Holiday holiday : holidays) {
            String employeeName =getEmployeeNameById(holiday.getIdEmploye());
            tableModel.addRow(new Object[]{
                holiday.getId(),
                employeeName,
                holiday.getDateDebut(),
                holiday.getDateFin(),
                holiday.getType().toString()
                
            });
        }
        loadEmployeeNames();
    }

    private String getEmployeeNameById(int employeeId) {
        List<Employe> employees = getEmployees();
        for (Employe employee : employees) {
            if (employee.getId() == employeeId) {
                return employee.getNom();
            }
           
        }
        return "";
    }

    private List<Employe> getEmployees() {
        return model.getEmployees();
    }

    private void loadEmployeeNames() {
        List<String> employeeNames = model.getEmployeeNames();
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(employeeNames.toArray(new String[0]));
        view.getEmployeeNameComboBox().setModel(comboBoxModel);
    }
    public void handleImport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers CSV", "csv", "txt"));

        if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            model.importData(filePath);
            JOptionPane.showMessageDialog(view, "Importation réussie !");
        }
    }

    public void handleExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers CSV", "csv"));

        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".txt")) {
                    filePath += ".txt";
                }

                List<Holiday> holidays = model.getAllHolidays();
                model.exportData(filePath, holidays);
                JOptionPane.showMessageDialog(view, "Exportation réussie !");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Erreur lors de l'exportation : " + ex.getMessage());
            }
        }
    }
}
