<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output encoding="ISO-8859-1" method="html" version="4.0"/>

	<xsl:include href="classpath://com/nordicpeak/flowengine/queries/common/xsl/QueryAdminCommon.sv.xsl"/>
	<xsl:include href="CalculationBasisQueryAdminTemplates.xsl"/>
	
	<xsl:variable name="java.queryTypeName">Beräkningsunderlag</xsl:variable>
	
	<xsl:variable name="i18n.maxLength">tillåten längd på textinnehåll</xsl:variable>
	
	<xsl:variable name="i18n.CalculationBasisQueryNotFound">Den begärda frågan hittades inte!</xsl:variable>
	
	<xsl:variable name="i18n.AllowedContactChannels">Tillåtna kontaktvägar</xsl:variable>
	<xsl:variable name="i18n.AllowedContactChannelsDescription">Välj vilka kontaktvägar som användaren skall få välja på</xsl:variable>
	<xsl:variable name="i18n.AllowLetter">Brev</xsl:variable>
	<xsl:variable name="i18n.AllowSMS">SMS</xsl:variable>
	<xsl:variable name="i18n.AllowEmail">E-post</xsl:variable>
	<xsl:variable name="i18n.AllowPhone">Telefon</xsl:variable>
	
	<xsl:variable name="i18n.NoContactChannelChoosen">Du måste välja minst en kontaktväg!</xsl:variable>
	
</xsl:stylesheet>
