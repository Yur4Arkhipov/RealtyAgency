package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.Deal;
import org.example.service.AgentService;
import org.example.service.ClientService;
import org.example.service.DealService;
import org.example.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;
    private final PropertyService propertyService;
    private final ClientService clientService;
    private final AgentService agentService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("deals", dealService.findAll());
        return "deals/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("deal", new Deal());
        model.addAttribute("properties", propertyService.findAvailable());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("agents", agentService.findActiveAgents());
        model.addAttribute("dealStatuses", Deal.DealStatus.values());
        return "deals/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Deal deal,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("properties", propertyService.findAvailable());
            model.addAttribute("clients", clientService.findAll());
            model.addAttribute("agents", agentService.findActiveAgents());
            model.addAttribute("dealStatuses", Deal.DealStatus.values());
            return "deals/form";
        }
        dealService.save(deal);
        redirectAttributes.addFlashAttribute("successMessage", "Сделка успешно создана!");
        return "redirect:/deals";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        return dealService.findById(id)
                .map(deal -> {
                    model.addAttribute("deal", deal);
                    return "deals/view";
                })
                .orElse("redirect:/deals");
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return dealService.findById(id)
                .map(deal -> {
                    model.addAttribute("deal", deal);
                    model.addAttribute("properties", propertyService.findAll());
                    model.addAttribute("clients", clientService.findAll());
                    model.addAttribute("agents", agentService.findAll());
                    model.addAttribute("dealStatuses", Deal.DealStatus.values());
                    return "deals/form";
                })
                .orElse("redirect:/deals");
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                        @Valid @ModelAttribute Deal deal,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("properties", propertyService.findAll());
            model.addAttribute("clients", clientService.findAll());
            model.addAttribute("agents", agentService.findAll());
            model.addAttribute("dealStatuses", Deal.DealStatus.values());
            return "deals/form";
        }
        deal.setId(id);
        dealService.save(deal);
        redirectAttributes.addFlashAttribute("successMessage", "Сделка успешно обновлена!");
        return "redirect:/deals";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        dealService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Сделка успешно удалена!");
        return "redirect:/deals";
    }
}

