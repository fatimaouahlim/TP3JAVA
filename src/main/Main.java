package main;

import View.*;
import DAO.*;
import Controller.*;
import Model.*;

public class Main {

    public static void main(String[] args) {
   
        // Initialize DAO implementations
        HolidayDAOImplement holidayDAO = new HolidayDAOImplement();
        EmployeDAOImplement employeeDAO = new EmployeDAOImplement();
        DataImportEmploye dataDao = new DataImportEmploye();
        DataimportHoliday dataHDao= new DataimportHoliday();
        // Initialize Models
        HolidayModel holidayModel = new HolidayModel(holidayDAO, employeeDAO,dataHDao);
        EmployeModel employeeModel = new EmployeModel(employeeDAO,dataDao);

        // Initialize Views
        HolidayView holidayView = new HolidayView();

        // Set up Controllers
        new HolidayController(holidayModel, holidayView);
        new EmployeController(employeeModel, holidayView.getEmployeView());

        // Show the HolidayView window
        holidayView.setVisible(true);
    }
}
