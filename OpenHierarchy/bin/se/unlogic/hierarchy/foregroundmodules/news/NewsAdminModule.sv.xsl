<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1" />

	<xsl:include href="NewsAdminModuleTemplates.xsl" />
	
	<!-- Naming template.mode.field.type -->

	<xsl:variable name="newsViewModuleXSLPath">NewsViewModule.sv.xsl</xsl:variable>

	<xsl:variable name="title" select="'Rubrik'"/>
	<xsl:variable name="text" select="'Innehåll'"/>
	<xsl:variable name="expandAll" select="'Fäll ut alla'" />
	<xsl:variable name="collapseAll" select="'Stäng alla'" />
	<xsl:variable name="addNewsInSection" select="'Lägg till en nyhet i sektionen'" />
	
	<xsl:variable name="varning.noPageviewModuleInSection" select="'Varning det finns ingen modul för visning av sidor i den här sektionen!'" />
	<xsl:variable name="movePage" select="'Flytta nyheten'"/>
	<xsl:variable name="movePageInstruction" select="'Klicka på den sektion som du vill flytta nyheten till'"/>
	<xsl:variable name="copyPage" select="'Kopiera nyheten'"/>
	<xsl:variable name="copyPageInstruction" select="'Klicka på den sektion som du vill kopiera nyheten till'"/>
	<xsl:variable name="editPage" select="'Redigera nyheten'"/>
	<xsl:variable name="deletePage" select="'Ta bort nyheten'"/>
	<xsl:variable name="editingOfPage" select="'Redigering av nyheten'"/>
	<xsl:variable name="description" select="'Beskrivning'"/>
	<xsl:variable name="alias" select="'Alias'"/>
	<xsl:variable name="name" select="'Titel'"/>
	<xsl:variable name="content" select="'Innehåll'"/>
	<xsl:variable name="additionalSettings" select="'Övriga inställningar'"/>
	<xsl:variable name="activatePage" select="'Aktivera nyhet'"/>
	<xsl:variable name="showInMenu" select="'Visa nyhet i menyn'"/>
	<xsl:variable name="showBreadCrumb" select="'Visa brödsmula'"/>
	<xsl:variable name="access" select="'Åtkomst'"/>
	<xsl:variable name="admins" select="'Administratörer'"/>
	<xsl:variable name="loggedInUsers" select="'Samtliga Inloggade användare'"/>
	<xsl:variable name="nonLoggedInUsers" select="'Ej inloggade användare'"/>
	<xsl:variable name="validationError.requiredField" select="'Du måste fylla i fältet'"/>
	<xsl:variable name="validationError.invalidFormat" select="'Felaktigt format på fältet'"/>
	<xsl:variable name="validationError.unknown" select="'Okänt fel på fältet'"/>
	<xsl:variable name="validationError.unknownErrorOccurred" select="'Ett okänt fel har uppstått'"/>
	<xsl:variable name="addPage" select="'Lägg till nyhet'"/>
	<xsl:variable name="pagePreview" select="'Förhandsvisning av nyhet'"/>
	<xsl:variable name="showPageOutsideAdminView" select="'Visa nyhet utanför administrationsgränssnittet'"/>
	<xsl:variable name="users" select="'Användare'"/>
	<xsl:variable name="groups" select="'Grupper'"/>
	<xsl:variable name="inSection" select="'i sektionen'"/>
	<xsl:variable name="saveChanges" select="'Spara ändringar'"/>
	
</xsl:stylesheet>