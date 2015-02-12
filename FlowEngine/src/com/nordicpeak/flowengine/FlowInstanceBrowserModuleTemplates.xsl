<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl"/>

	<xsl:variable name="globalscripts">
		/jquery/jquery.js
	</xsl:variable>

	<xsl:variable name="scripts">
		/js/jquery.blockui.js
		/js/flowengine.js
		/js/flowinstancebrowser.js
	</xsl:variable>

	<xsl:variable name="links">
		/css/flowengine.css
	</xsl:variable>

	<xsl:template match="Document">	
		
		<div id="FlowBrowser" class="contentitem">
			<xsl:apply-templates select="ShowFlowOverview"/>
			<xsl:apply-templates select="ShowFlowTypes"/>
			<xsl:apply-templates select="FlowInstanceManagerForm"/>
			<xsl:apply-templates select="FlowInstanceManagerPreview"/>
			<xsl:apply-templates select="FlowInstanceManagerSubmitted"/>
			<xsl:apply-templates select="SigningForm"/>	
		</div>
		
	</xsl:template>

	<xsl:template match="FlowInstanceManagerForm">
	
		<xsl:if test="FlowInstance/Status/contentType != 'NEW'">
			<xsl:call-template name="showFlowInstanceControlPanel">
				<xsl:with-param name="flowInstance" select="FlowInstance" />
				<xsl:with-param name="view" select="'FLOWINSTANCE'" />
			</xsl:call-template>
		</xsl:if>	
	
		<xsl:apply-imports/>
	
	</xsl:template>
	
	<xsl:template match="FlowInstanceManagerPreview">
	
		<xsl:if test="FlowInstance/Status/contentType != 'NEW'">
			<xsl:call-template name="showFlowInstanceControlPanel">
				<xsl:with-param name="flowInstance" select="FlowInstance" />
				<xsl:with-param name="view" select="'FLOWINSTANCE'" />
			</xsl:call-template>
		</xsl:if>	
	
		<xsl:apply-imports/>
	
	</xsl:template>
				
	<xsl:template match="ShowFlowOverview">
		
		<script type="text/javascript">
			userFavouriteModuleURI = '<xsl:value-of select="/Document/requestinfo/contextpath" /><xsl:value-of select="userFavouriteModuleAlias" />';
		</script>
		
		<xsl:apply-templates select="Flow" mode="overview" />
		
	</xsl:template>
	
	<xsl:template match="Flow" mode="overview">
		
		<xsl:variable name="flowID" select="flowID" />
		<xsl:variable name="flowFamilyID" select="FlowFamily/flowFamilyID" />

		<xsl:variable name="isInternal">
			<xsl:if test="not(externalLink)">true</xsl:if>
		</xsl:variable>
		
		<section>
		
				<div class="section-inside">
  				<div class="heading-wrapper">
  					<figure>
	  					<img alt="" src="{/Document/requestinfo/currentURI}/{/Document/module/alias}/icon/{flowID}" />
	  				</figure>
	  				<div class="heading">
  						<h1 id="flow_{$flowID}" class="xl"><xsl:value-of select="name" />
  							<xsl:if test="../loggedIn and ../userFavouriteModuleAlias">
  								<i id="flowFamily_{FlowFamily/flowFamilyID}" data-icon-after="*" class="xl favourite">
  									<xsl:if test="not(../UserFavourite[FlowFamily/flowFamilyID = $flowFamilyID])">
										<xsl:attribute name="class">xl favourite gray</xsl:attribute>
									</xsl:if>
  								</i>
  							</xsl:if>
  						</h1>
  						<xsl:if test="not(../loggedIn) and requireAuthentication = 'true'">
  							<span data-icon-before="u"><xsl:value-of select="$i18n.AuthenticationRequired" /></span>						
  						</xsl:if>
					</div>
  				</div>
  				<div class="description">
  					<h2><xsl:value-of select="$i18n.Description" /></h2>
  					<xsl:choose>
  						<xsl:when test="longDescription"><xsl:value-of select="longDescription" disable-output-escaping="yes" /></xsl:when>
  						<xsl:otherwise><xsl:value-of select="shortDescription" disable-output-escaping="yes" /></xsl:otherwise>
  					</xsl:choose>
  					
  				</div>
 				</div>
 				
  			<div class="aside-inside">
  				<div class="section yellow">
  					<xsl:if test="Checks/check">
	  					<h2 class="bordered"><xsl:value-of select="$i18n.ChecklistTitle" /></h2>
	  					<ul class="checklist">
	  						<xsl:apply-templates select="Checks/check" mode="overview" />
	  					</ul>
  					</xsl:if>
  					<div class="btn-wrapper">
  						<xsl:choose>
  							<xsl:when test="$isInternal = 'true'">
  								<a class="btn btn-green xl" href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/flow/{flowID}"><xsl:value-of select="$i18n.StartFlow" /></a>
  							</xsl:when>
  							<xsl:otherwise>
  								<a class="btn btn-green xl" href="{externalLink}">
  								
  									<xsl:if test="$isInternal != 'true' and ../openExternalFlowsInNewWindow = 'true'">
		  								<xsl:attribute name="data-icon-after">e</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
		  							</xsl:if>
  									<xsl:value-of select="$i18n.StartFlow" />
  								
  								</a>
  							</xsl:otherwise>
  						</xsl:choose>
  						
  					</div>
  				</div>
  			</div>
  			
  			<xsl:if test="$isInternal = 'true'">
  			
	  			<div class="section-full">
	  				<h2 class="h1"><xsl:value-of select="$i18n.StepDescriptionTitle" />:</h2>
	  				
	  				<xsl:variable name="submitText">
						<xsl:choose>
							<xsl:when test="requireSigning = 'true'"><xsl:value-of select="$i18n.signAndSubmit" /></xsl:when>
							<xsl:otherwise><xsl:value-of select="$i18n.submit" /></xsl:otherwise>
						</xsl:choose>			
					</xsl:variable>
	  				
	  				<ul class="service-navigator clearfix">
	  					
	  					<xsl:apply-templates select="Steps/Step" mode="overview" />
	  					
	  					<xsl:variable name="stepCount" select="count(Steps/Step)" />
	  					
	  					<xsl:choose>
	  						<xsl:when test="usePreview = 'true'">
	  							<li>
			  						<span data-step="{$stepCount + 1}"><xsl:value-of select="$i18n.preview" /></span>
			  					</li>
			  					<li>
			  						<span data-step="{$stepCount + 2}"><xsl:value-of select="$submitText" /></span>
			  					</li>
	  						</xsl:when>
	  						<xsl:otherwise>
	  							<li>
			  						<span data-step="{$stepCount + 1}"><xsl:value-of select="$submitText" /></span>
			  					</li>
	  						</xsl:otherwise>
	  					</xsl:choose>
	  				
	  				</ul>
	  			</div>
	  		
	  		</xsl:if>
	  		
		</section>
	
		<xsl:if test="../showRelatedFlows = 'true'">
	
			<xsl:variable name="flowTypeID" select="FlowType/flowTypeID" />
		
			<xsl:apply-templates select="FlowType">
				<xsl:with-param name="flows" select="../FlowTypeFlows/Flow[FlowType/flowTypeID = $flowTypeID]" />
			</xsl:apply-templates>
	
		</xsl:if>
	
	</xsl:template>
	
	<xsl:template match="check" mode="overview">
	
		<li><xsl:value-of select="." /></li>
	
	</xsl:template>
	
	<xsl:template match="Step" mode="overview">
		
		<li>
			<span data-step="{position()}"><xsl:value-of select="name" /></span>
		</li>
		
	</xsl:template>
	
	<xsl:template match="ShowFlowTypes">
		
		<script type="text/javascript">
			searchFlowURI = '<xsl:value-of select="/Document/requestinfo/currentURI" />/<xsl:value-of select="/Document/module/alias" />/search';
			userFavouriteModuleURI = '<xsl:value-of select="/Document/requestinfo/contextpath" /><xsl:value-of select="userFavouriteModuleAlias" />';
		</script>
		
		<xsl:if test="validationError">
			<xsl:apply-templates select="validationError" />
		</xsl:if>
		
		<section>
	  				
			<div class="search-wrapper">
				<h2 class="h1"><xsl:value-of select="$i18n.SearchTitle" /></h2>
				<div class="search">
					<div class="input-wrapper">
						<input type="text" placeholder="{$i18n.SearchHints}" name="search" class="noborder" id="search" />
						<div class="symbol"><i class="xl">r</i></div>
						<input type="button" value="s" class="btn btn-search" onclick="searchFlow()" />
					</div>
				</div>
			</div>
			<xsl:if test="recommendedTags">
				<div class="tags-wrapper">
					<div class="tags">
						<h2 class="h1"><xsl:value-of select="$i18n.RecommendedSearches" /></h2>
						<ul>
							<xsl:apply-templates select="recommendedTags/Tag" />
						</ul>
					</div>
				</div>
			</xsl:if>
			
		</section>
		
		<section class="search-results">
			<div class="info">
				<span class="message"><i>c</i><xsl:value-of select="$i18n.SearchDone" />.</span>
				<span class="close"><a href="#"><xsl:value-of select="$i18n.close" /> <i>x</i></a></span>
			</div>
			<h2 class="h1 search-results-title"><span class="title" /><xsl:text>&#160;</xsl:text><xsl:value-of select="$i18n.Hits.Part1" /><xsl:text>&#160;</xsl:text><span class="hits" /><xsl:text>&#160;</xsl:text><xsl:value-of select="$i18n.Hits.Part2" /></h2>
			<ul class="previews" />
		</section>
		
		<xsl:apply-templates select="FlowType" />
				
	</xsl:template>
	
	<xsl:template match="Tag">
	
		<li><a href="#">#<xsl:value-of select="." /></a></li>
		
	</xsl:template>
	
	<xsl:template match="FlowType">
		
		<xsl:param name="flowTypeID" select="flowTypeID" />
		<xsl:param name="flows" select="../Flow[FlowType/flowTypeID = $flowTypeID]" />
		
		<section id="flowtype_{flowTypeID}">
			
			<div class="heading-wrapper">
				
				<h2 class="h1"><xsl:value-of select="name" /></h2>
				
				<xsl:if test="$flows">
					
					<div class="select-wrapper">
						<div class="select-box" id="select_{flowTypeID}">
							<span class="text"><xsl:value-of select="$i18n.MostPopular" /></span>
							<span class="arrow">_</span>
							<div class="options">
								<ul>
									<xsl:apply-templates select="Categories/Category">
										<xsl:with-param name="flows" select="$flows" />
									</xsl:apply-templates>
									<xsl:variable name="uncategorizedFlows" select="$flows[not(Category)]" />
									<xsl:if test="$uncategorizedFlows">
										<li id="uncategorized">
											<a href="#">
												<span class="text"><xsl:value-of select="$i18n.Uncategorized" /></span>
												<span class="count"><xsl:value-of select="count($uncategorizedFlows)" /></span>
											</a>
										</li>
									</xsl:if>
									<li id="popular" class="selected">
										<a href="#">
											<span class="text"><xsl:value-of select="$i18n.MostPopular" /></span>
											<span class="count"><i>*</i></span>
										</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				
				</xsl:if>
				
			</div>
			
			<ul class="previews">
			
				<xsl:choose>
					<xsl:when test="not($flows)">
					
						<li><div class="inner"><h2><xsl:value-of select="$i18n.NoFlowsFound"/></h2></div></li>
					
					</xsl:when>
					<xsl:otherwise>
							
						<xsl:apply-templates select="$flows" />
					
					</xsl:otherwise>
				</xsl:choose>
			
			</ul>
			
		</section>
		
	</xsl:template>
	
	<xsl:template match="Category">
		
		<xsl:param name="flows" />
		
		<xsl:variable name="categoryID" select="categoryID" />
		<xsl:variable name="categoryFlows" select="$flows[Category/categoryID = $categoryID]" />
		
		<xsl:if test="$categoryFlows">
			
			<li id="category_{$categoryID}">
				<a href="#">
					<span class="text"><xsl:value-of select="name" /></span>
					<span class="count"><xsl:value-of select="count($flows[Category/categoryID = $categoryID])" /></span>
				</a>
			</li>

		</xsl:if>
		
	</xsl:template>
	
	<xsl:template match="Flow">
		
		<xsl:variable name="flowID" select="flowID" />
		
		<li id="flow_{flowID}">
			
			<xsl:attribute name="class">
				
				<xsl:choose>
					<xsl:when test="Category">
						<xsl:text> category_</xsl:text><xsl:value-of select="Category/categoryID" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:text> uncategorized</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="enabled = 'false'"> disabled</xsl:if>
	 			<xsl:if test="popular = 'true'"> popular</xsl:if>
			</xsl:attribute>
			
			<xsl:variable name="flowFamilyID" select="FlowFamily/flowFamilyID" />
			
			<xsl:choose>
				<xsl:when test="enabled = 'true'">
					<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/overview/{FlowFamily/flowFamilyID}">
						<div class="inner">
							<figure><img src="{/Document/requestinfo/currentURI}/{/Document/module/alias}/icon/{flowID}" /></figure>
							<h2>
								<xsl:value-of select="name" />
								<xsl:choose>
									<xsl:when test="//loggedIn">
										<xsl:if test="//userFavouriteModuleAlias">
											<i id="flowFamily_{FlowFamily/flowFamilyID}" data-icon-after="*" class="favourite">
												<xsl:if test="not(//UserFavourite[FlowFamily/flowFamilyID = $flowFamilyID])">
													<xsl:attribute name="class">favourite gray</xsl:attribute>
												</xsl:if>
											</i>
										</xsl:if>
									</xsl:when>
									<xsl:when test="requireAuthentication = 'true'">
										<i data-icon-before="u" title="{$i18n.AuthenticationRequired}"></i>
									</xsl:when>
								</xsl:choose>
							</h2>
							<xsl:value-of select="shortDescription" disable-output-escaping="yes" />
						</div>
					</a>
				</xsl:when>
				<xsl:otherwise>
					<div class="inner">
						<figure><img src="{/Document/requestinfo/currentURI}/{/Document/module/alias}/icon/{flowID}" width="65" /></figure>
						<h2><xsl:value-of select="name" /></h2>
						<b>(<xsl:value-of select="$i18n.FlowDisabled" />)</b>
						<xsl:value-of select="shortDescription" disable-output-escaping="yes" />
					</div>
				</xsl:otherwise>
			</xsl:choose>
			
		</li>
		
	</xsl:template>		
			
</xsl:stylesheet>