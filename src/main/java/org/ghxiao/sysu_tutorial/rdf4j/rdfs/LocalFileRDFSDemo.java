package org.ghxiao.sysu_tutorial.rdf4j.rdfs;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

import java.io.File;
import java.io.IOException;

public class LocalFileRDFSDemo {
    public static void main(String[] args) throws IOException {
//        Repository repo = new SailRepository(new MemoryStore());
        Repository repo = new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));

        repo.initialize();

        try (RepositoryConnection conn = repo.getConnection()) {
            conn.add(new File("src/main/resources/animal.ttl"), null, RDFFormat.TURTLE);

            String queryString = "prefix ex:  <http://ex.org/> \n" +
                    "prefix rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                    "prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> \n" +
                    "\n" +
                    "SELECT * WHERE {\n" +
                    "ex:xiao rdf:type ?type\n" +
                    "}";
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            //TupleQueryResult result = tupleQuery.evaluate();
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    System.out.println(bindingSet);
                }
            }
        }

        repo.shutDown();

    }
}
