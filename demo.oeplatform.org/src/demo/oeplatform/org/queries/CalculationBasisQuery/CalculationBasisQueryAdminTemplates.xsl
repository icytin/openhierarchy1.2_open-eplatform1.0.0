<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl"/>

	<xsl:variable name="globalscripts">
		/jquery/jquery.js
		/ckeditor/ckeditor.js
		/ckeditor/adapters/jquery.js
		/ckeditor/init.js
	</xsl:variable>	

	<xsl:template match="Document">	
		
		<div id="CalculationBasisQueryProvider" class="contentitem">
		
			<xsl:apply-templates select="validationError"/>
			<xsl:apply-templates select="UpdateCalculationBasisQuery"/>
		
		</div>
		
	</xsl:template>
		
	<xsl:template match="UpdateCalculationBasisQuery">
	
		<h1><xsl:value-of select="$i18n.UpdateQuery" /><xsl:text>:&#160;</xsl:text><xsl:value-of select="CalculationBasisQuery/QueryDescriptor/name" /></h1>
		
		<xsl:apply-templates select="validationException/validationError" />
		
		<form id="updateCalculationBasisQueryForm" name="queryAdminForm" method="post" action="{/Document/requestinfo/uri}">
		
			<xsl:call-template name="createCommonFieldsForm">
				<xsl:with-param name="element" select="CalculationBasisQuery" />
			</xsl:call-template>
			
			<div class="floatleft clearboth">
			
				<label class="floatleft clearboth"><xsl:value-of select="$i18n.AllowedContactChannels" /></label>
				
				<p class="floatleft clearboth"><xsl:value-of select="$i18n.AllowedContactChannelsDescription" />.</p>
			
			</div>
			
			<div class="floatleft full marginbottom">
				<xsl:call-template name="createCheckbox">
					<xsl:with-param name="id" select="'allowLetter'" />
					<xsl:with-param name="name" select="'allowLetter'" />
					<xsl:with-param name="value" select="'true'" />
					<xsl:with-param name="element" select="CalculationBasisQuery" />
				</xsl:call-template>
				<label for="allowLetter"><xsl:value-of select="$i18n.AllowLetter" /></label>
			</div>
			<div class="floatleft full marginbottom">
				<xsl:call-template name="createCheckbox">
					<xsl:with-param name="id" select="'allowSMS'" />
					<xsl:with-param name="name" select="'allowSMS'" />
					<xsl:with-param name="value" select="'true'" />
					<xsl:with-param name="element" select="CalculationBasisQuery" />
				</xsl:call-template>
				<label for="allowSMS"><xsl:value-of select="$i18n.AllowSMS" /></label>
			</div>
			<div class="floatleft full marginbottom">
				<xsl:call-template name="createCheckbox">
					<xsl:with-param name="id" select="'allowEmail'" />
					<xsl:with-param name="name" select="'allowEmail'" />
					<xsl:with-param name="value" select="'true'" />
					<xsl:with-param name="element" select="CalculationBasisQuery" />
				</xsl:call-template>
				<label for="allowEmail"><xsl:value-of select="$i18n.AllowEmail" /></label>
			</div>
			<div class="floatleft full marginbottom">
				<xsl:call-template name="createCheckbox">
					<xsl:with-param name="id" select="'allowPhone'" />
					<xsl:with-param name="name" select="'allowPhone'" />
					<xsl:with-param name="value" select="'true'" />
					<xsl:with-param name="element" select="CalculationBasisQuery" />
				</xsl:call-template>
				<label for="allowPhone"><xsl:value-of select="$i18n.AllowPhone" /></label>
			</div>
			
			<div class="floatright margintop clearboth">
				<input type="submit" value="{$i18n.SaveChanges}" />
			</div>
		
		</form>
		
	</xsl:template>

	<xsl:template match="validationError[messageKey = 'UpdateFailedCalculationBasisQueryNotFound']">
		
		<p class="error">
			<xsl:value-of select="$i18n.CalculationBasisQueryNotFound" />
		</p>
		
	</xsl:template>

	<xsl:template match="validationError[messageKey = 'NoContactChannelChoosen']">
	
		<p class="error">
			<xsl:value-of select="$i18n.NoContactChannelChoosen" />
		</p>
	
	</xsl:template>

	<xsl:template match="fieldName">

		<xsl:variable name="fieldName" select="." />
	
		<xsl:choose>
			<xsl:when test="$fieldName = 'maxLength'">
				<xsl:value-of select="$i18n.maxLength" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$fieldName" />
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>

</xsl:stylesheet>