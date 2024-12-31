package DAO;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Enum.HolidayType;
import Model.Holiday;


public class HolidayDAOImplement implements GeniricDAOI<Holiday> {

    public DBConnection connection = new DBConnection();
	
    @Override
	public void add(Holiday holiday) {
    	String sql = "INSERT INTO Holiday (employe_id, holiday_type, start_date, end_date) VALUES (?, ?, STR_TO_DATE(?, '%Y-%m-%d'), STR_TO_DATE(?, '%Y-%m-%d'))";

		try(  PreparedStatement  stmt =connection.getConnexion().prepareStatement(sql);){
			stmt.setInt(1, holiday.getIdEmploye());
			stmt.setString(2,holiday.getType().name());
			  stmt.setString(3, holiday.getDateDebut());
	            stmt.setString(4, holiday.getDateFin());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
    
    @Override
    public void update(Holiday holiday) {
        String sql = "UPDATE Holiday set  employe_id = ?, holiday_type = ?, start_date = ?, end_date = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, holiday.getIdEmploye());
            stmt.setString(2, holiday.getType().toString());
            stmt.setString(3, holiday.getDateDebut());
            stmt.setString(4, holiday.getDateFin());
            stmt.setInt(5, holiday.getId());
           
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Le conges est modifier avec succes !.");
            } else {
                System.out.println("Aucun conge est trouvé avec ce ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error lors la modification du conge " + e.getMessage());
        }
    }
    
    
    @Override
    public void delete(int id) {
        String sql = "Delete from Holiday where id=?";
        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql)) {
           
            stmt.setInt(1, id);
          
            int rowsDeleted = stmt.executeUpdate();
           
            if (rowsDeleted > 0) {
                System.out.println("le conge supprimé avec succès !");
            } else {
                System.out.println("Aucun conge trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
    public List<Holiday> getAll(){
    	  List<Holiday> holidays = new ArrayList<>();
    	String sql="SELECT * from Holiday ";
    	
    	
    	 try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    Holiday holiday = new Holiday(
                            rs.getInt("id"),
                            rs.getInt("employe_id"),
                            HolidayType.valueOf(rs.getString("Holiday_type")),
                            rs.getString("start_Date"),
                            rs.getString("end_Date")
                       
                       );
                    holidays.add(holiday);
                }
            } catch (SQLException e) {
             
                e.printStackTrace();
            }
            
            return holidays;
        }
    public String getEmployeeNameById(int idEmploye) {
        String sql = "SELECT nom FROM Employe WHERE id = ?";
        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, idEmploye);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nom"); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null; 
        
    }

    public boolean isHolidayDateExist(int employeId, String dateDebut, String dateFin) {
        String sql = "SELECT COUNT(*) From Holiday WHERE employe_id = ? AND (start_date <= ? AND end_date >= ?)";
        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, employeId);
            stmt.setString(2, dateFin);
            stmt.setString(3, dateDebut);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




}
