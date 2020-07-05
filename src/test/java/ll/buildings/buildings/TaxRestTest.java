package ll.buildings.buildings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import ll.buildings.buildings.data.Tax;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxRestTest {
    

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getListOfTaxesTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/rest/tax")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOneTaxTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/rest/tax/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTaxTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/rest/tax/{type}/", "apartment")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addTaxTest() throws JsonProcessingException, Exception {
        String json = mapper.writeValueAsString(Tax.createNewTax("type", new BigDecimal(0.001)));
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/rest/tax")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
       
    @Test
    public void addTaxTestNeg() throws JsonProcessingException, Exception {
        String json = mapper.writeValueAsString(Tax.createNewTax(null, new BigDecimal(0.001)));
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/rest/tax")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
    
           
    @Test
    public void updateTaxTest() throws JsonProcessingException, Exception {
        String json = mapper.writeValueAsString(Tax.createNewTax("house", new BigDecimal(0.001)));
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/rest/tax/{type}", "house")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
         
    @Test
    public void updateTaxTestNeg() throws JsonProcessingException, Exception {
        String json = mapper.writeValueAsString(Tax.createNewTax("house", null));
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/rest/tax/{type}", "house")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }  
}
