package org.ghxiao.sysu_tutorial.rdf4j;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

import java.io.File;
import java.io.IOException;

public class LocalFileSparqlDemo {
    public static void main(String[] args) throws IOException {
        Repository repo = new SailRepository(new MemoryStore());

        repo.initialize();

        try (RepositoryConnection conn = repo.getConnection()) {
            conn.add(new File("src/main/resources/foaf.ttl"), null, RDFFormat.TURTLE);

            String queryString = "BASE <http://example.org/> \n" +
                    "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n" +
                    "\n" +
                    "SELECT * WHERE {\n" +
                    "<#guohui> foaf:knows ?x.\n" +
                    "?x foaf:name ?n\n" +
                    "}";
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            //TupleQueryResult result = tupleQuery.evaluate();
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    System.out.println(bindingSet.getValue("n"));

                }
            }
        }

        repo.shutDown();

    }
}
