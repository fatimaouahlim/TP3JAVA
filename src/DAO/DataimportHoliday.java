package DAO;

import Model.Holiday;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataimportHoliday implements DataImportExport<Holiday> {

    private DBConnection connection = new DBConnection();

    @Override
    public void importData(String filePath) {
        String sql = "INSERT INTO Holiday (employe_id, holiday_type, start_date, end_date) VALUES (?, ?, STR_TO_DATE(?, '%Y-%m-%d'), STR_TO_DATE(?, '%Y-%m-%d'))";
        
        try (PreparedStatement stmt = connection.getConnexion().prepareStatement(sql);
             BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                   
                    stmt.setInt(1, Integer.parseInt(data[0].trim()));  
                    stmt.setString(2, data[1].trim());                
                    stmt.setString(3, data[2].trim());                
                    stmt.setString(4, data[3].trim());               
                    stmt.addBatch(); 
                }
            }
            stmt.executeBatch(); 
            System.out.println("Holiday data imported successfully!");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void exportData(String fileName, List<Holiday> data) throws IOException {
		
		    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
		        writer.write("employe_id, Holiday_type, start_Date, end_Date");
		        writer.newLine();
		        for (Holiday holidays : data) {
		            String line = String.format("%d,%s,%s,%s",
		            holidays.getIdEmploye(),
		            holidays.getType(),
		            holidays.getDateDebut(),
		      		holidays.getDateFin());
		            writer.write(line);
		            writer.newLine();
		        }
		    }

	
}
}