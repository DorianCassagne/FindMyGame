package me.dcal.gamemanager.model;

import java.util.ArrayList;
import java.util.List;

public class GameList {
	public List<Element>gameList;
	public GameList() {
		this.gameList=new ArrayList<>();
	}
	public String toString() {
		return "GameList:\n" + gameList.toString();
	}
	public List<Element> getGameList() {
		return gameList;
	}
	public void setGameList(List<Element> gameList) {
		this.gameList = gameList;
	}

}
