package Model;

import java.io.File;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import DAO.DataimportHoliday;
import DAO.EmployeDAOImplement;
import DAO.HolidayDAOImplement;
import Enum.HolidayType;

public class HolidayModel {
    private EmployeDAOImplement daoEmp;
    private HolidayDAOImplement dao;
    private DataimportHoliday dataDao;
    // Constructeur qui initialise les DAO pour Employé et Congé
    public HolidayModel(HolidayDAOImplement dao, EmployeDAOImplement daoEmp, DataimportHoliday dataDao) {
        this.dao = dao;
        this.daoEmp = daoEmp;
        this.dataDao=dataDao;
    }

    // Ajouter un congé pour un employé
    public boolean add(int employeId, String dateDebut, String dateFin, HolidayType type) {
        int dayDiff = dateDiff(dateDebut, dateFin);  // Calculer la différence en jours entre les dates
        
        // Vérifier si l'employé a suffisamment de solde de congé
        if (daoEmp.checkEmployeSolde(employeId, dayDiff)) {
            // Vérifier si les dates demandées ne chevauchent pas un congé existant
            if (!dao.isHolidayDateExist(employeId, dateDebut, dateFin)) {
                // Créer un objet Holiday et mettre à jour le solde de congé de l'employé
                Holiday holiday = new Holiday(employeId, type, dateDebut, dateFin);
                daoEmp.updateEmployeSolde(employeId, dayDiff);       
                dao.add(holiday);  // Ajouter le congé dans la base de données
                return true;
            } else {
                System.out.println("Les dates des congés sont déjà prises pour cet employé.");
                return false;
            }
        }
        return false;
    }

    // Calculer la différence en jours entre deux dates
    public int dateDiff(String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(startDate);  // Convertir la première date en objet Date
            Date date2 = sdf.parse(endDate);    // Convertir la deuxième date en objet Date

            long diffInMillies = Math.abs(date2.getTime() - date1.getTime());  // Calculer la différence en millisecondes

            return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);  // Convertir la différence en jours
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;  // Retourner 0 en cas d'erreur de parsing
        }
    }

    // Supprimer un congé à partir de son ID
    public boolean delete(int id) {
        try {
            dao.delete(id);  // Appeler la méthode delete du DAO Holiday
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mettre à jour un congé existant
    public boolean update(int id, int idEmploye, HolidayType type, String dateDebut, String dateFin) {
        try {
            Holiday nvHoliday = new Holiday(id, idEmploye, type, dateDebut, dateFin);  // Créer un objet Holiday avec les nouvelles valeurs
            dao.update(nvHoliday);  // Mettre à jour le congé dans la base de données
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer tous les congés
    public List<Holiday> getAllHolidays() {
        return dao.getAll();  // Retourner tous les congés à partir du DAO Holiday
    }

    // Récupérer tous les noms des employés
    public List<String> getEmployeeNames() {
        return daoEmp.getAllEmployeNames();  // Retourner tous les noms des employés à partir du DAO Employé
    }

    // Récupérer la liste de tous les employés
    public List<Employe> getEmployees() {
        return daoEmp.getAll();  // Retourner tous les employés à partir du DAO Employé
    }

    // Récupérer l'ID d'un employé à partir de son nom
    public int getEmployeeIdByName(String employeeName) {
        int employeeId = 0; 
        try {
            employeeId = daoEmp.getEmployeeIdByName(employeeName);  // Appeler la méthode du DAO pour obtenir l'ID de l'employé
            if (employeeId == 0) {
                System.out.println("Employee not found.");  // Si l'employé n'est pas trouvé
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return employeeId;  // Retourner l'ID de l'employé
    }
    
    
    
    
    
private boolean checkFileExits(File file) {
		
		if(!file.exists()) {
			throw new IllegalArgumentException ("le fichier n'existe pas "+file.getPath());
			
		}
		return true;
		
	}

private boolean checkIsFile(File file) {
	
	if(!file.isFile()) {
		throw new IllegalArgumentException ("le chemin specifie nest pas un fichier "+file.getPath());
		
	}
	return true;
	
}
private boolean checkIsReadebal(File file) {
	
	if(!file.canRead()) {
		throw new IllegalArgumentException ("le chemin specifie nest pas lisibles "+file.getPath());
		
	}
	return true;
	
}
public void importData(String filePath)  {
File file = new File(filePath);
    
    
    checkFileExits(file);
    checkIsFile(file);
    checkIsReadebal(file);
    System.out.println("improt model");
    dataDao.importData(filePath);
}

public void exportData(String filePath, List<Holiday> holidays) throws IOException {
File file = new File(filePath);
    
    
    checkFileExits(file);
    checkIsFile(file);
    checkIsReadebal(file);
    System.out.println("exprot model");
    dataDao.exportData(filePath, holidays);
}
}
