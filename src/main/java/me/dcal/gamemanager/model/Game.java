package me.dcal.gamemanager.model;

import java.util.List;


public class Game {
	public String name;
	public List<String> publisher;
	public List<String> releaseDates;
	public List<String> platform;
	public List<String> genre;
	
	public String toString() {
		return  "name : " + name + "\n" +
				publisher.toString() + "\n" +
				releaseDates.toString() + "\n" +
				platform.toString() + "\n" +
				genre.toString() + "\n"
				;
	}
	
	
	

}
