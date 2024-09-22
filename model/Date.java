package stocks.model;

import java.util.Objects;

/**
 * This Class represents a Date.
 * A date has a day month and year.
 */
public class Date {
  private int year;
  private int month;
  private int day;

  /**
   * Construct a MyDate as long as it is valid.
   *
   * @param day   Day to be on the date
   * @param month Month to be on the date
   * @param year  Year to be on the date
   */
  public Date(int day, int month, int year) {
    // Make sure all positive
    if (day <= 0 || day > 31 || month <= 0 || month > 12 || year < 0) {
      throw new IllegalArgumentException("This is not a valid date");
    }
    // Check Leap Year
    if ((day <= 29) && ((year % 4) == 0)
            && (((year % 100) != 0) || (((year % 400) == 0) && ((year % 100) == 0)))) {
      this.day = day;
      this.month = month;
      this.year = year;
    } else
      // Check any other year
      if (((month < 8) && ((month % 2) == 1)
              || (((month > 7) && ((month % 2) == 0))))
              || (month != 2 && day < 31) || ((month == 2) && day <= 28)) {
        this.day = day;
        this.month = month;
        this.year = year;
      } else {
        throw new IllegalArgumentException("This is not a valid date");
      }
  }

  /**
   * Advances or reverses the date by the specified amount of days.
   *
   * @param days The number of days to advance the current date by.
   */
  public void advance(int days) {
    while (days != 0) {
      if (days > 0) {
        this.day++;
        days--;
        if (day > this.findLastDayOfMonth(this.month, this.year)) {
          this.month++;
          this.day = 1;
          if (this.month > 12) {
            this.month = 1;
            this.year++;
          }
        }
      } else {
        this.day--;
        days++;
        if (day < 1) {
          this.month--;
          if (this.month < 1) {
            this.month = 12;
            this.year--;
            if (this.year < 0) {
              this.year = 0;
              this.month = 1;
              this.day = 1;
              break; // Stops the while loop to ensure that the date is capped
            }
          }
          this.day = this.findLastDayOfMonth(this.month, this.year);
        }
      }
    }
  }

  /**
   * Returns the last day of a given month factoring in whether it is a leap year or not.
   *
   * @param month month represented as an integer.
   * @param year  year represented as an integer.
   * @return Returns the last day of the given month based if the year is a leap year or not.
   */
  public int findLastDayOfMonth(int month, int year) {
    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
            || month == 10 || month == 12) {
      return 31;
    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
      return 30;
    } else {
      if (year % 4 == 0 && ((year % 100 != 0) || (year % 100 == 0) && year % 400 == 0)) {
        return 29;
      } else {
        return 28;
      }
    }
  }

  @Override
  public String toString() {
    String y = String.format("%04d", this.year);
    String m = String.format("%02d", this.month);
    String d = String.format("%02d", this.day);

    return y + "-" + m + "-" + d;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Date)) {
      return false;
    }
    return this.toString().equals(((Date) obj).toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.day, this.month, this.year);
  }

  /**
   * Gets the day.
   */
  public int getDay() {
    return this.day;
  }

  /**
   * Gets the month.
   */
  public int getMonth() {
    return this.month;
  }

  /**
   * Gets the year.
   */
  public int getYear() {
    return this.year;
  }





  /**
   * Calculates the interval between two dates to be used in bar chart.
   *
   * @param to date to reach.
   * @return the interval between dates to be displayed.
   */
  public int findInterval(Date to) {
    int total = 0;
    int numLines = 0;
    Date tempFrom = new Date(this.getDay(), this.getMonth(), this.getYear());
    while (!tempFrom.toString().equals(to.toString())) {
      total++;
      tempFrom.advance(1);
    }
    if (total <= 30) {
      numLines = total + 1;
    }
    else if (total <= 90) {
      numLines = total / 7;
    }
    else if (total <= 365) {
      numLines = total / 30;
    }
    else {
      numLines = total / 90;
    }
    if (numLines < 5) {
      numLines = 5;
    }
    else if (numLines > 30) {
      numLines = 30;
    }
    return total / (numLines - 1);
  }


  /**
   * Determines if this date is chronologically after a given date.
   *
   * @param date to be compared to this.
   * @return true if this date is after the given date and false otherwise.
   */
  public Boolean isAfter(Date date) {
    return (this.getYear() > date.getYear()
            || (this.getYear() == date.getYear() && this.getMonth() > date.getMonth())
            || (this.getYear() == date.getYear() && this.getMonth() == date.getMonth()
            && this.getDay() >= date.getDay()));
  }
}


