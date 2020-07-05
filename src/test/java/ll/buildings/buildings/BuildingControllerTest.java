package ll.buildings.buildings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ll.buildings.buildings.controller.BuildingController;
import ll.buildings.buildings.dao.BuildingDAO;
import ll.buildings.buildings.dao.TaxDAO;
import ll.buildings.buildings.data.Building;
import ll.buildings.buildings.data.Tax;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class BuildingControllerTest {

    @Mock
    private BuildingDAO buildingDAO;

    @Mock
    private TaxDAO taxDAO;

    @InjectMocks
    private BuildingController controller = new BuildingController();

    @Test
    public void testRetrieveBuildings() {
        List<Building> buildingList = new ArrayList<>();
        buildingList.add(new Building());
        buildingList.add(new Building());
        buildingList.add(new Building());

        when(buildingDAO.orderedList()).thenReturn(buildingList);
        List<Building> result = controller.getListB();

        assertEquals(3, result.size());
        verify(buildingDAO, times(1)).orderedList();
    }

    @Test
    public void testFindById() {
        Building building = Building.createNewBuilding(2, "address2", "owner2", 80.0, new BigDecimal(10020), "appartment");
        when(buildingDAO.findById(2)).thenReturn(Optional.ofNullable(building));
        when(buildingDAO.findById(not(eq(2)))).thenReturn(Optional.ofNullable(null));

        Optional<Building> result = controller.getOneB(2);
        assertEquals(2, result.get().getId());

        result = controller.getOneB(1);
        assertEquals(true, result.isEmpty());

        verify(buildingDAO, times(1)).findById(2);
        verify(buildingDAO, times(1)).findById(1);
    }

    @Test
    public void testFailDeleteById() {
        when(buildingDAO.findById(any())).thenReturn(Optional.ofNullable(null));
        ResponseEntity entry = controller.deleteB(1);
        assertEquals(HttpStatus.BAD_REQUEST, entry.getStatusCode());
    }
    
    @Test
    public void testDeleteById() {
        Building building = Building.createNewBuilding(null, "address", "owner", 0, BigDecimal.ONE, "");
        
        when(buildingDAO.findById(any())).thenReturn(Optional.ofNullable(building));
        
        ResponseEntity entry = controller.deleteB(1);
        assertEquals(HttpStatus.OK, entry.getStatusCode());
        verify(buildingDAO, times(1)).deleteById(any());
    }
    
    @Test
    public void testYearlyTax() {
        List<Building> buildingList = new ArrayList<>();
        buildingList.add(Building.createNewBuilding(1, "address1", "owner", 80.0, new BigDecimal(1000), "appartment"));
        buildingList.add(Building.createNewBuilding(2, "address2", "owner", 80.0, new BigDecimal(1001), "appartment"));
        buildingList.add(Building.createNewBuilding(3, "address3", "owner", 80.0, new BigDecimal(2000), "house"));
        buildingList.add(Building.createNewBuilding(4, "address3", "owner2", 80.0, new BigDecimal(2000), "appartment"));
        buildingList.add(Building.createNewBuilding(5, "address3", "owner3", 80.0, new BigDecimal(1000), "industrial"));
        buildingList.add(Building.createNewBuilding(6, "address3", "owner4", 80.0, new BigDecimal(2222.22), "build"));

        Tax tax = Tax.createNewTax("appartment", new BigDecimal(0.1));
        Tax tax2 = Tax.createNewTax("industrial", new BigDecimal(0));
        Tax tax4 = Tax.createNewTax("build", new BigDecimal(0.0001));

        when(buildingDAO.findAll()).thenReturn(buildingList);
        when(taxDAO.findById("appartment")).thenReturn(Optional.ofNullable(tax));
        when(taxDAO.findById("industrial")).thenReturn(Optional.ofNullable(tax2));
        when(taxDAO.findById("house")).thenReturn(Optional.ofNullable(null));
        when(taxDAO.findById("build")).thenReturn(Optional.ofNullable(tax4));

        BigDecimal taxAmount = controller.yearlyTax("owner");
        assertEquals(600.1, taxAmount.doubleValue());
        BigDecimal taxAmount2 = controller.yearlyTax("owner2");
        assertEquals(200, taxAmount2.doubleValue());
        BigDecimal taxAmount3 = controller.yearlyTax("owner3");
        assertEquals(0, taxAmount3.doubleValue());
        BigDecimal taxAmount4 = controller.yearlyTax("owner4");
        assertEquals(0.2222, taxAmount4.doubleValue());
    }

}
