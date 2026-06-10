package com.example.demo.Controller;

import com.example.demo.Dto.DepartmentDto;
import com.example.demo.Dto.DepartmentDtoIMPL;
import com.example.demo.Service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/save")
    public ResponseEntity<DepartmentDtoIMPL> addDepartment(@RequestBody DepartmentDto dto) {
        DepartmentDtoIMPL departmentDtoIMPL = departmentService.addDepartment(dto);
        return ResponseEntity.ok(departmentDtoIMPL);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<DepartmentDtoIMPL> findById(@PathVariable Long id) {
        DepartmentDtoIMPL byId = departmentService.findById(id);
        return ResponseEntity.ok(byId);
    }
    @DeleteMapping("/delete/{departmentId}")
    public ResponseEntity<?>deleteById(@PathVariable Long departmentId){
        departmentService.deleteById(departmentId);
        return ResponseEntity.ok("Deleted successfully");
    }
    @PutMapping("/update/{departmentId}")
    public ResponseEntity<DepartmentDtoIMPL>updateById(@PathVariable Long departmentId,@RequestBody DepartmentDto dto){
        DepartmentDtoIMPL departmentDtoIMPL = departmentService.updateById(departmentId,dto);
        return ResponseEntity.ok(departmentDtoIMPL);

    }
}
