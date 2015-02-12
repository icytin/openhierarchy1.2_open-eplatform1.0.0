<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="utf-8" indent="yes" />
  
  <xsl:template match="document">
    <h1>Example Module!</h1>
    
    <table>
      <tr>
        <th>Id</th>
        <th>Name</th>
      </tr>
      <xsl:apply-templates name="Examples/Example" />
    </table>
  </xsl:template>
  
  <xsl:template match="Examples/Example">
    <tr>
      <td><xsl:value-of select="id" /></td>
      <td><xsl:value-of select="name" /></td>
    </tr>
  </xsl:template>
</xsl:stylesheet>