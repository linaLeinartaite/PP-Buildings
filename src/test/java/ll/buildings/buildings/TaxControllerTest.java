package ll.buildings.buildings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ll.buildings.buildings.controller.TaxController;
import ll.buildings.buildings.dao.TaxDAO;
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
public class TaxControllerTest {   

    @Mock
    private TaxDAO taxDAO;

    @InjectMocks
    private TaxController controller = new TaxController();

    @Test
    public void testRetrieveTax() {
        List<Tax> taxList = new ArrayList<>();
        taxList.add(new Tax());
        taxList.add(new Tax());
        taxList.add(new Tax());

        when(taxDAO.findAll()).thenReturn(taxList);
        List<Tax> result = controller.getListT();

        assertEquals(3, result.size());
        verify(taxDAO, times(1)).findAll();
    }
    
    @Test
    public void testFindTaxById() {
        Tax tax = Tax.createNewTax("type", new BigDecimal(0.2));
        when(taxDAO.findById("type")).thenReturn(Optional.ofNullable(tax));
        when(taxDAO.findById(not(eq("type")))).thenReturn(Optional.ofNullable(null));

        Optional<Tax> result = controller.getOneT("type");
        assertEquals("type", result.get().getPropertyType());

        result = controller.getOneT("type2");
        assertEquals(true, result.isEmpty());

        verify(taxDAO, times(1)).findById("type2");
        verify(taxDAO, times(1)).findById("type2");
    }
    
    @Test
    public void testDeleteTaxById() {
        Tax tax = Tax.createNewTax(null, BigDecimal.ONE);
        
        when(taxDAO.findById(any())).thenReturn(Optional.ofNullable(tax));
        
        ResponseEntity entry = controller.deleteT("apartament");
        assertEquals(HttpStatus.OK, entry.getStatusCode());
        verify(taxDAO, times(1)).deleteById(any());
    }
}
