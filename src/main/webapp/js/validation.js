/*
  Validates both date and currency fields.
  $Id: validation.js,v 1.2 2008/12/14 16:42:50 torstein Exp $
 */
function validate(field)
{
  if (field.value == "")
  {
    return;
  }

  var isDateField = 0;
  dateFields = new Array("birth-date", "date", "from-date", "to-date", "creation-date");

  for (var i = 0; i < dateFields.length; i++)
  {
    if (dateFields[i] == field.name)
    {
      isDateField = 1;
    }
  }

  if (isDateField == 1)
  {
    regExp = new RegExp("^\\d?\\d\\.\\d?\\d\\.\\d?\\d\\d\\d$");
    format_help = "f.eks. 05.09.2008";
  }
  else
  {
    regExp = new RegExp("^\\d*\\.\\d\\d$");
    format_help = "f.eks. 2500.50";
  }

  feedback = document.getElementById("feedback");

  if (!regExp.test(field.value))
  {
    feedback.textContent = "Verdien (" + field.value + ")"
      + " i feltet '" + field.name + "'"
      + " har ikke riktig format" + " (" + format_help + ")";
  }
  else
  {
    feedback.textContent = "";
  }

}
