package controllers;

import models.NewsModel;
import helpers.ControllerService;

import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

import javax.inject.*;

import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

public class NewsController extends ControllerService {

  @Inject FormFactory formFactory;
  FileInputStream in = null;
  FileOutputStream out = null;
  String fileType = "";
  private List<String> errors = new ArrayList<String>(){};

  public Result news() {
    return ok(views.html.news.render(errors));
  }

  public Result addNews() {
    Form<NewsModel> newsForm = formFactory.form(NewsModel.class).bindFromRequest();
    MultipartFormData<File> body = request().body().asMultipartFormData();
    FilePart<File> picture = body.getFile("image");
    String fileName = picture.getFilename();
    if(newsForm.hasErrors()) {
      return badRequest(views.html.news.render(errorsMessage(newsForm.errors())));
    }
    if(fileName != null) {
      String uuidImage = UUID.randomUUID().toString();
      String[] splitName = fileName.toString().split("\\.");
      for(String str : splitName) {
        fileType = str;
      }
      // String contentType = picture.getContentType();
      File file = picture.getFile();
      if(uploadImages(file, uuidImage).contains("success")) {
        newsForm.get().image = uuidImage + "." + fileType;
        newsForm.get().save();
        return ok();
      }else {
        return badRequest();
      }
    } else {
      // flash("error", "Missing file");
      errors.add("Image is required");
      return badRequest();
    }
  }

  public Result updateNews(Long id) {
    NewsModel newsForm = NewsModel.findNewsById(id);
    return ok();
  }

  private String uploadImages(File file, String uuidImage) {
    try {
      in = new FileInputStream(file);
      out = new FileOutputStream("public/images/uploads/" + uuidImage + "." + fileType);
      int c;
      while ((c = in.read()) != -1) {
        out.write(c);
      }
    }catch(Exception e) {
      System.out.println("Cannot upload image..");
    }finally {
      if (in != null) {
        try {
          in.close();
        }catch(Exception e) {
          return "Cannot close input stream..";
        }
      }
      if (out != null) {
        try {
          out.close();
        }catch(Exception e) {
          return "Cannot close output stream..";
        }
      }
    }
    return "Upload image success";
  }

}
