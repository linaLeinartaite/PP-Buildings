package ll.buildings.buildings.dao;

import java.util.List;
import ll.buildings.buildings.data.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BuildingDAO extends JpaRepository<Building, Integer> {
    
    @Query("select b from Building b "
            + " order by b.address, b.owner, b.marketValue")
    public List<Building> orderedList();      
    
     @Query("select b from Building b "
            + " where upper(b.address) = upper(:address) and upper(b.owner) = upper(:owner)")
    public List<Building> getOneByOwnerAddres(@Param("owner") String owner, @Param("address") String address);  
    
}