package duke.model;

import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.RouteDuplicateException;
import duke.logic.CreateMap;
import duke.model.lists.EventList;
import duke.model.lists.RouteList;
import duke.model.lists.VenueList;
import duke.model.locations.BusStop;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.transports.BusService;
import duke.model.transports.Route;
import duke.storage.Storage;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

/**
 * Implements the methods defined in the Model Interface.
 */
public class ModelManager implements Model {
    private Storage storage;
    private EventList events;
    private RouteList routes;
    private CreateMap map;

    /**
     * Constructs a new ModelManager object.
     */
    public ModelManager() {
        storage = new Storage();
        events = storage.getEvents();
        map = storage.getMap();
        routes = storage.getRoutes();
    }

    @Override
    public CreateMap getMap() {
        return map;
    }

    @Override
    public EventList getEvents() {
        return events;
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
    public List<Agenda> getRecommendations(int numDays, Itinerary itinerary) throws DukeException {
        List<Agenda> recommendations = storage.readVenues(numDays);
        itinerary.setTasks(recommendations);
        storage.writeItineraries(itinerary, 2);
        return recommendations;
    }

    @Override
    public VenueList getEventVenues() {
        return new VenueList(events);
    }

    @Override
    public void saveItinerary(Itinerary itinerary) throws FileNotSavedException {
        storage.writeItineraries(itinerary, 1);
    }

    @Override
    public void itineraryListSave(Itinerary itinerary) throws FileNotSavedException, FileNotFoundException {
        storage.writeItinerarySave(itinerary);
    }

    @Override
    public String listItineraries() throws FileLoadFailException {
        return storage.readItineraryList();
    }

    @Override
    public Itinerary getItinerary(String number) throws DukeException {
        return storage.getItinerary(number);
    }

    /**
     * Adds a route to the list of routes.
     *
     * @param route The route to add.
     */
    @Override
    public void addRoute(Route route) throws RouteDuplicateException {
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
