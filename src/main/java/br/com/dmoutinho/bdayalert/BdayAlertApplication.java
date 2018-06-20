package br.com.dmoutinho.bdayalert;

import java.util.Date;
import java.util.Optional;
import java.util.Collection;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SpringBootApplication
@EnableAutoConfiguration
public class BdayAlertApplication {
	public static void main(String[] args) {
		SpringApplication.run(BdayAlertApplication.class, args);
	}
}

@Component
class SampleDataInitializer implements ApplicationRunner {

    private final BirthdayRepository birthdayRepository;

    SampleDataInitializer(BirthdayRepository birthdayRepository) {
        this.birthdayRepository = birthdayRepository;
    }

    private Date getDate(String strDate) {
    	try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        
    	this.birthdayRepository.save(new Birthday(null,"Kurt Cobain",this.getDate("1967-02-20")));

    	this.birthdayRepository.save(new Birthday(null,"Amy Winehouse",this.getDate("1983-09-17")));

    	this.birthdayRepository.save(new Birthday(null,"Elis Regina",this.getDate("1945-03-17")));

    	this.birthdayRepository.save(new Birthday(null,"Cássia Eller",this.getDate("1962-12-10")));
    	
    	this.birthdayRepository.save(new Birthday(null,"Tim Maia",this.getDate("1942-09-28")));
    	
        this.birthdayRepository.findAll().forEach(System.out::println);
        
    }
    
}

@RestController
@RequestMapping("/bday")
class BirthdayRestController {

    private final BirthdayRepository birthdayRepository;

    BirthdayRestController(BirthdayRepository birthdayRepository) {
        this.birthdayRepository = birthdayRepository;
    }

    ResponseEntity<Birthday> getCrossResponseEntity(Birthday birthday, HttpStatus httpStatus) {
    	
      	ResponseEntity<Birthday> responseEntity = null;
    	
      	if( birthday==null ) {
          	responseEntity = new ResponseEntity<Birthday>(httpStatus);
    	} else {
          	responseEntity = new ResponseEntity<Birthday>(birthday,httpStatus);
    	}
      	
      	//responseEntity.getHeaders().add("Access-Control-Allow-Origin","*");
      	//responseEntity.getHeaders().add("Access-Control-Allow-Headers","application/json");
      	//responseEntity.getHeaders().add("Access-Control-Allow-Methods","POST, GET, OPTIONS, DELETE, PUT");

      	return responseEntity;
    
    }
    
    @CrossOrigin
    @GetMapping("/birthday")
    Collection<Birthday> findAll() {
        return this.birthdayRepository.findAll();
    }
    
    @CrossOrigin
    @GetMapping("/birthday/{id}")
    ResponseEntity<Birthday> findBirthday(@PathVariable("id") Long id) {
    	
    	Optional<Birthday> birthday = this.birthdayRepository.findById(id);
    	
    	if ( !birthday.isPresent() ) {
			return getCrossResponseEntity(null,HttpStatus.NOT_FOUND);
		}

    	System.out.println("findBirthday end");
    	
		return getCrossResponseEntity(birthday.get(),HttpStatus.OK);
    	
    }
    
    @CrossOrigin
	@PostMapping("/birthday")
	ResponseEntity<Birthday> createBirthday(@RequestBody Birthday birthday) {

		birthday = this.birthdayRepository.save(birthday);
	
		return getCrossResponseEntity(birthday,HttpStatus.OK);
		
	}
    
    @CrossOrigin
	@DeleteMapping("/birthday/{id}")
	ResponseEntity<Birthday> deleteBirthday(@PathVariable Long id) {

		this.birthdayRepository.deleteById(id);
		
		return getCrossResponseEntity(null,HttpStatus.OK);

	}
    
    @CrossOrigin
	@PutMapping("/birthday/{id}")
	ResponseEntity<Birthday> updateBirthday(@PathVariable Long id, @RequestBody Birthday birthday) {

		birthday = this.birthdayRepository.save(birthday);

    	ResponseEntity<Birthday> responseEntity = null;
		
		if ( null==birthday ) {
			responseEntity =  getCrossResponseEntity(birthday,HttpStatus.NO_CONTENT);
		} else {
			responseEntity =  getCrossResponseEntity(birthday,HttpStatus.OK);
		}
		
		return responseEntity;
		
	}
    
}

interface BirthdayRepository extends JpaRepository<Birthday,Long> {
    //Collection<Birthday> findByName(String name);
}

@Entity
class Birthday {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    
    private Date day;

	public Birthday(Long id, String name, Date day) {
		super();
		this.id = id;
		this.name = name;
		this.day = day;
	}

	public Birthday() {
		super();
	}

	@Override
	public String toString() {
		return "Birthday [id=" + id + ", name=" + name + ", day=" + day + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}
    
}