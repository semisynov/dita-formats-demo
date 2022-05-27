package ru.vtb.dita.formats.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.vtb.dita.formats.demo.dita.DitaFormat;
import ru.vtb.dita.formats.demo.service.DitaProcessService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final DitaProcessService ditaProcessService;

    @PostMapping("/")
    public void saveFiles(@RequestParam("files") List<MultipartFile> files,
                          @RequestParam("inputFormat") DitaFormat inputFormat,
                          @RequestParam("outputFormat") DitaFormat outputFormat) {
        ditaProcessService.storeAndProcessFile(files, inputFormat, outputFormat);
    }

    @PostMapping("/demo")
    public void demoDitaTransform() {
        ditaProcessService.demoTransform();
    }
}
