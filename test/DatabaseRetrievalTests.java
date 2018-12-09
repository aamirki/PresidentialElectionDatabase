import model.DatabaseRetrieval;
import model.ElectionResults;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DatabaseRetrievalTests {

  DatabaseRetrieval dbR = new DatabaseRetrieval();

  @Test
  public void testGetStates() {
    String[] states = {
        "Alabama",
        "Alaska",
        "Arizona",
        "Arkansas",
        "California",
        "Colorado",
        "Connecticut",
        "Delaware",
        "Florida",
        "Georgia",
        "Hawaii",
        "Idaho",
        "Illinois",
        "Indiana",
        "Iowa",
        "Kansas",
        "Kentucky",
        "Louisiana",
        "Maine",
        "Maryland",
        "Massachusetts",
        "Michigan",
        "Minnesota",
        "Mississippi",
        "Missouri",
        "Montana",
        "Nebraska",
        "Nevada",
        "New Hampshire",
        "New Jersey",
        "New Mexico",
        "New York",
        "North Dakota",
        "North Carolina",
        "Ohio",
        "Oklahoma",
        "Oregon",
        "Pennsylvania",
        "Rhode Island",
        "South Carolina",
        "South Dakota",
        "Tennessee",
        "Texas",
        "Utah",
        "Vermont",
        "Virginia",
        "Washington",
        "West Virginia",
        "Wisconsin",
        "Wyoming",
        "District of Columbia"
    };
    assertArrayEquals(states, dbR.getStates());
  }

  @Test
  public void testGetStateIDDifferentCase() {
    assertEquals(1, dbR.getStateID("Alabama"));
    assertEquals(1, dbR.getStateID("alabama"));
    assertEquals(1, dbR.getStateID("ALAbamA"));
  }

  @Test
  public void testGetStateID() {
    assertEquals(51, dbR.getStateID("district of columbia"));
    assertEquals(25, dbR.getStateID("Missouri"));
    assertEquals(11, dbR.getStateID("Hawaii"));
    assertEquals(37, dbR.getStateID("Oregon"));
  }

  @Test
  public void testGetStateName() {
    assertEquals("District of Columbia", dbR.getStateName(51));
    assertEquals("Missouri", dbR.getStateName(25));
    assertEquals("Hawaii", dbR.getStateName(11));
    assertEquals("Oregon", dbR.getStateName(37));
  }

  @Test
  public void testGetCountyID() {
    assertEquals(141, dbR.getCountyID("Lincoln", 4));
    assertEquals(1817, dbR.getCountyID("bronx", 32));
    assertEquals(1, dbR.getCountyID("AUTAUGA", 1));
    assertEquals(847, dbR.getCountyID("O'BRIEN", 15));
  }

  @Test
  public void testGetMetrics() {
    String[] metrics = new String[] {
        "Democratic Percentage",
        "Republican Percentage",
        "Democratic Vote",
        "Republican Vote",
        "White Percentage",
        "Black Percentage",
        "Asian Percentage",
        "Native Percentage",
        "Other Race Percentage",
        "Hispanic Percentage"
    };
    assertArrayEquals(metrics, dbR.getMetrics());
  }

  @Test
  public void testGetCounties() {
    String[] alabamaCounties = new String[] {
        "AUTAUGA",
        "BALDWIN",
        "BARBOUR",
        "BIBB",
        "BLOUNT",
        "BULLOCK",
        "BUTLER",
        "CALHOUN",
        "CHAMBERS",
        "CHEROKEE",
        "CHILTON",
        "CHOCTAW",
        "CLARKE",
        "CLAY",
        "CLEBURNE",
        "COFFEE",
        "COLBERT",
        "CONECUH",
        "COOSA",
        "COVINGTON",
        "CRENSHAW",
        "CULLMAN",
        "DALE",
        "DALLAS",
        "DEKALB",
        "ELMORE",
        "ESCAMBIA",
        "ETOWAH",
        "FAYETTE",
        "FRANKLIN",
        "GENEVA",
        "GREENE",
        "HALE",
        "HENRY",
        "HOUSTON",
        "JACKSON",
        "JEFFERSON",
        "LAMAR",
        "LAUDERDALE",
        "LAWRENCE",
        "LEE",
        "LIMESTONE",
        "LOWNDES",
        "MACON",
        "MADISON",
        "MARENGO",
        "MARION",
        "MARSHALL",
        "MOBILE",
        "MONROE",
        "MONTGOMERY",
        "MORGAN",
        "PERRY",
        "PICKENS",
        "PIKE",
        "RANDOLPH",
        "RUSSELL",
        "SHELBY",
        "ST. CLAIR",
        "SUMTER",
        "TALLADEGA",
        "TALLAPOOSA",
        "TUSCALOOSA",
        "WALKER",
        "WASHINGTON",
        "WILCOX",
        "WINSTON"
    };
    String[] wyomingCounties = new String[] {
        "ALBANY",
        "BIG HORN",
        "CAMPBELL",
        "CARBON",
        "CONVERSE",
        "CROOK",
        "FREMONT",
        "GOSHEN",
        "HOT SPRINGS",
        "JOHNSON",
        "LARAMIE",
        "LINCOLN",
        "NATRONA",
        "NIOBRARA",
        "PARK",
        "PLATTE",
        "SHERIDAN",
        "SUBLETTE",
        "SWEETWATER",
        "TETON",
        "UINTA",
        "WASHAKIE",
        "WESTON"
    };
    assertArrayEquals(alabamaCounties, dbR.getCountiesFromDatabase(1));
    assertArrayEquals(wyomingCounties, dbR.getCountiesFromDatabase(50));
  }


  @Test
  public void testGetStateTotal() {
    ElectionResults resultsIdaho = dbR.getStateTotalFromDatabase(12, 1988);
    ElectionResults resultsDC = dbR.getStateTotalFromDatabase(51, 2016);
    assertEquals(253881, resultsIdaho.getRepVotes());
    assertEquals(147272, resultsIdaho.getDemVotes());
    assertEquals(7815, resultsIdaho.getOtherVotes());
    assertEquals(12723, resultsDC.getRepVotes());
    assertEquals(282830, resultsDC.getDemVotes());
    assertEquals(15715, resultsDC.getOtherVotes());
  }

  @Test
  public void testGetCountyTotal() {
    ElectionResults resultsNassauNY = dbR.getCountyTotalFromDatabase(1844, 1972);
    ElectionResults resultsMiamiDade = dbR.getCountyTotalFromDatabase(351, 1992);
    assertEquals(438723, resultsNassauNY.getRepVotes());
    assertEquals(252831, resultsNassauNY.getDemVotes());
    assertEquals(1875, resultsNassauNY.getOtherVotes());
    assertEquals(235313, resultsMiamiDade.getRepVotes());
    assertEquals(254609, resultsMiamiDade.getDemVotes());
    assertEquals(54921, resultsMiamiDade.getOtherVotes());
  }

  @Test
  public void testGetWhitePercentage() {
    assertEquals(73.6476F, dbR.getWhitePercentage(1, 1992), 0.001F);
    assertEquals(41.4852F, dbR.getWhitePercentage(11, 2012), 0.001F);
  }

  @Test
  public void testGetBlackPercentage() {
    assertEquals(25.2613F, dbR.getBlackPercentage(1, 1992), 0.001F);
    assertEquals(2.8538F, dbR.getBlackPercentage(11, 2012), 0.001F);
  }

  @Test
  public void testGetAsianPercentage() {
    assertEquals(0.5395F, dbR.getAsianPercentage(1, 1992), 0.001F);
    assertEquals(57.4114F, dbR.getAsianPercentage(11, 2012), 0.001F);
  }

  @Test
  public void testGetNativePercentage() {
    assertEquals(0.4085F, dbR.getNativePercentage(1, 1992), 0.001F);
    assertEquals(28.6167F, dbR.getNativePercentage(11, 2012), 0.001F);
  }

  @Test
  public void testGetOtherPercentage() {
    assertEquals(0.1431F, dbR.getOtherRacePercentage(1, 1992), 0.001F);
    assertEquals(2.5141F, dbR.getOtherRacePercentage(11, 2012), 0.001F);
  }

  @Test
  public void testGetHispanicPercentage() {
    assertEquals(0.6095F, dbR.getHispanicPercentage(1, 1992), 0.001F);
    assertEquals(8.8835F, dbR.getHispanicPercentage(11, 2012), 0.001F);
  }




}
