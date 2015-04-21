<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output encoding="ISO-8859-1" method="html" version="4.0"/>

	<xsl:include href="classpath://com/nordicpeak/flowengine/queries/common/xsl/QueryAdminCommon.sv.xsl"/>
	<xsl:include href="CalculationBasisQueryAdminTemplates.xsl"/>
	
	<xsl:variable name="java.queryTypeName">Ber�kningsunderlag</xsl:variable>
	
	<xsl:variable name="i18n.maxLength">till�ten l�ngd p� textinneh�ll</xsl:variable>
	
	<xsl:variable name="i18n.CalculationBasisQueryNotFound">Den beg�rda fr�gan hittades inte!</xsl:variable>
	
	<xsl:variable name="i18n.AllowedContactChannels">Till�tna kontaktv�gar</xsl:variable>
	<xsl:variable name="i18n.AllowedContactChannelsDescription">V�lj vilka kontaktv�gar som anv�ndaren skall f� v�lja p�</xsl:variable>
	<xsl:variable name="i18n.AllowLetter">Brev</xsl:variable>
	<xsl:variable name="i18n.AllowSMS">SMS</xsl:variable>
	<xsl:variable name="i18n.AllowEmail">E-post</xsl:variable>
	<xsl:variable name="i18n.AllowPhone">Telefon</xsl:variable>
	
	<xsl:variable name="i18n.NoContactChannelChoosen">Du m�ste v�lja minst en kontaktv�g!</xsl:variable>
	
</xsl:stylesheet>
