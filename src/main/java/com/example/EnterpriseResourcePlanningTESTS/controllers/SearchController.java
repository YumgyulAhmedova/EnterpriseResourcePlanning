package com.example.EnterpriseResourcePlanningTESTS.controllers;

import com.example.EnterpriseResourcePlanningTESTS.entities.Protocol;
import com.example.EnterpriseResourcePlanningTESTS.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")

public class SearchController {

    @Autowired
    private SearchService searchService;


    @GetMapping()
    private String getAllProtocols(Model model) {
        String keyword = null;
        int weekNumber = 0;
        return listByPage(model, 1, "name", "asc", keyword, weekNumber);
    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
                             @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword,  @Param("weekNumber") int weekNumber) {
        Page<Protocol> page = searchService.listAll(currentPage, sortField, sortDir, keyword, weekNumber);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Protocol> listProtocols = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listProtocols", listProtocols);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("weekNumber", weekNumber);

        int total = searchService.calculateTotalHours(listProtocols);
        model.addAttribute("total", total);

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDir", reverseSortDir);

        return "Protocols/search";
    }


}
