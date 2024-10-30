package application;

import javafx.scene.control.Button;

public class Series {
	
	 private int SeriesID;
	 private String SeriesName;
	 private String Genre;
	 private int Episodes;
	 private int LastEpisode;
	 private int Seasons;
	 private String Status;
	 private String SeriesType;
	 private String DateWatched;
	 private String Type;
	 private Button AddEpBtn;
	 private Button MinusEpBtn;

	 public Series(int seriesID, String seriesName, String genre, int episodes, int seasons, String status, String seriesType, String dateWatched, int lastEpisode, String type) {
	        this.SeriesID = seriesID;
	        this.SeriesName = seriesName;
	        this.Genre = genre;
	        this.Episodes = episodes;
	        this.Seasons = seasons;
	        this.Status = status;
	        this.SeriesType = seriesType;
	        this.DateWatched = dateWatched;
	        this.LastEpisode = lastEpisode;
	        this.Type = type;
	    }
	 
	 
	 public int getSeriesID() {
	        return SeriesID;
	    }

	    public String getSeriesName() {
	        return SeriesName;
	    }

	    public String getGenre() {
	        return Genre;
	    }

	    public int getEpisodes() {
	        return Episodes;
	    }

	    public int getSeasons() {
	        return Seasons;
	    }

	    public String getStatus() {
	        return Status;
	    }

	    public String getSeriesType() {
	        return SeriesType;
	    }

	    public String getDateWatched() {
	        return DateWatched;
	    }


	    public int getLastEpisode() {
	        return LastEpisode;
	    }
	    
	    public String getType() {
	    	return Type;
	    }
}
