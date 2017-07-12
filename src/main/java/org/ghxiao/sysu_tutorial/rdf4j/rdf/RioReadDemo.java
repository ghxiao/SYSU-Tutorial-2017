package org.ghxiao.sysu_tutorial.rdf4j.rdf;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class RioReadDemo {
    public static void main(String[] args) throws IOException {
        ValueFactory factory = SimpleValueFactory.getInstance();

        IRI guohui = factory.createIRI("http://example.org/#guohui");

        InputStream inputStream = new FileInputStream(new File("src/main/resources/foaf.ttl"));

        RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);

        Model model = new LinkedHashModel();
        rdfParser.setRDFHandler(new StatementCollector(model));

        rdfParser.parse(inputStream, "http://example.org/");

        model.filter(guohui, FOAF.KNOWS, null)
                .objects()
                .forEach(System.out::println);

    }
}
