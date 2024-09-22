import org.junit.Before;
import org.junit.Test;

import stocks.model.Date;

import static org.junit.Assert.assertEquals;

/**
 * This is a testing class for the date class to test all public methods within date.
 */
public class DateTest {
  private Date date;
  private Date date2;
  private Date date3;
  private Date date5;
  private Date date6;
  private Date date7;
  private Date date8;
  private Date date9;
  private Date date10;

  /**
   * This method sets up the data to be used for testing the methods in date.
   */
  @Before
  public void setUp() {
    this.date = new Date(11, 3, 2005);
    this.date2 = new Date(17, 8, 2007);
    this.date3 = new Date(29, 2, 4000);
    this.date5 = new Date(2, 1, 1);
    this.date6 = new Date(29, 12, 2005);
    this.date7 = new Date(27, 1, 0);
    this.date8 = new Date(4, 3, 4000);
    this.date9 = new Date(28, 2, 4000);
    this.date10 = new Date(28, 2, 2005);
  }

  /**
   * This tests to see if an exception is thrown if an invalid year is inputted.
   */
  @Test
  public void testWrongYear() {
    try {
      new Date(6, 3, -4);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This is not a valid date");
    }
  }

  /**
   * This tests to see if an exception is thrown if an invalid month is inputted.
   */
  @Test
  public void testWrongMonth() {
    try {
      new Date(13, 14, 2005);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This is not a valid date");
    }

    try {
      new Date(13, 0, 2054);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This is not a valid date");
    }
  }

  /**
   * This tests to see if an exception is thrown if an invalid day is inputted.
   */
  @Test
  public void testWrongDay() {
    // Months that have 31 days
    try {
      new Date(0, 12, 2005);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This is not a valid date");
    }

    try {
      new Date(34, 12, 2005);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This is not a valid date");
    }

    // Months that have 30 days
    try {
      new Date(31, 4, 2004);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This is not a valid date");
    }

    try {
      new Date(0, 4, 2004);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This is not a valid date");
    }

    // February leap year
    try {
      new Date(30, 2, 4000);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This is not a valid date");
    }

    try {
      new Date(0, 2, 4000);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This is not a valid date");
    }

    // February non leap year
    try {
      new Date(29, 2, 3999);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This is not a valid date");
    }

    try {
      new Date(0, 2, 2005);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This is not a valid date");
    }
  }

  /**
   * This tests the advance method.
   */
  @Test
  public void testAdvance() {
    // Testing for increasing days
    assertEquals("2005-03-11", this.date.toString());
    this.date.advance(1);
    assertEquals("2005-03-12", this.date.toString());

    assertEquals("2007-08-17", this.date2.toString());
    this.date2.advance(16);
    assertEquals("2007-09-02", this.date2.toString());

    assertEquals("2005-12-29", this.date6.toString());
    this.date6.advance(5);
    assertEquals("2006-01-03", this.date6.toString());

    // Testing for decreasing days
    assertEquals("4000-02-29", this.date3.toString());
    this.date3.advance(-1);
    assertEquals("4000-02-28", this.date3.toString());

    assertEquals("0001-01-02", this.date5.toString());
    this.date5.advance(-3);
    assertEquals("0000-12-30", this.date5.toString());

    assertEquals("0000-01-27", this.date7.toString());
    this.date7.advance(-30);
    assertEquals("0000-01-01", this.date7.toString());

    // Non leap year
    assertEquals("2005-02-28", this.date10.toString());
    this.date10.advance(1);
    assertEquals("2005-03-01", this.date10.toString());

    assertEquals("2005-03-12", this.date.toString());
    this.date.advance(-12);
    assertEquals("2005-02-28", this.date.toString());

    // Leap Year
    assertEquals("4000-02-28", this.date9.toString());
    this.date9.advance(1);
    assertEquals("4000-02-29", this.date9.toString());

    assertEquals("4000-03-04", this.date8.toString());
    this.date8.advance(-4);
    assertEquals("4000-02-29", this.date8.toString());
  }

  /**
   * This tests the toString method.
   */
  @Test
  public void testToString() {
    assertEquals("2005-03-11", this.date.toString());
    assertEquals("2007-08-17", this.date2.toString());
    assertEquals("4000-02-29", this.date3.toString());
    assertEquals("0001-01-02", this.date5.toString());
  }
}
