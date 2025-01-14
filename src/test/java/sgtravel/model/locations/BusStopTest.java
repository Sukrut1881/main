package sgtravel.model.locations;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.FileLoadFailException;
import sgtravel.commons.exceptions.QueryFailedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BusStopTest {
    private BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
            1.39585817355572, 103.75427816224409);
    private BusStop v2 = new BusStop("25269", "Tuas Checkpoint", "Bus stop 2",
            1.34942405517095, 103.636127935782);

    @Test
    void getBusCode() {
        assertEquals("45039", v1.getBusCode());
        assertNotEquals("45039", v2.getBusCode());
    }

    @Test
    void getAddress() {
        assertEquals("Opp Yew Tee Ind Est", v1.getAddress());
        assertNotEquals("Opp Yew Tee Ind Est", v2.getAddress());
    }

    @Test
    void getDescription() {
        assertEquals("Woodlands Rd", v1.getDescription());
        assertNotEquals("Woodlands Rd", v2.getDescription());
    }

    @Test
    void getLatitude() {
        assertEquals(1.39585817355572, v1.getLatitude());
        assertNotEquals(1.39585817355572, v2.getLatitude());
    }

    @Test
    void getLongitude() {
        assertEquals(103.75427816224409, v1.getLongitude());
        assertNotEquals(103.75427816224409, v2.getLongitude());
    }

    @Test
    void getDistX() {
        assertEquals(0, v1.getDistX());
    }

    @Test
    void getDistY() {
        assertEquals(0, v2.getDistY());
    }

    @Test
    void getDistance() {
        assertTrue(v2.getDistance(v1) < 15000);
        assertTrue(v1.getDistance(v2) > 1000);
        assertEquals(v1.getDistance(v2), v2.getDistance(v1));
    }

    @Test
    void setLatitude() {
        v1.setLatitude(0);
        assertEquals(0, v1.getLatitude());
    }

    @Test
    void setLongitude() {
        v2.setLongitude(0);
        assertEquals(0, v2.getLongitude());
    }

    @Test
    void testToString() {
        assertEquals("Opp Yew Tee Ind Est (1.39585817355572, 103.75427816224409)", v1.toString());
    }

    @Test
    void testEquals() {
        Venue v = new Venue("Opp Yew Tee Ind Est", 1.39585817355572, 103.75427816224409,
                0, 0);
        assertTrue(v.equals(v1));
        assertFalse(v2.equals(v1));
    }

    @Test
    void testFetch() throws QueryFailedException, FileLoadFailException {
        ModelStub model = new ModelStub();
        BusStop v3 = new BusStop("45039", "", "", 0, 0);
        v3.fetchData(model);

        assertTrue(v1.equals(v3));
        assertFalse(v2.equals(v3));
    }

    @Test
    void getDisplayInfo() throws FileLoadFailException, QueryFailedException {
        ModelStub model = new ModelStub();
        BusStop busStop = new BusStop("17009", "", "", 0, 0);
        busStop.fetchData(model);

        String expected = "Clementi Int\n" + "Clementi Ave 3\n" + "(1.31491572870629, 103.76412225438476)";
        assertEquals(expected, busStop.getDisplayInfo());
    }
}
