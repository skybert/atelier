package no.studios.atelier.util;

import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;
import java.util.Date;
import java.text.NumberFormat;
import java.text.DecimalFormat;

/**
 * Common String utility methods.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.7 $
 */
public final class StringUtil
{
  protected static final Logger mLogger = Logger.getLogger(StringUtil.class);
  public static final String ENC_UTF8 = "UTF-8";
  public static final String ENC_ISO_8859_1 = "ISO-8859-1";

  /**
   * No instances please
   */
  private StringUtil()
  {
  }

  /**
   * Converts a string to UTF-8 byte array.
   * 
   * @param pString
   *          a string.
   * @return a UTF-8 byte array. If pString is null, an empty byte array is
   *         returned.
   */
  public static byte[] toUTF8ByteArray(final String pString)
  {
    if (pString == null)
    {
      return new byte[0];
    }

    try
    {
      return pString.getBytes(ENC_UTF8);
    }
    catch (UnsupportedEncodingException uee)
    {
      mLogger.error(ENC_UTF8 + " is not supported", uee);
      return pString.getBytes();
    }
  }

  /**
   * Converts a byte array to UTF-8 string.
   * 
   * @param pBytes
   *          a byte array.
   * @return a UTF-8 string.
   */
  public static String toUTF8String(final byte[] pBytes)
  {
    try
    {
      return new String(pBytes, ENC_UTF8);
    }
    catch (UnsupportedEncodingException uee)
    {
      mLogger.error(ENC_UTF8 + " is not supported", uee);
      return new String(pBytes);
    }
  }

  public static boolean isEmpty(final String pS)
  {
    return pS == null || pS.trim().length() == 0;
  }

  private static String[][] mCharacterHTMLEntityMap = new String[][] {
      { "ö", "&ouml;" }, { "Ö", "&Ouml;" }, { "ü", "&uuml;" },
      { "Ü", "&Uuml;" }, { "ß", "&szlig;" }, { "ø", "&oslash;" },
      { "å", "&aring;" }, { "Ø", "&Oslash;" }, { "Å", "&Aring;" },
      { "æ", "&aelig;" }, { "Æ", "&AElig;" } };

  /**
   * Useful for print :-) Returns empty string if pS is nul.
   * 
   * @param pS
   *          a <code>String</code> value
   * @return a <code>String</code> value
   */
  public static String print(final String pS)
  {
    if (pS != null)
    {
      String result = pS;
      for (String[] mapping : mCharacterHTMLEntityMap)
      {
        result = result.replace(mapping[0], mapping[1]);
      }

      return result;
    }

    return "";
  }

  /**
   * Useful for print :-) Returns empty string if pS is null.
   * 
   * @param pDate
   *          a <code>Date</code> value
   * @return a <code>String</code> value
   */
  public static String print(final Date pDate)
  {
    if (pDate != null)
    {
      return DateUtil.formatSimpleNorwegian(pDate);
    }

    return "";
  }

  private final static NumberFormat sAmountFormat = new DecimalFormat("0.00");
  private final static NumberFormat sAmountFormatWithoutDecimals = new DecimalFormat(
      "0");

  /**
   * Returns a string for printing of the double value on the format
   * <code>0.00</code>.
   * 
   * @param pAmount
   *          a <code>long</code> value
   * @return a <code>String</code> value
   */
  public static String print(final double pAmount)
  {
    return sAmountFormat.format(pAmount);
  }

  public static String print(
    final double pAmount,
    final boolean pWithoutDecimals)
  {
    if (pWithoutDecimals)
    {
      return sAmountFormatWithoutDecimals.format(pAmount);
    }
    else
    {
      return print(pAmount);
    }
  }

  public static String print(final int pInt)
  {
    return Integer.toString(pInt);
  }

}