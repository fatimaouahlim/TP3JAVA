package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import View.HolidayView;

public class DBConnection {
    private HolidayView view;
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_employe"; 
    private static final String UTILISATEUR = "root"; 
    private static final String MOT_DE_PASSE = ""; 
   
    public static Connection connection = null;
    public Connection getConnexion() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
            System.out.println("La connexion est établie avec succès.");
        } catch (SQLException e) {  
        	JOptionPane.showMessageDialog(view, "la connection a échoué.");
        	e.printStackTrace();
        	 
        }
        return connection;
    }
}
