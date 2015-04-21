<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output encoding="ISO-8859-1" method="html" version="4.0"/>

	<xsl:include href="classpath://com/nordicpeak/flowengine/queries/common/xsl/QueryAdminCommon.sv.xsl"/>
	<xsl:include href="CalculationBasisQueryAdminTemplates.xsl"/>
	
	<xsl:variable name="java.queryTypeName">Beräkningsunderlag</xsl:variable>
	<xsl:variable name="java.fieldLayoutNewLine">En kolumn</xsl:variable>
	<xsl:variable name="java.fieldLayoutFloat">Två kolumner</xsl:variable>
	
	<xsl:variable name="i18n.BaseInfo">Grundinformation</xsl:variable>
	<xsl:variable name="i18n.Layout">Layout</xsl:variable>
	
	<xsl:variable name="i18n.CalculationBasisQueryNotFound">Den begärda frågan hittades inte!</xsl:variable>
	<xsl:variable name="i18n.CalculationBasiss">Textfält</xsl:variable>
	<xsl:variable name="i18n.AddCalculationBasis">Lägg till textfält</xsl:variable>
	<xsl:variable name="i18n.UpdateCalculationBasis">Uppdatera textfält</xsl:variable>
	<xsl:variable name="i18n.UpdateBaseInformation">Uppdatera basinformation</xsl:variable>
	<xsl:variable name="i18n.Add">Lägg till</xsl:variable>
	<xsl:variable name="i18n.Done">Klar</xsl:variable>
	<xsl:variable name="i18n.SortCalculationBasiss.Title">Sortera textfält</xsl:variable>
	<xsl:variable name="i18n.Label">Namn</xsl:variable>
	<xsl:variable name="i18n.label">namn</xsl:variable>
	<xsl:variable name="i18n.Width">Fältets bredd</xsl:variable>
	<xsl:variable name="i18n.width">fältets bredd</xsl:variable>
	<xsl:variable name="i18n.Required">Obligatorisk</xsl:variable>
	<xsl:variable name="i18n.MaxLength">Tillåten längd på textinnehåll</xsl:variable>
	<xsl:variable name="i18n.maxLength">tillåten längd på textinnehåll</xsl:variable>
	<xsl:variable name="i18n.FormatValidator">Validator</xsl:variable>
	<xsl:variable name="i18n.InvalidFormatMessage">Valideringsmeddelande</xsl:variable>
	<xsl:variable name="i18n.invalidFormatMessage">valideringsmeddelande</xsl:variable>
	<xsl:variable name="i18n.SortCalculationBasiss">Sortera textfält för fråga</xsl:variable>
	
	<xsl:variable name="i18n.DeleteCalculationBasis.Confirm">Ta bort textfältet</xsl:variable>
	<xsl:variable name="i18n.DeleteCalculationBasis">Ta bort textfältet</xsl:variable>	
	
</xsl:stylesheet>
