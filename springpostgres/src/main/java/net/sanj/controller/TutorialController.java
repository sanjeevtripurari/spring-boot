package net.sanj.controller;

import net.sanj.model.Tutorial;
import net.sanj.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins="http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;


    @GetMapping("/greet")
    public String index(){
        return  "Greetings!! from Spring Boot";
    }


    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title){
        try {
            List<Tutorial> tutorials = new ArrayList<Tutorial>();

            if (title==null)
                tutorialRepository.findAll().forEach(tutorials::add);
            else
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

            if(tutorials.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);

        } catch ( Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id){
        Tutorial tutorial=tutorialRepository.findById(id);

        if (tutorial!=null){
            return new ResponseEntity<>(tutorial, HttpStatus.OK);
        }else{
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tutorials")
    public ResponseEntity<String> createTutorial(@RequestBody Tutorial tutorial){
        try {
            tutorialRepository.save(new Tutorial(tutorial.getTitle(),tutorial.getDescription(),false));
            return new ResponseEntity<>("Tutorial was created successfully", HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/tutorials/{id}")
    public ResponseEntity<String> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial){
        Tutorial _tutorial = tutorialRepository.findById(id);

        if (_tutorial != null){
            _tutorial.setId(id);
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            tutorialRepository.update(_tutorial);
            return  new ResponseEntity<>("Tutorial was updated successfuly", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Cannot find Tutorial with id="+id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished(){
        try {
            List<Tutorial> tutorials=tutorialRepository.finddByPulished(true);
            if (tutorials.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
