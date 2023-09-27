package com.example.logtofile;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

@RestController
@RequestMapping(value = "/cities")
public class CityRestController {
    private final CityRepository cityRepository;

    @Autowired
    public CityRestController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @PostMapping
    public ResponseEntity<City> create(@RequestBody City body) {
        City city = new City(body.getName(), body.getState());
        city = cityRepository.save(city);
        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodName(CityRestController.class, "getCity", city.getId()).build();
        return ResponseEntity.created(uriComponents.toUri()).body(city);
    }

    @RequestMapping(value="/{city}", method= RequestMethod.GET)
    public City getCity(@PathVariable Long city) {
        return cityRepository.findById(city).orElseThrow(() -> new EntityNotFoundException("city for ID: " + city));
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
