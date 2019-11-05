package duke.model;

import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.NoRecentItineraryException;
import duke.commons.exceptions.DuplicateRouteException;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.lists.VenueList;
import duke.model.locations.BusStop;
import duke.model.planning.Itinerary;
import duke.model.planning.Recommendation;
import duke.model.profile.ProfileCard;
import duke.model.transports.BusService;
import duke.model.transports.Route;
import duke.model.transports.TransportationMap;
import duke.storage.Storage;

import java.util.HashMap;
import java.util.List;

/**
 * Implements the methods defined in the Model Interface.
 */
public class ModelManager implements Model {
    private Storage storage;
    private EventList events;
    private RouteList routes;
    private TransportationMap map;
    private ProfileCard profileCard;
    private Recommendation recommendations;
    private HashMap<String, Itinerary> itineraryTable;
    private Itinerary recentItinerary;

    /**
     * Constructs a new ModelManager object.
     */
    public ModelManager() {
        storage = new Storage();
        events = storage.getEvents();
        map = storage.getMap();
        routes = storage.getRoutes();
        profileCard = storage.getProfileCard();
        recommendations = storage.getRecommendations();
        itineraryTable = storage.getItineraryTable();
    }

    @Override
    public String getName() {
        return profileCard.getPersonName();
    }

    @Override
    public TransportationMap getMap() {
        return map;
    }

    @Override
    public EventList getEvents() {
        return events;
    }

    @Override
    public void setEvents(EventList events) {
        this.events = events;
    }

    @Override
    public RouteList getRoutes() {
        return routes;
    }

    @Override
    public EventList getSortedList() {
        return events.getSortedList();
    }

    @Override
    public HashMap<String, BusStop> getBusStops() {
        return map.getBusStopMap();
    }

    @Override
    public List<BusService> getBusService() {
        return null;
    }

    @Override
    public Recommendation getRecommendations() {
        return recommendations;
    }

    @Override
    public HashMap<String, Itinerary> getItineraryTable() {
        return itineraryTable;
    }

    @Override
    public void setRecentItinerary(Itinerary recentItinerary) {
        this.recentItinerary = recentItinerary;
    }

    @Override
    public Itinerary getRecentItinerary() throws NoRecentItineraryException {
        if (recentItinerary == null) {
            throw new NoRecentItineraryException();
        }
        return recentItinerary;
    }

    @Override
    public void setNewItinerary(Itinerary itinerary) {
        this.itineraryTable.put(itinerary.getName(), itinerary);
    }

    @Override
    public void confirmRecentItinerary(String name) {
        recentItinerary.setName(name);
        this.itineraryTable.put(name, recentItinerary);
    }

    @Override
    public VenueList getEventVenues() {
        return new VenueList(events);
    }

    @Override
    public ProfileCard getProfileCard() {
        return profileCard;
    }

    /**
     * Shows the Itinerary specified by a give name.
     *
     * @param name The serial number of the Itinerary.
     */
    @Override
    public Itinerary getItinerary(String name) {
        return itineraryTable.get(name);
    }

    /**
     * Adds a route to the list of routes.
     *
     * @param route The route to add.
     */
    @Override
    public void addRoute(Route route) throws DuplicateRouteException {
        routes.add(route);
    }

    /**
     * Saves the file to local storage.
     *
     * @throws FileNotSavedException If the file cannot be saved.
     */
    @Override
    public void save() throws FileNotSavedException {
        storage.write();
    }
}
