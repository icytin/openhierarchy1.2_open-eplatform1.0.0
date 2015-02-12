package com.projecttemplate.beans;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.standardutils.xml.XMLUtils;

public class Example {
  private int id;
  private String name;
  
  /**
   * Returns example as XML element
   * 
   * @param doc
   * @return
   */
  public Element toXML(Document doc)
  {
    Element exampleElement = doc.createElement("Example");
    
    exampleElement.appendChild(XMLUtils.createElement("id", this.getId(), doc));
    exampleElement.appendChild(XMLUtils.createElement("name", this.getName(), doc));
    
    return exampleElement;
  }
  
  /**
   * Takes list of examples and adds to document
   * 
   * @param examples
   * @param doc
   */
  public static void toXML(ArrayList<Example> examples, Document doc)
  {
    Element examplesElement = doc.createElement("Examples");
    doc.getFirstChild().appendChild(examplesElement);

    if (examples != null)
    {
      for (Example example : examples)
      {
        examplesElement.appendChild(example.toXML(doc));
      }
    }
  }
  
  // Getters & Setters
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}