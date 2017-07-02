package com.models.user;

import com.models.location.Location;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;

@Entity
public class History {

	@Id
	@GeneratedValue
	int history_id ;
	@ManyToMany 
	@JoinTable(name = "history_location", joinColumns = {
			@JoinColumn(name = "history_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "location_id",
					nullable = false, updatable = false) })
	@MapKeyColumn(name="time")
    private Map <String, Location> positions = new HashMap <String, Location> ();

	@OneToOne (mappedBy="history" )
	private Profile profile ; 
    public Map<String, Location> getPositions() {
        return positions;
    }

    public void setPositions(Map<String, Location> positions) {
        this.positions = positions;
    }
}
