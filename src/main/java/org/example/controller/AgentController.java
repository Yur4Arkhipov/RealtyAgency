package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.Agent;
import org.example.service.AgentService;
import org.example.service.DealService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;
    private final DealService dealService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("agents", agentService.findAll());
        return "agents/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("agent", new Agent());
        return "agents/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Agent agent,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "agents/form";
        }
        agentService.save(agent);
        redirectAttributes.addFlashAttribute("successMessage", "Агент успешно добавлен!");
        return "redirect:/agents";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        return agentService.findById(id)
                .map(agent -> {
                    model.addAttribute("agent", agent);
                    model.addAttribute("agentDeals", dealService.findByAgent(id));
                    return "agents/view";
                })
                .orElse("redirect:/agents");
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return agentService.findById(id)
                .map(agent -> {
                    model.addAttribute("agent", agent);
                    return "agents/form";
                })
                .orElse("redirect:/agents");
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                        @Valid @ModelAttribute Agent agent,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "agents/form";
        }
        agent.setId(id);
        agentService.save(agent);
        redirectAttributes.addFlashAttribute("successMessage", "Данные агента успешно обновлены!");
        return "redirect:/agents";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        agentService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Агент успешно удалён!");
        return "redirect:/agents";
    }
}

