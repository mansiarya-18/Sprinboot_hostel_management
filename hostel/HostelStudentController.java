
package com.example.hostel;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/students")
public class HostelStudentController {

    private final HostelStudentRepository repo;

    public HostelStudentController(HostelStudentRepository repo) {
        this.repo = repo;
    }

    // 1. INSERT STUDENT
    @PostMapping
    public ResponseEntity<HostelStudent> addStudent(
            @RequestBody HostelStudent student) {

        HostelStudent saved = repo.save(student);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // 2. GET ALL STUDENTS
    @GetMapping
    public List<HostelStudent> getAllStudents() {
        return repo.findAll();
    }

    // 3. GET STUDENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<HostelStudent> getStudent(
            @PathVariable int id) {

        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. UPDATE STUDENT
    @PutMapping("/{id}")
    public ResponseEntity<HostelStudent> updateStudent(
            @PathVariable int id,
            @RequestBody HostelStudent student) {

        return repo.findById(id).map(existing -> {

            existing.setHostelName(student.getHostelName());
            existing.setRoomNumber(student.getRoomNumber());
            existing.setFee(student.getFee());

            HostelStudent updated = repo.save(existing);

            return ResponseEntity.ok(updated);

        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE STUDENT
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable int id) {

        if (repo.existsById(id)) {

            repo.deleteById(id);

            return ResponseEntity.ok(
                    "Student deleted successfully!");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Student not found!");
    }
}
