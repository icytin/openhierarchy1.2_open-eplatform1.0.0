package com.projecttemplate.utils;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ModuleDescriptor;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

/**
 * Helperclass for building XML
 *
 * Last edit: 2014-09-18
 *
 * @author Andreas Öman, andreas.oman@cgi.com
 * @see <a href="www.cgi.com">www.cgi.com</a>
 */
public class XML
{
  /**
   * Creates document with default template for use in modules
   *
   * @param request Request
   * @param uriParser URIParser
   * @param user Användare
   * @param moduleDescriptor Modulbeskrivning
   * @return XML-dokument med grundmall
   */
  public static Document createDocument(HttpServletRequest request, URIParser uriParser, User user, ModuleDescriptor moduleDescriptor)
  {
    Document doc = XMLUtils.createDomDocument();

    Element document = doc.createElement("document");
    doc.appendChild(document);
    document.appendChild(RequestUtils.getRequestInfoAsXML(doc, request, uriParser));
    document.appendChild(moduleDescriptor.toXML(doc));
    document.appendChild(user.toXML(doc));

    return doc;
  }
}