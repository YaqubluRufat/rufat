package com.example.demo.Controller;

import com.example.demo.DTO.DepartmentDto;
import com.example.demo.DTO.DepartmentDtoIMPL;
import com.example.demo.Service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DepartmentController {
    @Autowired
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/save")
    public ResponseEntity<DepartmentDtoIMPL> addDepartment(@RequestBody @Valid DepartmentDto departmentDto) {
        DepartmentDtoIMPL departmentDtoIMPL = departmentService.addDepartment(departmentDto);
        return ResponseEntity.ok().body(departmentDtoIMPL);
    }

    @GetMapping("/find")
    public ResponseEntity<DepartmentDtoIMPL> findByName(@RequestParam String name) {
        DepartmentDtoIMPL byDepartmentName = departmentService.findByDepartmentName(name);
        return ResponseEntity.ok().body(byDepartmentName);

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<DepartmentDtoIMPL> findById(@PathVariable Long id) {
        DepartmentDtoIMPL byId = departmentService.findById(id);
        return ResponseEntity.ok().body(byId);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DepartmentDtoIMPL> updateDepartment(@RequestBody @Valid DepartmentDto departmentDto,
                                                              @PathVariable Long id) {
        DepartmentDtoIMPL departmentDtoIMPL = departmentService.updateDeparmetn(id, departmentDto);
        return ResponseEntity.ok().body(departmentDtoIMPL);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletebyId(@PathVariable Long id) {
        departmentService.deleteById(id);
        return ResponseEntity.badRequest().body("Deleted successfully");
    }
    @GetMapping("/all/{id}")
    public ResponseEntity<Long>alldepartments(@PathVariable Long id){
        Long allDepartments = departmentService.countAllDepartments(id);
        return ResponseEntity.ok().body(allDepartments);
    }
@GetMapping("/page")
    public ResponseEntity<Page<DepartmentDtoIMPL>>findAllByDepartmentId(@RequestParam Long id,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10")int size,
                                                                        @RequestParam(defaultValue = "id")String sortBy,
                                                                        @RequestParam(defaultValue = "asc") String sortDir){
        Page<DepartmentDtoIMPL> allByDepartmentId = departmentService.findAllBytId(id,page, size, sortBy, sortDir);
        return ResponseEntity.ok().body(allByDepartmentId);
    }


}
