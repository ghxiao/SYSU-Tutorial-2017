package org.ghxiao.sysu_tutorial.owlapi;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class OWLAPILoadDemo {
    public static void main(String[] args) throws OWLOntologyCreationException, FileNotFoundException, OWLOntologyStorageException {
        final OWLOntologyManager manager = OWLManager.createConcurrentOWLOntologyManager();
        final OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File("src/main/resources/pizza.owl"));

        ontology.getOntologyID().getOntologyIRI().ifPresent(
                id -> System.out.println("Ontology ID: " + id)
        );

//        ontology.axioms().forEach(
//                ax -> System.out.println(ax)
//        );

        ontology.classesInSignature().forEach(System.out::println);


    }

}


