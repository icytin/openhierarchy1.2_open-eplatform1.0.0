<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output encoding="ISO-8859-1" method="html" version="4.0"/>

	<xsl:include href="classpath://com/nordicpeak/flowengine/queries/common/xsl/QueryAdminCommon.sv.xsl"/>
	<xsl:include href="CalculationBasisQueryAdminTemplates.xsl"/>
	
	<xsl:variable name="java.queryTypeName">Ber�kningsunderlag</xsl:variable>
	<xsl:variable name="java.fieldLayoutNewLine">En kolumn</xsl:variable>
	<xsl:variable name="java.fieldLayoutFloat">Tv� kolumner</xsl:variable>
	
	<xsl:variable name="i18n.BaseInfo">Grundinformation</xsl:variable>
	<xsl:variable name="i18n.Layout">Layout</xsl:variable>
	
	<xsl:variable name="i18n.CalculationBasisQueryNotFound">Den beg�rda fr�gan hittades inte!</xsl:variable>
	<xsl:variable name="i18n.CalculationBasiss">Textf�lt</xsl:variable>
	<xsl:variable name="i18n.AddCalculationBasis">L�gg till textf�lt</xsl:variable>
	<xsl:variable name="i18n.UpdateCalculationBasis">Uppdatera textf�lt</xsl:variable>
	<xsl:variable name="i18n.UpdateBaseInformation">Uppdatera basinformation</xsl:variable>
	<xsl:variable name="i18n.Add">L�gg till</xsl:variable>
	<xsl:variable name="i18n.Done">Klar</xsl:variable>
	<xsl:variable name="i18n.SortCalculationBasiss.Title">Sortera textf�lt</xsl:variable>
	<xsl:variable name="i18n.Label">Namn</xsl:variable>
	<xsl:variable name="i18n.label">namn</xsl:variable>
	<xsl:variable name="i18n.Width">F�ltets bredd</xsl:variable>
	<xsl:variable name="i18n.width">f�ltets bredd</xsl:variable>
	<xsl:variable name="i18n.Required">Obligatorisk</xsl:variable>
	<xsl:variable name="i18n.MaxLength">Till�ten l�ngd p� textinneh�ll</xsl:variable>
	<xsl:variable name="i18n.maxLength">till�ten l�ngd p� textinneh�ll</xsl:variable>
	<xsl:variable name="i18n.FormatValidator">Validator</xsl:variable>
	<xsl:variable name="i18n.InvalidFormatMessage">Valideringsmeddelande</xsl:variable>
	<xsl:variable name="i18n.invalidFormatMessage">valideringsmeddelande</xsl:variable>
	<xsl:variable name="i18n.SortCalculationBasiss">Sortera textf�lt f�r fr�ga</xsl:variable>
	
	<xsl:variable name="i18n.DeleteCalculationBasis.Confirm">Ta bort textf�ltet</xsl:variable>
	<xsl:variable name="i18n.DeleteCalculationBasis">Ta bort textf�ltet</xsl:variable>	
	
</xsl:stylesheet>
