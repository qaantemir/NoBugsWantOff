package requests.skelethon.interfaces;

import models.BaseModel;

public interface CrudEndpointInterface {

  Object get();
  Object get(Long id);
  Object post();
  Object post(BaseModel model);
  Object put(BaseModel model);
  Object delete(Long id);

}
