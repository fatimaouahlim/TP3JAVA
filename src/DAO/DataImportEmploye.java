package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import Model.Employe;

public  class DataImportEmploye  implements DataImportExport <Employe>{
	public DBConnection connection = new DBConnection();
	@Override
	public void importData(String filePath) {
		
		 String sql = "INSERT INTO Employe (nom, prenom, email, telephone, salaire, role, poste) values(?,?,?,?,?,?,?)";
		 try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql);
			     BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

	        String line = reader.readLine();
	        while((line= reader.readLine())!=null) {
	        	String [] data =line.split(",");
	        	if(data.length== 7) {

	                stmt.setString(1, data[0].trim());
	                stmt.setString(2,data[1].trim());
	                stmt.setString(3,data[2].trim());
	                stmt.setString(4,data[3].trim());
	                stmt.setString(5, data[4].trim());
	                stmt.setString(6, data[5].trim());
	                stmt.setString(7, data[6].trim());
	                          stmt.addBatch();    
	        		
	        	}
	        }
	          stmt.executeBatch();
	          System.out.println("Employees imports successfly ");
	          
	
	}catch(IOException | SQLException e) {
		e.printStackTrace();
	}            
	        
	}
	public void exportData(String fileName, List<Employe> data) throws IOException {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
	        writer.write("nom, prenom, email, telephone, salaire, role, poste");
	        writer.newLine();
	        for (Employe employee : data) {
	            String line = String.format("%s,%s,%s,%s,%.2f,%s,%s",
	                    employee.getNom(),
	                    employee.getPrenom(),
	                    employee.getEmail(),
	                    employee.getTelephone(),
	                    employee.getSalaire(),  
	                    employee.getRole(),
	                    employee.getPoste());
	            writer.write(line);
	            writer.newLine();
	        }
	    }
	}


}


