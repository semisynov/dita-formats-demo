package ru.vtb.dita.formats.demo.dita;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DitaFormat {

    XHTML("xhtml"),
    ECLIPSE_HELP("eclipsehelp"),
    HTML5("html5"),
    HTML_HELP("htmlhelp"),
    DITA("dita"),
    PDF("pdf"),
    PDF2("pdf2"),
    MARKDOWN("markdown"),
    MARKDOWN_GITHUB("markdown_github"),
    MARKDOWN_GITBOOK("markdown_gitbook"),
    XDITA("xdita");

    private final String value;
}
