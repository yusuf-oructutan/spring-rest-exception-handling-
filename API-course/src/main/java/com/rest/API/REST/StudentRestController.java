package com.rest.API.REST;

import com.rest.API.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudents;


    @PostConstruct
    public void loadData(){

         theStudents =new ArrayList<>();

        theStudents.add(new Student("yusuf","oruçtutan"));

        theStudents.add(new Student("güven","oruçtutan"));

        theStudents.add(new Student("gülden","oruçtutan"));

    }




    @GetMapping("/students")
    public List<Student> getStudents(){

        return theStudents;
    }


    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){

        if ((studentId>= theStudents.size()) || (studentId<0)){
            throw new StudentNotFoundException("Student id bulunamadı "+studentId);
        }

        return theStudents.get(studentId);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException (StudentNotFoundException exc){

        StudentErrorResponse error =new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());

        error.setMessage(exc.getMessage());

        error.setTimeStamp(System.currentTimeMillis());


        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc){

        StudentErrorResponse error =new StudentErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());

        error.setMessage(exc.getMessage());

        error.setTimeStamp(System.currentTimeMillis());


        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);



    }
}
