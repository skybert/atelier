package no.studios.atelier.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import org.apache.log4j.Logger;
import java.util.Calendar;

/**
 * Common date utility methods.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.2 $
 */
public final class DateUtil
{
  protected final static Logger mLogger = Logger.getLogger(DateUtil.class);

  /**
   * 2007-04-10 08:00:00.0
   */
  private static final DateFormat mMySQLFormat = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss.0");

  /**
   * 26.10.1989
   */
  private static final DateFormat mSimpleNorwegian = new SimpleDateFormat(
      "dd.MM.yyyy");

  /**
   * No instances please
   */
  private DateUtil()
  {
  }

  public static Date parseSimpleNorwegian(final String pDate)
  {
    Date date = null;
    try
    {
      date = mSimpleNorwegian.parse(pDate);
    }
    catch (ParseException pe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(pe);
      }
    }

    return date;
  }

  public static String formatSimpleNorwegian(final Date pDate)
  {
    return mSimpleNorwegian.format(pDate);
  }

  public static String formatMySQL(final Date pDate)
  {
    return mMySQLFormat.format(pDate);
  }

  public static Date getDateInDays(final int pDays)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.roll(Calendar.DAY_OF_YEAR, pDays);
    return calendar.getTime();
  }

  public static void main(String args[])
  {
    System.out.println(formatMySQL(new Date()));
    System.out.println("day in 365 days=" + getDateInDays(365));

  }

}