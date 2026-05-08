package com.example.demo.Controller;

import com.example.demo.Dto.MenuItemDto;
import com.example.demo.Dto.MenuItemDtoIMPL;
import com.example.demo.Service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController("/menu")
public class MenuItemController {

    public final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping("/save")
    public ResponseEntity<MenuItemDtoIMPL>addMenuItem(@RequestBody@Valid MenuItemDto menuItemDto, Authentication authentication){
        MenuItemDtoIMPL menuItemDtoIMPL = menuItemService.addMenuItem(menuItemDto, authentication);
        return ResponseEntity.ok(menuItemDtoIMPL);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<MenuItemDtoIMPL>findById(@PathVariable Long id){
        MenuItemDtoIMPL byId = menuItemService.findById(id);
        return ResponseEntity.ok(byId);
    }
    @DeleteMapping("/dekete/{id}")
    public ResponseEntity<?>deleteById(@PathVariable Long id,Authentication authentication){
        menuItemService.deleteMenuItem(id,authentication);
        return ResponseEntity.ok("Deleted successfully");
    }
    @PutMapping("/uodate/{id}")
    public ResponseEntity<MenuItemDtoIMPL>updateById(@PathVariable Long id,Authentication authentication,@RequestBody@Valid MenuItemDto menuItemDto){
        MenuItemDtoIMPL menuItemDtoIMPL = menuItemService.updateMenuItem(id, menuItemDto, authentication);
        return ResponseEntity.ok(menuItemDtoIMPL);
    }
    @GetMapping("/list")
    public ResponseEntity<Page<MenuItemDtoIMPL>>getAll(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(defaultValue = "id") String sortBy,
                                                       @RequestParam(defaultValue = "asc") String sortDir){
        Page<MenuItemDtoIMPL> all = menuItemService.findAll(page, size, sortBy, sortDir);
        return ResponseEntity.ok(all);
    }

}
