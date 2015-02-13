<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:template match="validationError[messageKey='FlowNoLongerAvailable']">
	
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.FlowNoLongerAvailable"></xsl:with-param>
		</xsl:call-template>
		
	</xsl:template>
	
	<xsl:template match="validationError[messageKey='FlowInstanceNoLongerAvailable']">
		
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.FlowInstanceNoLongerAvailable"></xsl:with-param>
		</xsl:call-template>
		
	</xsl:template>	
	
	<xsl:template match="validationError[messageKey='FlowDisabled']">
		
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.FlowDisabled"></xsl:with-param>
		</xsl:call-template>
	
	</xsl:template>	
	
	<xsl:template match="validationError[messageKey='FlowNoLongerPublished']">
		
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.FlowNoLongerPublished"></xsl:with-param>
		</xsl:call-template>
		
	</xsl:template>
	
	<xsl:template match="validationError[messageKey='RequestedFlowNotFound']">
		
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.RequestedFlowNotFound"></xsl:with-param>
		</xsl:call-template>

	</xsl:template>	
	
	<xsl:template match="validationError[messageKey='RequestedFlowInstanceNotFound']">
		
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.RequestedFlowInstanceNotFound"></xsl:with-param>
		</xsl:call-template>
		
	</xsl:template>
	
	<xsl:template match="validationError[messageKey='ErrorGettingFlowInstanceManager']">
		
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.ErrorGettingFlowInstanceManager"></xsl:with-param>
		</xsl:call-template>
	
	</xsl:template>	
	
	<xsl:template match="validationError[messageKey='InvalidLinkRequested']">
			
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.InvalidLinkRequested"></xsl:with-param>
		</xsl:call-template>
			
	</xsl:template>
	
	<xsl:template match="validationError[messageKey='FlowInstanceErrorDataSaved']">
	
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.FlowInstanceErrorDataSaved"></xsl:with-param>
		</xsl:call-template>
	
	</xsl:template>	
	
	<xsl:template match="validationError[messageKey='FlowInstanceErrorDataNotSaved']">
	
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.FlowInstanceErrorDataNotSaved"></xsl:with-param>
		</xsl:call-template>
	
	</xsl:template>
	
	<xsl:template match="validationError[messageKey='FlowInstanceManagerClosed']">
	
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.FlowInstanceManagerClosed"></xsl:with-param>
		</xsl:call-template>
		
	</xsl:template>	
	
	<xsl:template match="validationError[messageKey='UnableToPopulateQueryInstance']">
	
		<xsl:call-template name="printValidationError">
			<xsl:with-param name="message" select="$i18n.UnableToPopulateQueryInstance"></xsl:with-param>
		</xsl:call-template>
		
	</xsl:template>
	
	<xsl:template name="printValidationError">
		
		<xsl:param name="message" />
		
		<section class="modal error">
			<span data-icon-before="!">
				<xsl:value-of select="$message" />
			</span>
			<i class="icon close">x</i>
		</section>
		
	</xsl:template>
	
	<xsl:template name="showFlowInstanceControlPanel">
		
		<xsl:param name="flowInstance" />
		<xsl:param name="view" />
		
		<div class="panel-wrapper">
	 		<div class="inner">
	 			<div class="buttons">
	 				<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/overview/{$flowInstance/Flow/flowID}/{$flowInstance/flowInstanceID}" class="btn btn-dark">
	 					<xsl:if test="$view = 'OVERVIEW'"><xsl:attribute name="class">btn btn-light</xsl:attribute></xsl:if>
	 					<span data-icon-before="^"><xsl:value-of select="$i18n.overview"/></span>
	 				</a>
	 				<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/preview/{$flowInstance/flowInstanceID}" class="btn btn-dark">
	 					<xsl:if test="$view = 'PREVIEW'"><xsl:attribute name="class">btn btn-light</xsl:attribute></xsl:if>
	 					<span data-icon-before="s"><xsl:value-of select="$i18n.showInstance"/></span>
	 				</a>
	 				<xsl:if test="$flowInstance/Status/isUserMutable = 'true'">
		 				<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/flowinstance/{$flowInstance/Flow/flowID}/{$flowInstance/flowInstanceID}" class="btn btn-dark">
		 					<xsl:if test="$view = 'FLOWINSTANCE'"><xsl:attribute name="class">btn btn-light</xsl:attribute></xsl:if>
		 					<span data-icon-before="w"><xsl:value-of select="$i18n.updateInstance"/></span>
		 				</a>
	 				</xsl:if>
	 				<xsl:if test="$flowInstance/Status/isUserDeletable = 'true'">
	 					<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/delete/{$flowInstance/flowInstanceID}" onclick="return confirm('{$i18n.CancelFlowInstanceConfirm}: {$flowInstance/Flow/name}?');" class="btn btn-red">
		 					<span data-icon-before="x"><xsl:value-of select="$i18n.cancelInstance"/></span>
		 				</a>
	 				</xsl:if>
	 			</div>
	 		</div>
	 	</div>
		
	</xsl:template>
	
	<xsl:template name="showManagerFlowInstanceControlPanel">
		
		<xsl:param name="flowInstance" />
		<xsl:param name="view" />
		<xsl:param name="bookmarked" select="Bookmarked" />
		
		<div class="panel-wrapper">
	 		<div class="inner">
	 			<div class="buttons">
	 				<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/overview/{$flowInstance/flowInstanceID}" class="btn btn-dark">
	 					<xsl:if test="$view = 'OVERVIEW'"><xsl:attribute name="class">btn btn-light</xsl:attribute></xsl:if>
	 					<span data-icon-before="^"><xsl:value-of select="$i18n.overview"/></span>
	 				</a>
	 				
	 				<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/preview/{$flowInstance/flowInstanceID}" class="btn btn-dark">
	 					<xsl:if test="$view = 'PREVIEW'"><xsl:attribute name="class">btn btn-light</xsl:attribute></xsl:if>
	 					<span data-icon-before="s"><xsl:value-of select="$i18n.showInstance"/></span>
	 				</a>
	 				
	 				<xsl:if test="$flowInstance/Status/isAdminMutable = 'true'">
		 				<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/flowinstance/{$flowInstance/Flow/flowID}/{$flowInstance/flowInstanceID}" class="btn btn-dark">
		 					<xsl:if test="$view = 'FLOWINSTANCE'"><xsl:attribute name="class">btn btn-light</xsl:attribute></xsl:if>
		 					<span data-icon-before="w"><xsl:value-of select="$i18n.updateInstance"/></span>
		 				</a>
	 				</xsl:if>
	 				
	 				<a href="#" class="btn btn-dark" onclick="return toggleBookmark(event, this, '{/Document/requestinfo/currentURI}/{/Document/module/alias}/bookmark/{$flowInstance/flowInstanceID}');">
	 					<xsl:if test="$bookmarked"><xsl:attribute name="class">btn btn-green</xsl:attribute></xsl:if>
	 					<span data-icon-before="f"><xsl:value-of select="$i18n.bookmarkInstance"/></span>
	 				</a>
	 				
	 				<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/status/{$flowInstance/flowInstanceID}" class="btn btn-dark">
	 					<xsl:if test="$view = 'STATUS'"><xsl:attribute name="class">btn btn-light</xsl:attribute></xsl:if>
	 					<span data-icon-before="!"><xsl:value-of select="$i18n.updateStatus"/></span>
	 				</a>
	 				
	 				<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/managers/{$flowInstance/flowInstanceID}" class="btn btn-dark">
	 					<xsl:if test="$view = 'MANAGER'"><xsl:attribute name="class">btn btn-light</xsl:attribute></xsl:if>
	 					<span data-icon-before="u"><xsl:value-of select="$i18n.updateManagers"/></span>
	 				</a>	 					 				
	 				
	 				<xsl:if test="$flowInstance/Status/isAdminDeletable = 'true'">
	 					<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/delete/{$flowInstance/flowInstanceID}" onclick="return confirm('{$i18n.DeleteFlowInstanceConfirm}: {$flowInstance/Flow/name}?');" class="btn btn-red">
		 					<span data-icon-before="t"><xsl:value-of select="$i18n.deleteInstance"/></span>
		 				</a>
	 				</xsl:if>
	 			</div>
	 		</div>
	 	</div>
		
	</xsl:template>		
		
</xsl:stylesheet>