package ll.buildings.buildings.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import ll.buildings.buildings.dao.TaxDAO;
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
@RequestMapping("/rest/tax")
public class TaxController {

    @Autowired
    private TaxDAO taxDAO;

    @ApiOperation(value = "Find all tax rates of various property types", nickname = "getTaxList")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tax> getListT() {

        return taxDAO.findAll();
    }

    @ApiOperation(value = "Find tax rate of certain property type", nickname = "getTaxOfOneBuilding")
    @GetMapping(value = "{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Tax> getOneT(@PathVariable(value = "type") String propertyType) {

        return taxDAO.findById(propertyType);
    }

    @ApiOperation(value = "Delete certain property type with its tax rate")
    @Transactional
    @DeleteMapping(value = "{type}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity deleteT(@PathVariable(value = "type") String propertyType) {

        if (taxDAO.findById(propertyType).equals(Optional.empty())) {
            return new ResponseEntity("There is no such property type!", HttpStatus.BAD_REQUEST);
        }
        taxDAO.deleteById(propertyType);
        return new ResponseEntity("Tax deleted successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "Add new property type with its tax rate")
    @Transactional
    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity addT(@RequestBody Tax tax) {

        if (taxDAO.findById(tax.getPropertyType()).equals(Optional.empty())) {
            taxDAO.save(tax);
        } else {
            return new ResponseEntity("This property type is allready there!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Tax added successfully!", HttpStatus.OK);
    }

    @ApiOperation(value = "Update tax rate of certain property type")
    @Transactional
    @PutMapping(value = "{type}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity updateT(@PathVariable(value = "type") String propertyType, @RequestBody Tax updatedTax) {

        if (taxDAO.findById(propertyType).equals(Optional.empty())) {
            return new ResponseEntity("There is no such property type!", HttpStatus.BAD_REQUEST);
        }

        Tax t = taxDAO.getOne(propertyType);
        t.setTaxRate(updatedTax.getTaxRate());
        return new ResponseEntity("Tax updated successfully!", HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<String> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<String>("ERROR! = " + ex.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
