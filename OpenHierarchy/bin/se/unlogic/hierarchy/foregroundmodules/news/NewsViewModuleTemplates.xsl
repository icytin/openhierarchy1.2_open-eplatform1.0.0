<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl"/>

	<xsl:template match="Document">		
		<xsl:choose>
			<xsl:when test="news/news">
			
				<xsl:if test="displayHeader='true'">
					<div class="contentitem">
						<h1><xsl:value-of select="$News"/></h1>
					</div>
				</xsl:if>			

				<xsl:apply-templates select="news/news"/>
			</xsl:when>
			<xsl:otherwise>
				<div class="contentitem">
					<h1><xsl:value-of select="$NoNews"/></h1>
					<p><xsl:value-of select="$NoNewsDescr"/></p>
				</div>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="news">		
		
		<div class="contentitem">

			<xsl:apply-templates select="../../AdminModule">
				<xsl:with-param name="newsItem" select="."/>
			</xsl:apply-templates>
			
			<a name="news{newsID}" />
			<h2><xsl:value-of select="title"/></h2>
			
			<xsl:value-of select="text" disable-output-escaping="yes"/>
			
			<xsl:if test="../../displayMetadata='true'">
				<p class="tiny"><xsl:value-of select="$Createdby"/>: <xsl:call-template name="createdBy"><xsl:with-param name="user" select="poster/user"/><xsl:with-param name="userNotFoundPhrase" select="$UserNotFoundPhrase"></xsl:with-param></xsl:call-template></p>
				<p class="tiny"><xsl:value-of select="$Published"/>: <xsl:value-of select="added"/></p>
				
				<xsl:if test="updated">
					<p class="tiny"><xsl:value-of select="$Updated"/>: <xsl:value-of select="updated"/></p>
				</xsl:if>			
			</xsl:if>
			
			<xsl:if test="not(last())">
				<hr />
			</xsl:if>
		</div>
			
	</xsl:template>
	
	<xsl:template match="AdminModule">
		
		<xsl:param name="newsItem"/>
					
		<div style="float: right">
			
			<a href="{/Document/requestinfo/contextpath}{section/fullAlias}/{module/alias}/add/{$newsItem/sectionID}" title="{$newsViewModule.addlinktitle}">
				<img src="{/Document/requestinfo/contextpath}/static/f/{module/sectionID}/{module/moduleID}/pics/page_add.png"/>
			</a>			
			
			<a href="{/Document/requestinfo/contextpath}{section/fullAlias}/{module/alias}/move/{$newsItem/newsID}" title="{$newsViewModule.movelinktitle} {$newsItem/name}">
				<img src="{/Document/requestinfo/contextpath}/static/f/{module/sectionID}/{module/moduleID}/pics/page_move.png"/>
			</a>
			
			<a href="{/Document/requestinfo/contextpath}{section/fullAlias}/{module/alias}/copy/{$newsItem/newsID}" title="{$newsViewModule.copylinktitle} {$newsItem/name}">
				<img src="{/Document/requestinfo/contextpath}/static/f/{module/sectionID}/{module/moduleID}/pics/page_copy.png"/>
			</a>			
			
			<a href="{/Document/requestinfo/contextpath}{section/fullAlias}/{module/alias}/update/{$newsItem/newsID}" title="{$newsViewModule.updatelinktitle} {$newsItem/name}">
				<img src="{/Document/requestinfo/contextpath}/static/f/{module/sectionID}/{module/moduleID}/pics/page_edit.png"/>
			</a>
			
			<a href="{/Document/requestinfo/contextpath}{section/fullAlias}/{module/alias}/delete/{$newsItem/newsID}" onclick="return confirm('{$newsViewModule.deletenewspopup}'"  title="{$newsViewModule.deletenewstitle} {$newsItem/name}">
				<img src="{/Document/requestinfo/contextpath}/static/f/{module/sectionID}/{module/moduleID}/pics/page_delete.png"/>
			</a>
		</div>
	</xsl:template>
	
</xsl:stylesheet>