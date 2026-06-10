package com.example.demo.Service;

import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.MarketRepository;
import com.example.demo.Security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class DepartmentSecurity {
    private final MarketRepository marketRepository;
    private final DepartmentRepository departmentRepository;

    public DepartmentSecurity(MarketRepository marketRepository, DepartmentRepository departmentRepository) {
        this.marketRepository = marketRepository;
        this.departmentRepository = departmentRepository;
    }
    public boolean isOwner(Long marketId, Authentication authentication){

        if(! (authentication.getPrincipal() instanceof MyUserDetails user)){
            return false;
        }
        return marketRepository.existsByIdAndUserId(marketId,user.getId());
    }
    public boolean isOWNER(Long departmentId,Authentication authentication){
        if(! (authentication.getPrincipal() instanceof MyUserDetails user)) {
            return false;
        }
        return departmentRepository.existsByIdAndMarketUserId(departmentId,user.getId());
    }
}
