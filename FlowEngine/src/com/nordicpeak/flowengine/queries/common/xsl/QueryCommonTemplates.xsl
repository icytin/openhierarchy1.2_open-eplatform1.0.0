<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="helpText">
		
		<div class="help">
			<a data-icon-after="?" href="#" class="open-help"><xsl:value-of select="$i18n.Help" /></a>
			<div class="help-box" id="" style="display: none;">
				<span> 
					<a data-icon-after="x" href="#" class="close"></a> 
					<xsl:value-of select="." disable-output-escaping="yes" />
				</span> 
				<div class="marker"></div>
			</div>
		</div>
		
	</xsl:template>
	
	<xsl:template name="createUpdateButton">
		
		<xsl:param name="queryID" />
		
		<xsl:if test="updateURL">
			<div class="edit">
				<a data-icon-before="w" href="{updateURL}#query_{$queryID}" class="btn btn-light update_query"><xsl:value-of select="$i18n.UpdateQuery" /></a>
			</div>
		</xsl:if>
		
	</xsl:template>
					
	<xsl:template match="validationError[messageKey = 'FreeTextAlternativeValueToLong']">
		
		<span>
			<strong data-icon-before="!">
				<xsl:value-of select="$i18n.FreeTextAlternativeToLong"/>
			</strong>
		</span>
		
	</xsl:template>
	
	<xsl:template match="validationError[messageKey = 'FreeTextAlternativeValueRequired']">
	
		<span>
			<strong data-icon-before="!">
				<xsl:value-of select="$i18n.FreeTextAlternativeValueRequired"/>
			</strong>
		</span>
	
	</xsl:template>
					
</xsl:stylesheet>