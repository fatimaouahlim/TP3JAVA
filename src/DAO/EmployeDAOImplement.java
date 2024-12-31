package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Enum.Poste;
import Enum.Role;
import Model.*;

public class EmployeDAOImplement implements GeniricDAOI<Employe>{
    
    public DBConnection connection = new DBConnection();

   
    @Override
    public void add(Employe employe) {
        String sql = "INSERT INTO Employe (nom, prenom, email, telephone, salaire, role, poste) values(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql)) {
          
            stmt.setString(1, employe.getNom());
            stmt.setString(2, employe.getPrenom());
            stmt.setString(3, employe.getEmail());
            stmt.setString(4, employe.getTelephone());
            stmt.setDouble(5, employe.getSalaire());
            stmt.setString(6, employe.getRole().name());
            stmt.setString(7, employe.getPoste().name());
                      stmt.executeUpdate();    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
    @Override
    public void delete(int id) {
        String sql = "Delete from Employe where id=?";
        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql)) {
            
            stmt.setInt(1, id);
           
            int rowsDeleted = stmt.executeUpdate();
           
            if (rowsDeleted > 0) {
                System.out.println("Employé supprimé avec succès !");
            } else {
                System.out.println("Aucun employé trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Employe employee) {
        String sql = "UPDATE employe SET nom = ?, prenom = ?, telephone = ?, email = ?, salaire = ?, role = ?, poste = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, employee.getNom());
            stmt.setString(2, employee.getPrenom());
            stmt.setString(3, employee.getTelephone());
            stmt.setString(4, employee.getEmail());
            stmt.setDouble(5, employee.getSalaire());
            stmt.setString(6, employee.getRole().toString());
            stmt.setString(7, employee.getPoste().toString());
            stmt.setInt(8, employee.getId());
       
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employ est modifier avec succes !.");
            } else {
                System.out.println("Aucun employé trouvé avec ce ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }
    }

  @Override
  public List<Employe> getAll() {
        List<Employe> employes = new ArrayList<>();
        String sql = "SELECT * FROM employe";
        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql);ResultSet rs =stmt.executeQuery()) {
        while (rs.next()) {
            Employe employe = new Employe(
            		rs.getInt("id"),
            		rs.getString("nom"),
            		rs.getString("prenom"),
            		rs.getString("email"),
            		rs.getString("telephone"),
            		rs.getDouble("salaire"),
                Role.valueOf(rs.getString("role")),
                Poste.valueOf(rs.getString("poste")),
                rs.getDouble("balance")
            );
            employes.add(employe);
        }
        }catch(SQLException e){ e.printStackTrace();}
        return employes;
    }

    
    public boolean checkEmployeSolde(int id, int days) {
        int daysCheck = 0;
        String sql = "SELECT balance FROM Employe WHERE id = ?";
        
        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
          
            if (rs.next()) {
                daysCheck = rs.getInt("balance");
            } else {
              
                System.out.println("No employee found with the given ID");
                return false; 
            }

            return daysCheck >= days;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
   
    public void updateEmployeSolde(int id,int days) {
    	
    	String sql="UPDATE Employe SET balance=balance-? where id=?";
    	try(PreparedStatement stmt =connection.getConnexion().prepareStatement(sql);){
    		stmt.setInt(1, days);
    		stmt.setInt(2, id);
    	int rowsAffected=stmt.executeUpdate();
    	if (rowsAffected > 0) {
            System.out.println("le solde d'employer est modifie avec succee!");
            } else {
             System.err.println("le solde n'est pas modifier.");
         }
    	} catch (SQLException e) {
			e.printStackTrace();
		}	
    }

    public void restoreEmployeeSolde(int id, int days) {
        String sql = "UPDATE Employe SET balance = balance + ? WHERE id = ?";
        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, days);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllEmployeNames() {
    	
        List<String> employeeNames = new ArrayList<>();
        String sql = "SELECT nom FROM Employe";
        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql);
        		ResultSet rs = stmt.executeQuery()) {
        		while (rs.next()) {
                employeeNames.add(rs.getString("nom"));
        		}
        } catch (SQLException e) {
        		e.printStackTrace();
        }
        return employeeNames;
    }
    public int  getBalance(int employeID) {
    	 int employeeBalance =0;
    	 String sql = "SELECT balance FROM Employe WHERE nom = ?";
    	  try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql)) {
              
              stmt.setInt(1, employeID);

              try (ResultSet rs = stmt.executeQuery()) {
                  if (rs.next()) {
                      employeeBalance = rs.getInt("balance");
                  }
              }

          } catch (SQLException e) {
              e.printStackTrace();
               
          }

          return employeeBalance;
      }
    	
    	

    
    public int getEmployeeIdByName(String employeeName) throws SQLException {
        int employeeId = 0;
        String sql = "SELECT id FROM Employe WHERE nom = ?";

        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql)) {
           
            stmt.setString(1, employeeName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    employeeId = rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  
        }

        return employeeId;
    }




}
