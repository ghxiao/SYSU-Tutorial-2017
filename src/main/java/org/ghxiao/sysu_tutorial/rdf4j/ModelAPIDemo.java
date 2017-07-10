package org.ghxiao.sysu_tutorial.rdf4j;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;

import java.util.Optional;

public class ModelAPIDemo {
    public static void main(String[] args) {
        ValueFactory factory = SimpleValueFactory.getInstance();

        IRI alice = factory.createIRI("http://example.org/alice");
        Literal alicesName = factory.createLiteral("Alice");

        IRI bob = factory.createIRI("http://example.org/bob");

        Literal bobsName = factory.createLiteral("Bob");

        Statement nameStatement = factory.createStatement(bob, FOAF.NAME, bobsName);
        Statement typeStatement = factory.createStatement(bob, RDF.TYPE, FOAF.PERSON);

        // create a new Model to put statements in
        Model model = new LinkedHashModel();
        // add an RDF statement
        model.add(typeStatement);
        // add another RDF statement by simply providing subject, predicate, and object.
        model.add(bob, FOAF.NAME, bobsName);

        model.add(alice, FOAF.NAME, alicesName);
        model.add(alice, RDF.TYPE, FOAF.PERSON);


        //model.forEach(System.out::println);

//        for (Statement s : model.filter(null, RDF.TYPE, FOAF.PERSON)) {
//            System.out.println(s.getSubject());
//        }

//        model.filter(null, RDF.TYPE, FOAF.PERSON)
//                .subjects()
//                .forEach(System.out::println);

//        for (Resource person : model.filter(null, RDF.TYPE, FOAF.PERSON).subjects()) {
//            // get the name of the person (if it exists)
//            Optional<Literal> n = Models.objectLiteral(model.filter(person, FOAF.NAME, null));
//            n.ifPresent(System.out::println);
//        }

        model.filter(null, RDF.TYPE, FOAF.PERSON)
                .subjects()
                .forEach(person -> Models.objectLiteral(model.filter(person, FOAF.NAME, null))
                        .ifPresent(System.out::println));
    }
}
