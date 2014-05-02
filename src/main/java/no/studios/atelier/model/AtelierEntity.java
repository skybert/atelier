package no.studios.atelier.model;

import java.util.Date;

public interface AtelierEntity
{
  public Integer getId();

  public void setId(Integer pId);

  public Date getCreationDate();

  public void setCreationDate(final Date date);
}
