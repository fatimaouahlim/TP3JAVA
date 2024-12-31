package Controller;

import View.*;
import Model.*;

import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import Enum.Poste;
import Enum.Role;

public class EmployeController {
    private EmployeModel model;
    private EmployeView view;

    // Constructeur qui initialise la vue et le modèle, et ajoute des listeners pour les actions des boutons
    public EmployeController(EmployeModel model, EmployeView view) {
        this.view = view;
        this.model = model;
        displayEmployes();
        
        // Ajout des ActionListener pour les boutons de la vue
        this.view.addButton.addActionListener(e -> addEmploye());
        this.view.deleteButton.addActionListener(e -> deleteEmploye());
        this.view.displayButton.addActionListener(e -> displayEmployes());
        this.view.appliquerButton.addActionListener(e -> updateEmploye());
        this.view.updateButton.addActionListener(e -> prepareForUpdate());
        this.view.chargerButton.addActionListener(e -> handleExport());
        this.view.importerButton.addActionListener(e -> handleImport());
    }
    
    // Méthode pour ajouter un employé
    private void addEmploye() {
        String name = view.nomField.getText();
        String prenom = view.prenomField.getText();
        float salaire = Float.parseFloat(view.salaireField.getText());
        String telephone = view.telephoneField.getText();
        Role role = (Role) view.roleDropdown.getSelectedItem();
        Poste poste = (Poste) view.posteDropdown.getSelectedItem();
        String email;

        try {
            email = view.emailField.getText();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Email invalide !");
            return;
        }

        boolean ajoutReussi = model.add(name, prenom, email, telephone, salaire, role, poste);
        if (ajoutReussi) {
            JOptionPane.showMessageDialog(view, "L'employé est ajouté avec succès !");
            displayEmployes();
        } else {
            JOptionPane.showMessageDialog(view, "L'ajout a échoué !");
        }
    }
    
    // Méthode pour supprimer un employé
    private void deleteEmploye() {
        int selectedRow = view.employeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un employé à supprimer !");
            return;
        }

        int id = (int) view.tableModel.getValueAt(selectedRow, 0);
        if (model.delete(id)) {
            displayEmployes();
        } else {
            JOptionPane.showMessageDialog(view, "L'employé a des congés !");
        }
    }
    
    // Méthode appelée lorsqu'une ligne est sélectionnée pour modification
    private void prepareForUpdate() {
        int selectedRow = view.employeTable.getSelectedRow();

        if (selectedRow != -1) {
            view.nomField.setText((String) view.tableModel.getValueAt(selectedRow, 1));
            view.prenomField.setText((String) view.tableModel.getValueAt(selectedRow, 2));
            view.telephoneField.setText((String) view.tableModel.getValueAt(selectedRow, 3));
            view.emailField.setText((String) view.tableModel.getValueAt(selectedRow, 4));
            view.salaireField.setText(String.valueOf(view.tableModel.getValueAt(selectedRow, 5)));
            view.roleDropdown.setSelectedItem(view.tableModel.getValueAt(selectedRow, 6));
            view.posteDropdown.setSelectedItem(view.tableModel.getValueAt(selectedRow, 7));
        } else {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner une ligne à modifier.");
        }
    }

    // Méthode appelée pour appliquer les modifications après la validation
    private void updateEmploye() {
        int selectedRow = view.employeTable.getSelectedRow();

        if (selectedRow != -1) {
            try {
                String nom = view.nomField.getText();
                String prenom = view.prenomField.getText();
                String telephone = view.telephoneField.getText();
                String email = view.emailField.getText();
                double salaire = Double.parseDouble(view.salaireField.getText());
                Role role = (Role) view.roleDropdown.getSelectedItem();
                Poste poste = (Poste) view.posteDropdown.getSelectedItem();

                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id = (int) view.tableModel.getValueAt(selectedRow, 0);

                boolean success = model.update(id, nom, prenom, email, telephone, salaire, role, poste);

                if (success) {
                    view.tableModel.setValueAt(nom, selectedRow, 1);
                    view.tableModel.setValueAt(prenom, selectedRow, 2);
                    view.tableModel.setValueAt(telephone, selectedRow, 3);
                    view.tableModel.setValueAt(email, selectedRow, 4);
                    view.tableModel.setValueAt(salaire, selectedRow, 5);
                    view.tableModel.setValueAt(role, selectedRow, 6);
                    view.tableModel.setValueAt(poste, selectedRow, 7);
                    JOptionPane.showMessageDialog(view, "La mise à jour a réussi.");
                } else {
                    JOptionPane.showMessageDialog(view, "La mise à jour a échoué.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Entrée invalide pour le salaire.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Une erreur est survenue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view, "Aucune ligne sélectionnée.");
        }
    }

    private void displayEmployes() {
        List<Employe> employes = model.getAllEmployes();
        DefaultTableModel tableModel = (DefaultTableModel) view.employeTable.getModel();
        tableModel.setRowCount(0);

        for (Employe employe : employes) {
            tableModel.addRow(new Object[]{
                employe.getId(),
                employe.getNom(),
                employe.getPrenom(),
                employe.getTelephone(),
                employe.getEmail(),
                employe.getSalaire(),
                employe.getRole(),
                employe.getPoste(),
                employe.getBalance()
            });
        }
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

                List<Employe> employes = model.getAllEmployes();
                model.exportData(filePath, employes);
                JOptionPane.showMessageDialog(view, "Exportation réussie !");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Erreur lors de l'exportation : " + ex.getMessage());
            }
        }
    }
}
