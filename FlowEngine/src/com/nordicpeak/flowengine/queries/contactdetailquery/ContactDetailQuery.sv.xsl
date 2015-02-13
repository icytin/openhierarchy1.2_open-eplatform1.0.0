<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output encoding="ISO-8859-1" method="html" version="4.0"/>

	<xsl:include href="classpath://com/nordicpeak/flowengine/queries/common/xsl/QueryCommon.sv.xsl"/>
	<xsl:include href="ContactDetailQueryTemplates.xsl"/>
	
	<xsl:variable name="i18n.Firstname">Förnamn</xsl:variable>
	<xsl:variable name="i18n.Lastname">Efternamn</xsl:variable>
	<xsl:variable name="i18n.FirstnameAndLastname">För- &amp; Efternamn</xsl:variable>
	<xsl:variable name="i18n.Address">Adress</xsl:variable>
	<xsl:variable name="i18n.ZipCode">Postnummer</xsl:variable>
	<xsl:variable name="i18n.PostalAddress">Ort</xsl:variable>
	<xsl:variable name="i18n.MobilePhone">Mobiltelefon</xsl:variable>
	<xsl:variable name="i18n.Email">E-postadress</xsl:variable>
	<xsl:variable name="i18n.Phone">Telefon</xsl:variable>
	
	<xsl:variable name="i18n.ChooseContactChannels">Ange hur du vill att vi ska kontakta dig i det här ärendet</xsl:variable>
	
	<xsl:variable name="i18n.ContactByLetter">Brev</xsl:variable>
	<xsl:variable name="i18n.ContactBySMS">SMS</xsl:variable>
	<xsl:variable name="i18n.ContactByEmail">E-post</xsl:variable>
	<xsl:variable name="i18n.ContactByPhone">Telefon</xsl:variable>
	
	<xsl:variable name="i18n.And">&amp;</xsl:variable>
	
	<xsl:variable name="i18n.UpdateMyUserProfile">Uppdatera kontaktuppgifterna under mina uppgifter</xsl:variable>
	
	<xsl:variable name="i18n.EmailAlreadyTaken">E-postadressen används redan av en annan användare</xsl:variable>
	<xsl:variable name="i18n.UnableToUpdateUser">Det gick inte att uppdatera dina kontaktuppgifter, försök igen</xsl:variable>
		
	<xsl:variable name="i18n.RequiredQuery">Den här frågan är obligatorisk!</xsl:variable>
	<xsl:variable name="i18n.RequiredField">Det här fältet är obligatoriskt!</xsl:variable>
	<xsl:variable name="i18n.TooLongFieldContent.part1">Innehållet i det här fältet är </xsl:variable>
	<xsl:variable name="i18n.TooLongFieldContent.part2"> tecken vilket överskrider maxgränsen på </xsl:variable>
	<xsl:variable name="i18n.TooLongFieldContent.part3"> tecken!</xsl:variable>
	<xsl:variable name="i18n.InvalidFormat">Felaktigt format på fältet</xsl:variable>
	<xsl:variable name="i18n.UnknownValidationError">Ett okänt valideringsfel har uppstått!</xsl:variable>
	<xsl:variable name="i18n.NoContactChannelChoosen">Du måste välja minst ett kontaktsätt!</xsl:variable>
	
</xsl:stylesheet>
