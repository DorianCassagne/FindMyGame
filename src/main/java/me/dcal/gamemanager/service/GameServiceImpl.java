package me.dcal.gamemanager.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.stereotype.Service;

import me.dcal.gamemanager.model.DataTypeEnum;
import me.dcal.gamemanager.model.Element;
import me.dcal.gamemanager.model.Game;
import me.dcal.gamemanager.model.GameList;

@Service
@Transactional
public class GameServiceImpl implements GameService {
	
	
	public GameList getListGame(String name,String genre,String platform,String publisher) {
		ResultSet rs  = gameQuery(name, genre, platform, publisher, DataTypeEnum.EMPTY);
		return resultToListGame(rs);
	}
	
	public Game getGame(String res) {
		Game game = new Game();
		game.name = resultToList(singleGameQuery(DataTypeEnum.NAME, res), DataTypeEnum.NAME).get(0);
		game.name = game.name.substring(0,game.name.length()-3);
		game.publisher = resultToList(singleGameQuery(DataTypeEnum.PUBLISHER, res), DataTypeEnum.PUBLISHER);
		game.releaseDates = resultToList(singleGameQuery(DataTypeEnum.RELEASE, res), DataTypeEnum.RELEASE);
		game.platform = resultToList(singleGameQuery(DataTypeEnum.PLATFORM, res), DataTypeEnum.PLATFORM);
		game.genre = resultToList(singleGameQuery(DataTypeEnum.GENRE, res), DataTypeEnum.GENRE);
		
		return game;
	}
	
	private ResultSet gameQuery(String name,String genre,String platform,String publisher,DataTypeEnum dataType) {
		String nameQ = (name==null||name.equals(""))?"":("FILTER contains(xsd:string(?Name),'"+name+"').\n");
		String genreQ = (genre==null||genre.equals(""))?"":("FILTER contains(xsd:string(?Genre),'"+genre+"').\n");
		String platformQ = (platform==null||platform.equals(""))?"":("FILTER contains(xsd:string(?Platform),'"+platform+"').\n");
		String publisherQ = (publisher==null||publisher.equals(""))?"":("FILTER contains(xsd:string(?Publisher),'"+publisher+"').\n");

		String szEndpoint = "https://dbpedia.org/sparql";
		String szQuery = ""+
				"PREFIX dbo:<http://dbpedia.org/ontology/>\n" + 
				"PREFIX dbp:<http://dbpedia.org/property/>\n" +  
				"PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
				"select distinct ?vg " +
				dataType.getQuery() + 
				" where{\n" + 
				"?vg rdf:type dbo:VideoGame.\n" + 
				"?vg foaf:name ?Name.\n" + 
				"?vg dbo:publisher ?Publisher.\n" + 
				"?vg dbo:computingPlatform ?Platform.\n" + 
				"?vg dbo:releaseDate ?Release.\n" + 
				"?vg dbp:genre ?Genre.\n" + 
				 nameQ +
				 genreQ +
				 platformQ +
				 publisherQ +
				"}" +
				"LIMIT 100";
		
		Query query = QueryFactory.create(szQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(szEndpoint, query);

        return qexec.execSelect();
		
	}
	private ResultSet singleGameQuery(DataTypeEnum dataType,String resource) {

		String szEndpoint = "https://dbpedia.org/sparql";
		String szQuery = ""+
				"PREFIX dbo:<http://dbpedia.org/ontology/>\n" + 
				"PREFIX dbp:<http://dbpedia.org/property/>\n" + 
				"PREFIX dbr:<http://dbpedia.org/resource/>\n" + 
				"PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
				"select distinct " +
				dataType.getQuery() + 
				" where{\n" + 
				"<" + resource + "> foaf:name ?Name.\n" + 
				"<" + resource + "> dbo:publisher ?Publisher.\n" + 
				"<" + resource + "> dbo:computingPlatform ?Platform.\n" + 
				"<" + resource + "> dbo:releaseDate ?Release.\n" + 
				"<" + resource + "> dbp:genre ?Genre.\n" + 
				"}" +
				"LIMIT 30";
		Query query = QueryFactory.create(szQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(szEndpoint, query);

        return qexec.execSelect();
		
	}
	
	private ArrayList<String> resultToList(ResultSet rs,DataTypeEnum dtype){
		ArrayList<String> list = new ArrayList<>();
		 while (rs.hasNext()) {
	        	QuerySolution qs= rs.next();
	        	list.add(Element.urlToName(qs.get(dtype.getQuery()).toString()));
		 }
		 return list; 
	}
	private GameList resultToListGame(ResultSet rs){
		GameList gl=new GameList();
		while (rs.hasNext()) {
	       	QuerySolution qs= rs.next();
	       	gl.getGameList().add(Element.urlToElem(qs.getResource("?vg").toString()));
		}
		return gl;
	}

}
