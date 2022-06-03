package ru.dita.formats.demo.dita;

import lombok.extern.slf4j.Slf4j;
import org.dita.dost.Processor;
import org.dita.dost.ProcessorFactory;
import org.dita.dost.exception.DITAOTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dita.formats.demo.utils.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

@Slf4j
@Component
public class DitaProcessor {

    private final String ditaDir;
    private final String tmpDir;
    private final String outputDir;

    public DitaProcessor(@Value("${dita.dita-dir}") String ditaDir,
                         @Value("${dita.tmp-dir}") String tmpDir,
                         @Value("${dita.output-dir}") String outputDir) {
        this.ditaDir = ditaDir;
        this.tmpDir = tmpDir;
        this.outputDir = outputDir;
    }

    public void process(Path file, DitaFormat inputFormat, DitaFormat outputFormat) {
        var properties = Map.of("nav-toc", "partial");
        var processor = initProcessor(file.toFile(), inputFormat.getValue(), outputFormat.getValue(), properties);
        try {
            log.info("Start dita process file:{}, from format {} to {}", file.getFileName(), inputFormat, outputFormat);
            processor.run();
            log.info("End dita process file:{}, from format {} to {}", file.getFileName(), inputFormat, outputFormat);
        } catch (DITAOTException e) {
            log.error("Error while dita processing", e);
        }
    }

    private Processor initProcessor(File file, String inputFormat, String outputFormat, Map<String, String> properties) {
        var ditaDirPath = FileUtils.initFolder(ditaDir);
        var tempDir = FileUtils.initFolder(tmpDir);
        var outDir = FileUtils.initFolder(Path.of(outputDir, inputFormat, outputFormat).toString());
        var filePath = file.getAbsoluteFile();

        var processorFactory = ProcessorFactory.newInstance(ditaDirPath);
        processorFactory.setBaseTempDir(tempDir);
        return processorFactory
                .newProcessor(outputFormat)
                .setInput(filePath)
                .setOutputDir(outDir)
                .setProperties(properties);
    }
}
