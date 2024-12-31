package Model;

import Enum.HolidayType;

public class Holiday {
    private int id;
    private int idEmploye;
    private HolidayType type;
    private String dateDebut;
    private String dateFin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public HolidayType getType() {
        return type;
    }

    public void setType(HolidayType type) {
        this.type = type;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public Holiday(int id, int idEmploye, HolidayType type, String dateDebut, String dateFin) {
        this.id = id;
        this.idEmploye = idEmploye;
        this.type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Holiday(int idEmploye, HolidayType type, String dateDebut, String dateFin) {
        this.idEmploye = idEmploye;
        this.type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

}
