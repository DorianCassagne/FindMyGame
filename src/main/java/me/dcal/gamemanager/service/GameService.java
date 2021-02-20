package me.dcal.gamemanager.service;


import me.dcal.gamemanager.model.Game;
import me.dcal.gamemanager.model.GameList;

public interface GameService {
	public GameList getListGame(String name,String genre,String platform,String publisher) ;
	
	public Game getGame(String res);
	

}
