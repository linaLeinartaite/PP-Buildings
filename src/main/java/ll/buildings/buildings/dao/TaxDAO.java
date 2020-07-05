package ll.buildings.buildings.dao;

import ll.buildings.buildings.data.Tax;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaxDAO extends JpaRepository<Tax, String>{
    
}
