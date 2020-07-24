package ll.buildings.buildings.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import javassist.bytecode.ParameterAnnotationsAttribute;
import ll.buildings.buildings.dao.BuildingDAO;
import ll.buildings.buildings.dao.TaxDAO;
import ll.buildings.buildings.data.Building;
import ll.buildings.buildings.data.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/buildings")
public class BuildingController {

    private final BigDecimal defaultTaxRate = new BigDecimal(0.2);

    @Autowired
    private BuildingDAO buildingDAO;

    @Autowired
    private TaxDAO taxDAO;

    @ApiOperation(value = "Find all the buildings in Building Registry", nickname = "getListOfBuildings")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Building> getListB() {

        return buildingDAO.orderedList();
    }

    @ApiOperation(value = "Find building in Building Registry by its id number", nickname = "getOneBuildingById")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Building> getOneB(@PathVariable(value = "id") Integer id) {

        return buildingDAO.findById(id);
    }

    @ApiOperation(value = "Delete building from Building Registry by its id number")
    @Transactional
    @DeleteMapping(value = "{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity deleteB(@PathVariable(value = "id") Integer id) {

        if (buildingDAO.findById(id).equals(Optional.empty())) {
            return new ResponseEntity("There is no building with such id!", HttpStatus.BAD_REQUEST);
        }

        buildingDAO.deleteById(id);
        return new ResponseEntity("Building deleted successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "Add new building into Building Registry")
    @Transactional
    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity addB(@RequestBody Building building) {
        List<Building> exist = buildingDAO.getOneByOwnerAddres(building.getOwner(), building.getAddress());

        if (!exist.isEmpty()) {
            return new ResponseEntity("Owner with this address is already in there!", HttpStatus.BAD_REQUEST);
        }
        building.setId(null);
        buildingDAO.save(building);
        return new ResponseEntity("Building added successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "Update building from Building Registry by its id number")
    @Transactional
    @PutMapping(value = "{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity updateB(@PathVariable(value = "id") Integer id, @RequestBody Building updatedBuilding) {

        Building b = buildingDAO.getOne(id);
        List<Building> exist = buildingDAO.getOneByOwnerAddres(updatedBuilding.getOwner(), updatedBuilding.getAddress());

        if (buildingDAO.findById(id).equals(Optional.empty())) {
            return new ResponseEntity("There is no building with such id!", HttpStatus.BAD_REQUEST);
        }
        if (!exist.isEmpty()) {
            b.setSize(updatedBuilding.getSize());
            b.setMarketValue(updatedBuilding.getMarketValue());
            b.setPropertyType(updatedBuilding.getPropertyType());
            return new ResponseEntity("Owner with this address is already in there!,"
                    + "thus address and owner were not updated!", HttpStatus.ACCEPTED);
        } else {
            b.setAddress(updatedBuilding.getAddress());
            b.setOwner(updatedBuilding.getOwner());
            b.setSize(updatedBuilding.getSize());
            b.setMarketValue(updatedBuilding.getMarketValue());
            b.setPropertyType(updatedBuilding.getPropertyType());
        }

        return new ResponseEntity(
                "Building updated successfully!", HttpStatus.OK);

    }

    @ApiOperation(value = "Calculate yearly Tax of certain owner")
    @Transactional
    @GetMapping(value = "tax/{owner}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal yearlyTax(@PathVariable(value = "owner") String owner) {

        List<Building> listB = buildingDAO.findAll();
        BigDecimal yearlyTax = new BigDecimal(0);
        BigDecimal taxRate;
        for (Building b : listB) {
            if (b.getOwner().toLowerCase().equals(owner.toLowerCase())) {

                Optional<Tax> taxOptional = taxDAO.findById(b.getPropertyType());
                if (taxOptional.isEmpty()) {
                    taxRate = defaultTaxRate;
                } else {
                    Tax tax = taxOptional.get();
                    taxRate = tax.getTaxRate();
                }
                yearlyTax = yearlyTax.add(b.getMarketValue().multiply(taxRate));
            }
        }
        yearlyTax = yearlyTax.setScale(4, RoundingMode.HALF_UP);
        return yearlyTax;
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<String> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<String>("ERROR! = " + ex.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
