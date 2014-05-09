package no.studios.atelier.data;

public abstract class AtelierEntityBase implements AtelierEntity
{
  @Override
  public String toString()
  {
    return getClass().getName() + "[" + "id=" + getId() + "]";
  }

}
