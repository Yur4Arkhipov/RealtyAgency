package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.Client;
import org.example.service.ClientService;
import org.example.service.DealService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final DealService dealService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "clients/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("clientTypes", Client.ClientType.values());
        return "clients/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Client client,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("clientTypes", Client.ClientType.values());
            return "clients/form";
        }
        clientService.save(client);
        redirectAttributes.addFlashAttribute("successMessage", "Клиент успешно добавлен!");
        return "redirect:/clients";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        return clientService.findById(id)
                .map(client -> {
                    model.addAttribute("client", client);
                    model.addAttribute("clientDeals", dealService.findByClient(id));
                    return "clients/view";
                })
                .orElse("redirect:/clients");
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return clientService.findById(id)
                .map(client -> {
                    model.addAttribute("client", client);
                    model.addAttribute("clientTypes", Client.ClientType.values());
                    return "clients/form";
                })
                .orElse("redirect:/clients");
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                        @Valid @ModelAttribute Client client,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("clientTypes", Client.ClientType.values());
            return "clients/form";
        }
        client.setId(id);
        clientService.save(client);
        redirectAttributes.addFlashAttribute("successMessage", "Данные клиента успешно обновлены!");
        return "redirect:/clients";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        clientService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Клиент успешно удалён!");
        return "redirect:/clients";
    }
}

