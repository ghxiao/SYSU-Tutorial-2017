package org.ghxiao.sysu_tutorial.rdf4j.rdf;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

public class RemoteSparqlEndpointDemo {

    public static void main(String[] args) {
        String sparqlEndpoint = "http://dbpedia.org/sparql";
        Repository repo = new SPARQLRepository(sparqlEndpoint);
        repo.initialize();

        try (RepositoryConnection conn = repo.getConnection()) {
            String queryString = "SELECT * WHERE {\n" +
                    " ?movie a                                            <http://dbpedia.org/ontology/Film> .\n" +
                    " ?movie <http://dbpedia.org/ontology/country>        <http://it.dbpedia.org/resource/Italia> .\n" +
                    " ?movie <http://dbpedia.org/ontology/filmColourType> ?colour .\n" +
                    " FILTER ( ?colour in (\"B/N\"@it, \"bianco/nero\"@it ) )\n" +
                    "}";
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            //TupleQueryResult result = tupleQuery.evaluate();
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    Value movie = bindingSet.getValue("movie");
                    System.out.println(movie);
                    // do something interesting with the values here...
                }
            }
        }
    }
}
