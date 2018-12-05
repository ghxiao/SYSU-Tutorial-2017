package org.ghxiao.sysu_tutorial.datalog;

import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.rio.*;
import org.semanticweb.owlapi.vocab.XSDVocabulary;


import java.io.*;


public class TurtleToDatalog {

    public static void main(String[] args) throws IOException {

        FileInputStream inputStream = new FileInputStream(args[0]);

        FileWriter fileWriter = new FileWriter(args[1]);

        RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);

        rdfParser.setRDFHandler(new RDFHandler() {
            @Override
            public void startRDF() throws RDFHandlerException {

            }

            @Override
            public void endRDF() throws RDFHandlerException {
                try {
                    fileWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void handleNamespace(String prefix, String uri) throws RDFHandlerException {

            }

            @Override
            public void handleStatement(Statement st) throws RDFHandlerException {
                System.out.println(st);
                try {


                    StringBuilder line = new StringBuilder();

                    String p;
                    if (st.getPredicate().equals(RDF.TYPE)) {
                        p = normalizeIRI((IRI) st.getObject());
                    } else {
                        p = normalizeIRI(st.getPredicate());
                    }

                    line.append(p).append("(");

                    if (st.getPredicate().equals(RDF.TYPE)) {
                        line.append(format(st.getSubject()));
                    } else {
                        line.append(format(st.getSubject()));
                        line.append(", ");
                        line.append(format(st.getObject()));
                    }


                    line.append(").\n");
                    fileWriter.append(line);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void handleComment(String comment) throws RDFHandlerException {

            }
        });

        rdfParser.parse(inputStream, "http://example.org/");

    }

    public static String format(Value rdfValue) {
        if (rdfValue instanceof IRI) {
            return "\"<" + ((IRI) rdfValue).toString() + ">\"";

        } else if (rdfValue instanceof Literal) {

            Literal literal = (Literal) rdfValue;

            //if(literal.getDatatype().equals(XMLSchema.STRING)){
                return "\"" + ((Literal) rdfValue).getLabel() + "\"";
            //}

            //return ((Literal) rdfValue).toString();

        } else {// if (resource instanceof BlankNode) {
            return "";
        }



    }

    public static String normalizeIRI(IRI iri) {
        String fragment = iri.getLocalName();
        if (fragment != null) {
            //fragment = replaceUmlauts(fragment);
            return fragment.replaceAll("[_-]", "").toLowerCase();
        } else {
            final String iriString = iri.toString();
            int i = iriString.lastIndexOf('/') + 1;
            return iriString.substring(i).replace("_-", "").toLowerCase();

        }
    }
}
