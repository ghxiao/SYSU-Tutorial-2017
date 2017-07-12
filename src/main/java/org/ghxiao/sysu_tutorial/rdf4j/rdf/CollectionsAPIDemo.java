package org.ghxiao.sysu_tutorial.rdf4j.rdf;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.util.RDFCollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionsAPIDemo {
    public static void main(String[] args) {
        String ns = "http://example.org/";
        ValueFactory vf = SimpleValueFactory.getInstance();

        // IRI for ex:favoriteLetters
        IRI favoriteLetters = vf.createIRI(ns, "favoriteLetters");

        // IRI for ex:John
        IRI john = vf.createIRI(ns, "John");

        // create a list of letters
        List<Literal> letters = Arrays.asList(vf.createLiteral("A"), vf.createLiteral("B"), vf.createLiteral("C"));

        // create a head resource for our list
        Resource head = vf.createBNode();

        // convert our list and add it to a newly-created Model
        Model aboutJohn = RDFCollections.asRDF(letters, head, new LinkedHashModel());

        // set the ex:favoriteLetters property to link to the head of the list
        aboutJohn.add(john, favoriteLetters, head);

        // get the value of the ex:favoriteLetters property
        Resource node = Models.objectResource(aboutJohn.filter(john, favoriteLetters, null))
                .orElse(null);
// Convert its collection back to an ArrayList of values
        if (node != null) {
            List<Value> values = RDFCollections.asValues(aboutJohn, node, new ArrayList<Value>());

            System.out.println(values);

        }

    }
}
