package Model;
import java.io.File;
import java.io.IOException;
import java.util.List;

import DAO.*;
import Enum.Poste;
import Enum.Role;

public class EmployeModel {

   
    private EmployeDAOImplement dao;
    private DataImportEmploye dataDao;
  
    public EmployeModel(EmployeDAOImplement dao,DataImportEmploye dataDao) {
        this.dao = dao;
        this.dataDao = dataDao;
    }
   
    public boolean add(String nom, String prenom, String email, String telephone, float salaire, Role role, Poste poste) {
     
        if (!email.contains("@") && !email.contains(".")) {
            System.out.println("L'email est invalide !");
            return false;
        }
        
        Employe nvEmploye = new Employe(nom, prenom, email, telephone, salaire, role, poste);
      
        dao.add(nvEmploye);
        return true;    
    }
    
    
    public boolean delete(int id) {
        try {
            dao.delete(id); 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;    
        }    
    }
   
    public boolean update(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste) {
        try {
           
            Employe nvEmploye = new Employe(id, nom, prenom, email, telephone, salaire, role, poste);
           
            dao.update(nvEmploye);
            return true;            
        } catch (Exception e) {
            e.printStackTrace();
            return false;    
        }    
    }
   
    public List<Employe> getAllEmployes() {
        return dao.getAll();
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

public void exportData(String filePath, List<Employe> employes) throws IOException {
File file = new File(filePath);
    
    
    checkFileExits(file);
    checkIsFile(file);
    checkIsReadebal(file);
    System.out.println("exprot model");
    dataDao.exportData(filePath, employes);
}

}
