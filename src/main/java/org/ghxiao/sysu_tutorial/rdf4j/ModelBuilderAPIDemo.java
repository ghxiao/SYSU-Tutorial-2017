package org.ghxiao.sysu_tutorial.rdf4j;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;

public class ModelBuilderAPIDemo {
    public static void main(String[] args) {
        ModelBuilder builder = new ModelBuilder();

// set some namespaces
        builder.setNamespace("ex", "http://example.org/")
                .setNamespace(FOAF.NS);

        builder.namedGraph("ex:graph1")      // add a new named graph to the model
                .subject("ex:john")        // add  several statements about resource ex:john
                .add(FOAF.NAME, "John")  // add the triple (ex:john, foaf:name "John") to the named graph
                .add(FOAF.AGE, 42)
                .add(FOAF.MBOX, "john@example.org");

        // add a triple to the default graph
        builder.defaultGraph().add("ex:graph1", RDF.TYPE, "ex:Graph");

        // return the Model object
        Model m = builder.build();

        m.forEach(System.out::println);
    }
}
