package controllers;

import javax.inject.Inject;

import play.mvc.*;
import play.db.*;

class JavaApplicationDatabase extends Controller {

  private Database db;

  @Inject
  public JavaApplicationDatabase(@NamedDatabase("testing") Database db) {
    this.db = db;
  }
}
