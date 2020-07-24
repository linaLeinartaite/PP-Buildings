package ll.buildings.buildings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import ll.buildings.buildings.data.Building;
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
public class BuildingRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getListOfBuildingsTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/rest/buildings")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOneBuildingTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/rest/buildings/{id}", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBuildingTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/rest/buildings/{id}/", 2)
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addBuildingTest() throws JsonProcessingException, Exception {
        String json = mapper.writeValueAsString(Building.createNewBuilding(null, "address1", "owner1", 80.0, new BigDecimal(10020), "appartment"));
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/rest/buildings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk());
    }


    @Test
    public void addBuildingTestNeg() throws JsonProcessingException, Exception {
        String json = mapper.writeValueAsString(Building.createNewBuilding(null, null, "owner2", 80.0, new BigDecimal(10020), "appartment"));
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/rest/buildings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.TEXT_PLAIN_VALUE))
                  .andExpect(status().is5xxServerError());
    }

    @Test()
    public void addExisting() throws JsonProcessingException, Exception {
        String json = mapper.writeValueAsString(Building.createNewBuilding(null, "Algirdo 41-a, Vilnius",
                "Lina", 800.0, new BigDecimal(10020), "appartment"));
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/rest/buildings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateBuildingTest() throws JsonProcessingException, Exception {
        String json = mapper.writeValueAsString(Building.createNewBuilding(3, "address3", "owner3", 80.0, new BigDecimal(10020), "appartment"));
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/rest/buildings/{id}", "3")
                //says what is media-type of the request:
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                //tels what media-type of the response will be understood:
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk());
    }


    @Test
    public void updateBuildingTestNeg() throws JsonProcessingException, Exception {
        String json = mapper.writeValueAsString(Building.createNewBuilding(3, null, "owner3", 80.0, new BigDecimal(10020), "appartment"));
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/rest/buildings/{id}", "3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void updateIntoExistingTest() throws JsonProcessingException, Exception {
        String json = mapper.writeValueAsString(Building.createNewBuilding(4, "Algirdo 41-a, Vilnius",
                "Lina", 80.0, new BigDecimal(10020), "appartment"));
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/rest/buildings/{id}", "3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isAccepted());
    }

}
