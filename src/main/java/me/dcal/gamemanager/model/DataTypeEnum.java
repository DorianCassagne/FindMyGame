package me.dcal.gamemanager.model;

public enum DataTypeEnum {
	ALL("?Name ?Publisher ?Platform  ?Release"),NAME("?Name")
	,RELEASE("?Release"),GENRE("?Genre"),UNIQUE("?Name ?Publisher")
	,PUBLISHER("?Publisher"),PLATFORM("?Platform"),EMPTY("");

	private String query;
	private DataTypeEnum(String query) {
		this.setQuery(query);
	}
	public String getQuery() {
		return query;
	}
	void setQuery(String query) {
		this.query = query;
	}

}
