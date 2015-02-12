<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1" />

	<xsl:template match="Document">

		<section class="user-menu">
			<h2 class="bordered"><xsl:value-of select="section/name" /></h2>
			<ul class="list-table">
				<xsl:apply-templates select="menuitem" />
			</ul>
		</section>

	</xsl:template>

	<xsl:template match="menuitem">

		<li>
			<xsl:choose>
				<xsl:when test="url">
					
					<a>
						<xsl:attribute name="href">
							<xsl:choose>
								<xsl:when test="urlType='RELATIVE_FROM_CONTEXTPATH'">
									<xsl:value-of select="/Document/contextpath"/>
									<xsl:value-of select="url"/>												
								</xsl:when>
								
								<xsl:when test="urlType='FULL'">
									<xsl:value-of select="url"/>				
								</xsl:when>
							</xsl:choose>
						</xsl:attribute>
						<span data-icon-before="&gt;" class="text" title="{description}"><xsl:value-of select="name" /></span>
					</a>
					
				</xsl:when>
				<xsl:otherwise>
					
					<xsl:attribute name="class">no-url</xsl:attribute>
						
					<xsl:value-of select="name"/>
					
					<xsl:if test="itemType='BLANK'">
						<xsl:text>&#160;</xsl:text> 					
					</xsl:if>
								
				</xsl:otherwise>
			</xsl:choose>
		</li>

	</xsl:template>

</xsl:stylesheet>