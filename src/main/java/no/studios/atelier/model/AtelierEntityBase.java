package no.studios.atelier.model;

public abstract class AtelierEntityBase implements AtelierEntity
{
  @Override
  public String toString()
  {
    return getClass().getName() + "[" + " id=" + getId() + " creationDate="
        + getCreationDate() + "]";
  }

}
