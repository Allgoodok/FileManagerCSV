package com.vlad.controller;

import com.vlad.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@Component
public class FileListController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping(value = "files")
    public String listFiles(Model model) throws IOException {

        model.addAttribute("documents", documentService.getDocuments());

        return "files";
    }

    @RequestMapping(value= "files/{doc.title}")
    public String listHeader(@PathVariable("doc.title") String pathToFile, Model model) throws IOException {
        model.addAttribute("headersok", documentService.getHeader(pathToFile));
        model.addAttribute("lines", documentService.getRows(pathToFile));
        model.addAttribute("nullListVals", documentService.getColumns(pathToFile));
        model.addAttribute("suggestedSchema", documentService.suggestColumnTypes(pathToFile));
        return "header";
    }

}
