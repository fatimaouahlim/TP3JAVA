package DAO;
import java.util.List;

public interface GeniricDAOI<T> {
	
	void add(T entite ) ;
	void delete(int id);
	void update(T entite);
	List<T> getAll();
	
}
