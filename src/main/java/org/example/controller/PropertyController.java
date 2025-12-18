package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.Property;
import org.example.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("properties", propertyService.findAll());
        return "properties/list";
    }

    @GetMapping("/available")
    public String listAvailable(Model model) {
        model.addAttribute("properties", propertyService.findAvailable());
        return "properties/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("property", new Property());
        model.addAttribute("propertyTypes", Property.PropertyType.values());
        model.addAttribute("propertyStatuses", Property.PropertyStatus.values());
        return "properties/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Property property,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("propertyTypes", Property.PropertyType.values());
            model.addAttribute("propertyStatuses", Property.PropertyStatus.values());
            return "properties/form";
        }
        propertyService.save(property);
        redirectAttributes.addFlashAttribute("successMessage", "Объект недвижимости успешно добавлен!");
        return "redirect:/properties";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        return propertyService.findById(id)
                .map(property -> {
                    model.addAttribute("property", property);
                    return "properties/view";
                })
                .orElse("redirect:/properties");
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return propertyService.findById(id)
                .map(property -> {
                    model.addAttribute("property", property);
                    model.addAttribute("propertyTypes", Property.PropertyType.values());
                    model.addAttribute("propertyStatuses", Property.PropertyStatus.values());
                    return "properties/form";
                })
                .orElse("redirect:/properties");
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                        @Valid @ModelAttribute Property property,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("propertyTypes", Property.PropertyType.values());
            model.addAttribute("propertyStatuses", Property.PropertyStatus.values());
            return "properties/form";
        }
        property.setId(id);
        propertyService.save(property);
        redirectAttributes.addFlashAttribute("successMessage", "Объект недвижимости успешно обновлён!");
        return "redirect:/properties";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        propertyService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Объект недвижимости успешно удалён!");
        return "redirect:/properties";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String address,
                        @RequestParam(required = false) Property.PropertyType type,
                        Model model) {
        if (address != null && !address.isEmpty()) {
            model.addAttribute("properties", propertyService.searchByAddress(address));
        } else if (type != null) {
            model.addAttribute("properties", propertyService.findByType(type));
        } else {
            model.addAttribute("properties", propertyService.findAll());
        }
        model.addAttribute("propertyTypes", Property.PropertyType.values());
        return "properties/list";
    }
}

