package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.AgentService;
import org.example.service.ClientService;
import org.example.service.DealService;
import org.example.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PropertyService propertyService;
    private final AgentService agentService;
    private final ClientService clientService;
    private final DealService dealService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("propertiesCount", propertyService.count());
        model.addAttribute("availableCount", propertyService.countAvailable());
        model.addAttribute("agentsCount", agentService.countActive());
        model.addAttribute("clientsCount", clientService.count());
        model.addAttribute("dealsCount", dealService.countCompleted());
        model.addAttribute("totalRevenue", dealService.calculateTotalRevenue());
        model.addAttribute("recentProperties", propertyService.findAvailable().stream().limit(6).toList());
        return "index";
    }
}

