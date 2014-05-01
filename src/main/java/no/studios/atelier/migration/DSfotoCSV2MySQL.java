package no.studios.atelier.migration;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import no.studios.atelier.util.*;

/**
 * Converts from DSFOTO CSV to MySQL SQL.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.4 $
 */
public final class DSfotoCSV2MySQL
{

  public static String cleanString(final String pS)
  {
    String s = pS.replace("\"", "");
    s = s.replace("\'", "");
    return s.replace("\\", "/").trim();
  }

  private final static String FIELD_SEPARATOR = "|";

  public static void main(String args[]) throws Exception
  {
    File file = new File(args[0]);
    // just hard coding it now
    // File file = new
    // File("/home/torstein/projects/atelier/dsfoto-csv/kunderegister.csv.emacs");
    String s = FileUtils.readFileToString(file, StringUtil.ENC_ISO_8859_1);

    String[] lines = s.split("\n");

    StringBuffer sql = new StringBuffer();

    for (String line : lines)
    {
      String[] elements = line.split("\\|");
      if (elements.length > 34)
      {
        String name = elements[1];
        String firstName = "\"\"";
        String lastName = cleanString(name);

        if (name.indexOf(",") != -1)
        {
          firstName = "\"" + cleanString(name.substring(name.indexOf(",") + 1))
              + "\"";
          lastName = "\"" + cleanString(name.substring(0, name.indexOf(",")))
              + "\"";
        }

        else if (name.indexOf(" ") != -1)
        {
          firstName = "\"" + cleanString(name.substring(name.indexOf(" ") + 1))
              + "\"";
          lastName = "\"" + cleanString(name.substring(0, name.indexOf(" ")))
              + "\"";
        }
        else
        {
          lastName = "\"" + lastName + "\"";
        }

        String oldArchiveId = "\"" + cleanString(elements[3]) + "\"";
        String oldCustomerId = "\"" + cleanString(elements[5]) + "\"";
        String address = "\"" + cleanString(elements[9]) + "\"";
        // String postCode = "\"" + cleanString(elements[26]) + "\"";
        String homePhone = "\"" + cleanString(elements[34]) + "\"";

        Date creationDate = DateUtil.parseSimpleNorwegian(elements[12]);
        if (creationDate == null)
        {
          creationDate = new Date();
        }

        sql.append("insert into customer ");
        sql.append("(first_name, last_name, address, home_phone, ");
        sql.append("old_archive_id, old_customer_id, creation_date) ");
        sql.append("values (");
        sql.append(firstName + ", " + lastName + ", " + address + ", "
            + homePhone);
        sql.append(", " + oldArchiveId + ", " + oldCustomerId + ", ");
        sql.append("'" + DateUtil.formatMySQL(creationDate) + "'");
        sql.append(");\n");
      }
    }

    File result = new File("/tmp/customer.sql");
    FileUtils.writeStringToFile(result, sql.toString(),
        StringUtil.ENC_ISO_8859_1);
  }
}
