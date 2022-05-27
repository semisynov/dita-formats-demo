package ru.vtb.dita.formats.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vtb.dita.formats.demo.config.ApplicationProperties;
import ru.vtb.dita.formats.demo.dita.DitaFormat;
import ru.vtb.dita.formats.demo.dita.DitaProcessor;
import ru.vtb.dita.formats.demo.utils.FileUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DitaProcessService {

    private final ApplicationProperties properties;
    private final DitaProcessor ditaProcessor;

    public void demoTransform() {
        var demoProperties = properties.getDemo();
        log.info("Start demo transform process");
        for (DitaFormat outputFormat : DitaFormat.values()) {
            var ditaFile = Path.of(demoProperties.getInput(), DitaFormat.DITA.getValue(), demoProperties.getDitaFile());
            ditaProcessor.process(ditaFile, DitaFormat.DITA, outputFormat);
            var markdownFile = Path.of(demoProperties.getInput(), DitaFormat.MARKDOWN.getValue(), demoProperties.getMarkdownFile());
            ditaProcessor.process(markdownFile, DitaFormat.MARKDOWN, outputFormat);
        }
        log.info("End demo transform process");
    }

    public void storeAndProcessFile(List<MultipartFile> files, DitaFormat inputFormat, DitaFormat outputFormat) {
        var rootLocation = Paths.get(properties.getUploadDir());
        files
                .stream()
                .map(file -> FileUtils.saveUploadFile(rootLocation, file))
                .forEach(savedFile -> ditaProcessor.process(savedFile, inputFormat, outputFormat));
    }
}
