package com.projecttemplate.modules.example;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.projecttemplate.beans.Example;
import com.projecttemplate.daos.ExampleDAO;
import com.projecttemplate.utils.XML;

import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.webutils.http.URIParser;

public class ExampleModule extends AnnotatedForegroundModule
{
  private ExampleDAO ExampleDAO = new ExampleDAO(this.dataSource);
  
  @Override
  public SimpleForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception
  {
    Document doc = XML.createDocument(req, uriParser, user, this.moduleDescriptor);
    
    // Get examples
    ArrayList<Example> examples = ExampleDAO.get();

    // Add examples to xml doc
    Example.toXML(examples, doc);

    this.log.info("User " + user + " viewing example module");

    return new SimpleForegroundModuleResponse(doc, this.moduleDescriptor.getName(), this.getDefaultBreadcrumb());
  }
}