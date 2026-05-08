package com.example.demo.Service;

import com.example.demo.Dto.MenuItemDto;
import com.example.demo.Dto.MenuItemDtoIMPL;
import com.example.demo.Entity.MenuItem;
import com.example.demo.Entity.Restaurant;
import com.example.demo.Entity.User;
import com.example.demo.Exception.InvalidRole;
import com.example.demo.Exception.InvalidSort;
import com.example.demo.Exception.MenuItemNotFound;
import com.example.demo.Exception.RestaurantNotFound;
import com.example.demo.Mapper.MenuItemMapper;
import com.example.demo.Repository.MenuItemRepository;
import com.example.demo.Repository.RestaurantRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {


    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemService(MenuItemRepository menuItemRepository, MenuItemMapper menuItemMapper, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;

        this.menuItemMapper = menuItemMapper;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }
    @PreAuthorize("hasRole('OWNER')")
    public MenuItemDtoIMPL addMenuItem(MenuItemDto menuItemDto, Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));

        MenuItem menuItem = menuItemMapper.toMenuItem(menuItemDto);
        Restaurant restaurant = restaurantRepository.findById(menuItemDto.getRestaurantId()).orElseThrow(() ->
                new RestaurantNotFound("Restaurant not found"));
        if(!user.getId().equals(restaurant.getUser().getId())){
            throw new InvalidRole("You are not the owner of this restaurant");
        }
        menuItem.setRestaurant(restaurant);
        MenuItem save = menuItemRepository.save(menuItem);
        return menuItemMapper.toMenuItemDtoIMPL(save);

    }

    public MenuItemDtoIMPL findById(Long id){
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new MenuItemNotFound("Menu item not found"));
        return menuItemMapper.toMenuItemDtoIMPL(menuItem);
    }
    @PreAuthorize("hasRole('OWNER')")
    public void deleteMenuItem(Long id,Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new MenuItemNotFound("Menu item not found"));
        if(!user.getId().equals(menuItem.getRestaurant().getUser().getId())){
            throw new InvalidRole("Its not four you");
        }
        menuItemRepository.delete(menuItem);
    }
  @PreAuthorize("hasRole('OWNER')")
    public MenuItemDtoIMPL updateMenuItem(Long id,MenuItemDto menuItemDto,Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new MenuItemNotFound("MenuItem not found"));

        if(!user.getId().equals(menuItem.getRestaurant().getUser().getId())){
            throw new InvalidRole("Its not your role");
        }
        if(menuItemDto.getName()!=null){
            menuItem.setName(menuItemDto.getName());
        }
        if(menuItemDto.getPrice()!=null){
            menuItem.setPrice(menuItemDto.getPrice());
        }

        MenuItem save = menuItemRepository.save(menuItem);
        return menuItemMapper.toMenuItemDtoIMPL(save);

    }

    public Page<MenuItemDtoIMPL> findAll(int page ,int size,String sortBy,String sortDir){
        List<String> list = List.of("id", "name", "price");
        if(!list.contains(sortBy)){
            throw new InvalidSort("Invalid sort");
        }
        List<String> list1 = List.of("asc", "desc");
        if(!list1.contains(sortDir)){
            throw new InvalidSort("Invalid sort");
        }

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return menuItemRepository.findAll(pageRequest).map(menuItemMapper::toMenuItemDtoIMPL);

    }
}
