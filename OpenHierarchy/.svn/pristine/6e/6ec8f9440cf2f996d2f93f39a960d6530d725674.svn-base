<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl" />
	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/CKEditor.xsl" />

	<xsl:variable name="globalscripts">
		/jquery/jquery.js
		/ckeditor/ckeditor.js
		/ckeditor/adapters/jquery.js
		/ckeditor/init.js
	</xsl:variable>

	<xsl:variable name="scripts">
		/dtree/dtree.js
		/js/askBeforeRedirect.js
	</xsl:variable>		

	<xsl:variable name="links">
		/dtree/dtree.css
	</xsl:variable>

	<xsl:template match="document">
		<div class="contentitem">
			<xsl:apply-templates select="addNewsForm"/>
			<xsl:apply-templates select="updateNewsForm"/>
			<xsl:apply-templates select="moveNewsForm"/>
			<xsl:apply-templates select="copyNewsForm"/>
			<xsl:apply-templates select="preview"/>		
			<xsl:apply-templates select="sections"/>
		</div>
	</xsl:template>
	
	<xsl:template match="sections">	
		
		<h1><xsl:value-of select="/document/module/name"/></h1>		
		
		<div class="dtree">
			<p><a href="javascript: pagetree{/document/module/moduleID}.openAll();"><xsl:value-of select="$expandAll" /></a> | <a href="javascript: pagetree{/document/module/moduleID}.closeAll();"><xsl:value-of select="$collapseAll" /></a></p>

			<script type="text/javascript">
				pagetree<xsl:value-of select="/document/module/moduleID"/> = new dTree('pagetree<xsl:value-of select="/document/module/moduleID"/>','<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/');
				pagetree<xsl:value-of select="/document/module/moduleID"/>.icon.root = '<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/img/globe.gif';
				<xsl:apply-templates select="section" mode="pagetree"/>
				
				document.write(pagetree<xsl:value-of select="/document/module/moduleID"/>);
			</script>
		</div>				
	</xsl:template>
	
	<xsl:template match="section" mode="pagetree">
	
		<xsl:variable name="parentSectionID">
			<xsl:choose>
				<xsl:when test="parentSectionID">
					<xsl:text>section</xsl:text>
					<xsl:value-of select="parentSectionID"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>-1</xsl:text>
				</xsl:otherwise>
			</xsl:choose>			
		</xsl:variable>
	
		<xsl:variable name="name">
            <xsl:call-template name="common_js_escape">
               <xsl:with-param name="text" select="name" />
            </xsl:call-template>			
		</xsl:variable>
		
		<xsl:variable name="description">
            <xsl:call-template name="common_js_escape">
               <xsl:with-param name="text" select="description" />
            </xsl:call-template>			
		</xsl:variable>			

		pagetree<xsl:value-of select="/document/module/moduleID"/>.add('section<xsl:value-of select="sectionID"/>','<xsl:value-of select="$parentSectionID"/>','<xsl:value-of select="$name"/>','','<xsl:value-of select="$description"/>','','<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/img/folder.gif','<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/img/folderopen.gif','','<img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/dtree/img/empty.gif"/><a href="{/document/requestinfo/currentURI}/{/document/module/alias}/add/{sectionID}" title="{$addNewsInSection}: {$name}"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_add.png"/></a><xsl:if test="noPageModuleWarning"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/warning.png" alt="{$varning.noPageviewModuleInSection}" title="{$varning.noPageviewModuleInSection}"/></xsl:if>');

		<xsl:apply-templates select="subsections/section" mode="pagetree"/>
		
		<xsl:apply-templates select="news/news" mode="dtree"/>
	</xsl:template>
	
	<xsl:template match="news" mode="dtree">	
	
		<xsl:variable name="title">
            <xsl:call-template name="common_js_escape">
               <xsl:with-param name="text" select="title" />
            </xsl:call-template>			
		</xsl:variable>
		
		<xsl:variable name="nameWithoutQuotes">
			<xsl:call-template name="replace-substring">
				<xsl:with-param name="from" select="'&quot;'"/>
				<xsl:with-param name="to" select="''"/>
				<xsl:with-param name="value">
					<xsl:call-template name="replace-substring">
						<xsl:with-param name="from">&apos;</xsl:with-param>
						<xsl:with-param name="to" select="''"/>
						<xsl:with-param name="value" select="title" />
					</xsl:call-template>
				</xsl:with-param>
			</xsl:call-template>			
		</xsl:variable>
		
		<xsl:variable name="description">
            <xsl:call-template name="common_js_escape">
               <xsl:with-param name="text" select="title" />
            </xsl:call-template>			
		</xsl:variable>		
	
		<xsl:choose>
			<xsl:when test="enabled='true'">
				pagetree<xsl:value-of select="/document/module/moduleID"/>.add('news<xsl:value-of select="newsID"/>','section<xsl:value-of select="sectionID"/>','<xsl:value-of select="$title"/>','<xsl:value-of select="/document/requestinfo/currentURI"/>/<xsl:value-of select="/document/module/alias"/>/update/<xsl:value-of select="newsID"/>','<xsl:value-of select="$description"/>','','<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/><xsl:text>/pics/page.png</xsl:text>','','','<img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/dtree/img/empty.gif"/><a href="{/document/requestinfo/currentURI}/{/document/module/alias}/move/{newsID}" title="{$movePage}: {$title}"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_move.png"/></a><a href="{/document/requestinfo/currentURI}/{/document/module/alias}/copy/{newsID}" title="{$copyPage}: {$title}"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_copy.png"/></a><a href="{/document/requestinfo/currentURI}/{/document/module/alias}/update/{newsID}" title="{$editPage}: {$title}"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_edit.png"/></a><a href="javascript:askBeforeRedirect(\'{$deletePage}: {$nameWithoutQuotes}?\',\'{/document/requestinfo/currentURI}/{/document/module/alias}/delete/{newsID}\');" title="{$deletePage}: {$name}"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_delete.png"/></a>');
			</xsl:when>
			<xsl:otherwise>
				pagetree<xsl:value-of select="/document/module/moduleID"/>.add('news<xsl:value-of select="newsID"/>','section<xsl:value-of select="sectionID"/>','<xsl:value-of select="$title"/>','<xsl:value-of select="/document/requestinfo/currentURI"/>/<xsl:value-of select="/document/module/alias"/>/update/<xsl:value-of select="newsID"/>','<xsl:value-of select="$description"/>','','<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/><xsl:text>/pics/page_disabled.png</xsl:text>','','','<img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/dtree/img/empty.gif"/><a href="{/document/requestinfo/currentURI}/{/document/module/alias}/move/{newsID}" title="{$movePage}: {$title}"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_move.png"/></a><a href="{/document/requestinfo/currentURI}/{/document/module/alias}/copy/{newsID}" title="{$copyPage}: {$title}"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_copy.png"/></a><a href="{/document/requestinfo/currentURI}/{/document/module/alias}/update/{newsID}" title="{$editPage}: {$title}"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_edit.png"/></a><a href="javascript:askBeforeRedirect(\'{$deletePage}: {$nameWithoutQuotes}?\',\'{/document/requestinfo/currentURI}/{/document/module/alias}/delete/{newsID}\');" title="{$deletePage}: {$name}"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_delete.png"/></a>');	
			</xsl:otherwise>
		</xsl:choose>	
	</xsl:template>
	
	<xsl:template match="updateNewsForm">
		
		<h1><xsl:value-of select="$editingOfPage"/> "<xsl:value-of select="news/title"/>" <xsl:value-of select="$inSection"/> "<xsl:value-of select="section/name"/>"</h1>
		
		<xsl:apply-templates select="validationException/validationError"/>
		
		<form method="post" action="{/document/requestinfo/url}" accept-charset="ISO-8859-1">
			
			<table width="100%">
				<tr>
					<td><xsl:value-of select="$title"/>:</td>
					<td>
						<input type="text" name="title" size="40">
							<xsl:attribute name="value">
								<xsl:choose>
									<xsl:when test="requestparameters/parameter[name='title']">
										<xsl:value-of select="requestparameters/parameter[name='title']/value"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="news/title"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:attribute>
						</input>					
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<h2><xsl:value-of select="$text"/></h2>							
						
						<textarea class="fckeditor" name="text" rows="20">
							<xsl:choose>
								<xsl:when test="requestparameters/parameter[name='text']">
									<xsl:call-template name="removeLineBreak">
										<xsl:with-param name="string">
											<xsl:value-of select="requestparameters/parameter[name='text']/value"/>
										</xsl:with-param>
									</xsl:call-template>
								</xsl:when>
								<xsl:otherwise>
									<xsl:call-template name="removeLineBreak">
										<xsl:with-param name="string">
											<xsl:value-of select="news/text"/>
										</xsl:with-param>
									</xsl:call-template>
								</xsl:otherwise>
							</xsl:choose>
						</textarea>						
						
						<xsl:call-template name="initFCKEditor" /> 					
					</td>
				</tr>
			</table>
			
			<h2><xsl:value-of select="$additionalSettings"/></h2>
			
			<table>
				<tr>
					<td>
						<input type="checkbox" name="enabled">
							
							<xsl:choose>
								<xsl:when test="requestparameters">
									<xsl:if test="requestparameters/parameter[name='enabled']">
										<xsl:attribute name="checked"/>
									</xsl:if>
								</xsl:when>
								<xsl:when test="news/enabled='true'">
									<xsl:attribute name="checked"/>
								</xsl:when>
							</xsl:choose>						
						</input>
											
						<xsl:value-of select="$activatePage"/>
					</td>
				</tr>			
			
			</table>
			
			<div align="right">
				<input type="submit" value="{$saveChanges}"/>			
			</div>										
		</form>		
	</xsl:template>		
	
	<xsl:template name="common_js_escape">
		<!-- required -->
		<xsl:param name="text"/>
		<xsl:variable name="tmp">		
			<xsl:call-template name="replace-substring">
				<xsl:with-param name="from" select="'&quot;'"/>
				<xsl:with-param name="to">\"</xsl:with-param>
				<xsl:with-param name="value">
					<xsl:call-template name="replace-substring">
						<xsl:with-param name="from">&apos;</xsl:with-param>
						<xsl:with-param name="to">\'</xsl:with-param>
						<xsl:with-param name="value" select="$text" />
					</xsl:call-template>
				</xsl:with-param>
			</xsl:call-template>	
		</xsl:variable>
		<xsl:value-of select="$tmp" />
	</xsl:template>
	
	<xsl:template name="replace-substring">
	      <xsl:param name="value" />
	      <xsl:param name="from" />
	      <xsl:param name="to" />
	      <xsl:choose>
	         <xsl:when test="contains($value,$from)">
	            <xsl:value-of select="substring-before($value,$from)" />
	            <xsl:value-of select="$to" />
	            <xsl:call-template name="replace-substring">
	               <xsl:with-param name="value" select="substring-after($value,$from)" />
	               <xsl:with-param name="from" select="$from" />
	               <xsl:with-param name="to" select="$to" />
	            </xsl:call-template>
	         </xsl:when>
	         <xsl:otherwise>
	            <xsl:value-of select="$value" />
	         </xsl:otherwise>
	      </xsl:choose>
	</xsl:template>
	
	<xsl:template match="validationError">
		<xsl:if test="fieldName and validationErrorType and not(messageKey)">
			<p style="color: red;">
				<xsl:choose>
					<xsl:when test="validationErrorType='RequiredField'">
						<xsl:text><xsl:value-of select="$validationError.requiredField"/></xsl:text>
					</xsl:when>
					<xsl:when test="validationErrorType='InvalidFormat'">
						<xsl:text><xsl:value-of select="$validationError.invalidFormat"/></xsl:text>
					</xsl:when>		
					<xsl:otherwise>
						<xsl:text><xsl:value-of select="$validationError.unknown"/></xsl:text>
					</xsl:otherwise>
				</xsl:choose>
				
				<xsl:text>&#x20;</xsl:text>
				
				<xsl:choose>
					<xsl:when test="fieldName = 'name'">
						<xsl:text><xsl:value-of select="$name"/>!</xsl:text>
					</xsl:when>
					<xsl:when test="fieldName = 'description'">
						<xsl:text><xsl:value-of select="$description"/>!</xsl:text>
					</xsl:when>
					<xsl:when test="fieldName = 'alias'">
						<xsl:text><xsl:value-of select="$alias"/>!</xsl:text>
					</xsl:when>
					<xsl:when test="fieldName = 'text'">
						<xsl:text><xsl:value-of select="$content"/>!</xsl:text>
					</xsl:when>																																						
					<xsl:otherwise>
						<xsl:value-of select="fieldName"/><xsl:text>!</xsl:text>
					</xsl:otherwise>
				</xsl:choose>			
			</p>
		</xsl:if>
		
		<xsl:if test="messageKey">
			<p style="color: red;">
				<xsl:value-of select="$validationError.unknownErrorOccurred"/>
			</p>
		</xsl:if>
		
	</xsl:template>	
	
	<xsl:template match="addNewsForm">
		
		<h1><xsl:value-of select="$addNewsInSection"/><xsl:text>&#x20;</xsl:text><xsl:value-of select="section/name"/></h1>
		
		<xsl:apply-templates select="validationException/validationError"/>
		
		<form method="post" action="{/document/requestinfo/url}" accept-charset="ISO-8859-1">
			
			<table width="100%">
				<tr>
					<td><xsl:value-of select="$title"/>:</td>
					<td><input type="text" size="50" maxlength="255" name="title" value="{requestparameters/parameter[name='title']/value}"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<h2><xsl:value-of select="$text"/></h2>							
												
						<textarea class="fckeditor" name="text" rows="20">
							<xsl:value-of select="requestparameters/parameter[name='text']/value"/>
						</textarea>						
						
						<xsl:call-template name="initFCKEditor" /> 
					</td>
				</tr>
			</table>
			
			<h2><xsl:value-of select="$additionalSettings"/></h2>
			
			<table>
				<tr>
					<td>
						<input type="checkbox" name="enabled">
							
							<xsl:choose>
								<xsl:when test="requestparameters">
									<xsl:if test="requestparameters/parameter[name='enabled']">
										<xsl:attribute name="checked">true</xsl:attribute>
									</xsl:if>								
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="checked">true</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							
						</input><xsl:value-of select="$activatePage"/>
					</td>
				</tr>						
							
			</table>
			
			<!-- <h2><xsl:value-of select="$access"/></h2>
			
			<table>					
				<tr>
					<td>
						<input type="checkbox" name="adminAccess">
							<xsl:if test="requestparameters/parameter[name='adminAccess']">
								<xsl:attribute name="checked">true</xsl:attribute>
							</xsl:if>						
						</input><xsl:value-of select="$admins"/>
					</td>
				</tr><tr>
					<td>
						<input type="checkbox" name="userAccess">
							<xsl:if test="requestparameters/parameter[name='userAccess']">
								<xsl:attribute name="checked">true</xsl:attribute>
							</xsl:if>
						</input><xsl:value-of select="$loggedInUsers"/>
					</td>
				</tr><tr>
					<td>
						<input type="checkbox" name="anonymousAccess">
							<xsl:if test="requestparameters/parameter[name='anonymousAccess']">
								<xsl:attribute name="checked">true</xsl:attribute>
							</xsl:if>
						</input><xsl:value-of select="$nonLoggedInUsers"/>
					</td>
				</tr>
				
																																											
			</table>
			
			<xsl:apply-templates select="groups"/>
			
			<xsl:apply-templates select="users"/> -->			
			
			<div align="right">
				<input type="submit" value="{$addPage}"/>
			</div>										
		</form>		
	</xsl:template>	
	<!-- 
	<xsl:template match="group">
		<div class="floatleft full border marginbottom">
			<div class="floatleft">
				<xsl:choose>
					<xsl:when test="enabled='true'">
						<img class="alignbottom" src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/group.png"/>
					</xsl:when>
					<xsl:otherwise>
						<img class="alignbottom" src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/group_disabled.png"/>
					</xsl:otherwise>
				</xsl:choose>
				
				<xsl:text>&#x20;</xsl:text>
				
				<xsl:value-of select="name"/>			
			</div>
			<div class="floatright marginright">
				
				<xsl:variable name="groupID" select="groupID"/>
			
				<input type="checkbox" name="group" value="{groupID}">
					<xsl:choose>
						<xsl:when test="../../requestparameters">
							<xsl:if test="../../requestparameters/parameter[name='group'][value=$groupID]">
								<xsl:attribute name="checked"/>
							</xsl:if>						
						</xsl:when>
						<xsl:when test="../../page">
							<xsl:if test="../../page/allowedGroupIDs[groupID=$groupID]">
								<xsl:attribute name="checked"/>
							</xsl:if>								
						</xsl:when>					
					</xsl:choose>
				</input>
			</div>				
		</div>
	</xsl:template>	
	
	<xsl:template match="user">
		<div class="floatleft full border marginbottom">
			<div class="floatleft">
				<xsl:choose>
					<xsl:when test="enabled='true'">
						<img class="alignbottom" src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/user.png"/>
					</xsl:when>
					<xsl:otherwise>
						<img class="alignbottom" src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/user_disabled.png"/>
					</xsl:otherwise>
				</xsl:choose>
				
				<xsl:text>&#x20;</xsl:text>
				
				<xsl:value-of select="firstname"/>
				
				<xsl:text>&#x20;</xsl:text>
				
				<xsl:value-of select="lastname"/>
				
				<xsl:text>&#x20;</xsl:text>
				
				<xsl:text>(</xsl:text>
					<xsl:value-of select="username"/>
				<xsl:text>)</xsl:text>			
			</div>
			<div class="floatright marginright">
				
				<xsl:variable name="userID" select="userID"/>
			
				<input type="checkbox" name="user" value="{userID}">
					<xsl:choose>
						<xsl:when test="../../requestparameters">
							<xsl:if test="../../requestparameters/parameter[name='user'][value=$userID]">
								<xsl:attribute name="checked"/>
							</xsl:if>						
						</xsl:when>						
						<xsl:when test="../../page">
							<xsl:if test="../../page/allowedUserIDs[userID=$userID]">
								<xsl:attribute name="checked"/>
							</xsl:if>								
						</xsl:when>						
					</xsl:choose>
				</input>
			</div>				
		</div>
	</xsl:template>		
	 -->
	<xsl:template match="preview">
				
		<div align="right" style="float:right">
			<a href="{/document/requestinfo/contextpath}{/document/requestinfo/servletpath}{section/fullAlias}/{module/alias}/move/{news/newsID}" title="{$movePage}: {news/title}">
				<img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_move.png"/>
			</a>
			
			<a href="{/document/requestinfo/contextpath}{/document/requestinfo/servletpath}{section/fullAlias}/{module/alias}/copy/{news/newsID}" title="{$copyPage}: {news/title}">
				<img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_copy.png"/>
			</a>		 
		 
			<a href="{/document/requestinfo/currentURI}/{/document/module/alias}/update/{news/newsID}" title="{$editPage} {news/title}">
				<img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_edit.png"/>
			</a>				
		
			<a title="{$deletePage}: {news/title}">
				<xsl:attribute name="href">javascript:askBeforeRedirect('<xsl:value-of select="$deletePage"/> "<xsl:value-of select="news/title"/>?','<xsl:value-of select="/document/requestinfo/currentURI"/>/<xsl:value-of select="/document/module/alias"/>/delete/<xsl:value-of select="news/newsID"/>');</xsl:attribute>					
				<img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/page_delete.png" border="0" alt="{$deletePage}"/>
			</a>
		</div>
		
		<h1><xsl:value-of select="pagePreview"/> "<xsl:value-of select="news/title"/>" <xsl:value-of select="$inSection"/> "<xsl:value-of select="section/name"/>"</h1>
		
		<xsl:if test="module">
			<p><a href="{/document/requestinfo/contextpath}{/document/requestinfo/servletpath}{section/fullAlias}/{module/alias}/page/{page}#news{news/newsID}"><xsl:value-of select="$showPageOutsideAdminView"/></a></p>
		</xsl:if>
		
		<br/>		

		<xsl:value-of select="news/text" disable-output-escaping="yes"/>
	</xsl:template>		
	
	<xsl:template match="moveNewsForm">
				
		<h1><xsl:value-of select="$movePage"/> "<xsl:value-of select="news/title"/>"</h1>
		<p><xsl:value-of select="$movePageInstruction"/></p>
		
		<div class="dtree">
			<p><a href="javascript:sectiontree{/document/module/moduleID}.openAll();"><xsl:value-of select="$expandAll"/></a> | <a href="javascript:sectiontree{/document/module/moduleID}.closeAll();"><xsl:value-of select="$collapseAll"/></a></p>

			<script type="text/javascript">
				sectiontree<xsl:value-of select="/document/module/moduleID"/> = new dTree('sectiontree<xsl:value-of select="/document/module/moduleID"/>','<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/');
				sectiontree<xsl:value-of select="/document/module/moduleID"/>.icon.root = '<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/img/globe.gif';
				<xsl:apply-templates select="sections/section" mode="move"/>
				
				document.write(sectiontree<xsl:value-of select="/document/module/moduleID"/>);
			</script>
		</div>
	</xsl:template>
	
	<xsl:template match="section" mode="move">
	
		<xsl:variable name="parentSectionID">
			<xsl:choose>
				<xsl:when test="parentSectionID">
					<xsl:text>section</xsl:text>
					<xsl:value-of select="parentSectionID"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>-1</xsl:text>
				</xsl:otherwise>
			</xsl:choose>			
		</xsl:variable>
	
		<xsl:variable name="name">
            <xsl:call-template name="common_js_escape">
               <xsl:with-param name="text" select="name" />
            </xsl:call-template>			
		</xsl:variable>
		
		<xsl:variable name="description">
            <xsl:call-template name="common_js_escape">
               <xsl:with-param name="text" select="description" />
            </xsl:call-template>			
		</xsl:variable>			

		sectiontree<xsl:value-of select="/document/module/moduleID"/>.add('section<xsl:value-of select="sectionID"/>','<xsl:value-of select="$parentSectionID"/>','<xsl:value-of select="$name"/>','<xsl:value-of select="/document/requestinfo/currentURI"/>/<xsl:value-of select="/document/module/alias"/>/move/<xsl:value-of select="/document/moveNewsForm/news/newsID"/>/<xsl:value-of select="sectionID"/>','<xsl:value-of select="$description"/>','','<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/img/folder.gif','<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/img/folderopen.gif','','<xsl:if test="noPageModuleWarning"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/warning.png" alt="{$varning.noPageviewModuleInSection}" title="{$varning.noPageviewModuleInSection}"/></xsl:if>');

		<xsl:apply-templates select="subsections/section" mode="move"/>
	</xsl:template>
	
	<xsl:template match="copyNewsForm">
				
		<h1><xsl:value-of select="$copyPage"/> "<xsl:value-of select="news/title"/>"</h1>
		<p><xsl:value-of select="$copyPageInstruction"/></p>
		
		<div class="dtree">
			<p><a href="javascript:sectiontree{/document/module/moduleID}.openAll();"><xsl:value-of select="$expandAll"/></a> | <a href="javascript:sectiontree{/document/module/moduleID}.closeAll();"><xsl:value-of select="$collapseAll"/></a></p>

			<script type="text/javascript">
				sectiontree<xsl:value-of select="/document/module/moduleID"/> = new dTree('sectiontree<xsl:value-of select="/document/module/moduleID"/>','<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/');
				sectiontree<xsl:value-of select="/document/module/moduleID"/>.icon.root = '<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/img/globe.gif';
				<xsl:apply-templates select="sections/section" mode="copy"/>
				
				document.write(sectiontree<xsl:value-of select="/document/module/moduleID"/>);
			</script>
		</div>
	</xsl:template>
	
	<xsl:template match="section" mode="copy">
	
		<xsl:variable name="parentSectionID">
			<xsl:choose>
				<xsl:when test="parentSectionID">
					<xsl:text>section</xsl:text>
					<xsl:value-of select="parentSectionID"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>-1</xsl:text>
				</xsl:otherwise>
			</xsl:choose>			
		</xsl:variable>
	
		<xsl:variable name="name">
            <xsl:call-template name="common_js_escape">
               <xsl:with-param name="text" select="name" />
            </xsl:call-template>			
		</xsl:variable>
		
		<xsl:variable name="description">
            <xsl:call-template name="common_js_escape">
               <xsl:with-param name="text" select="description" />
            </xsl:call-template>			
		</xsl:variable>			

		sectiontree<xsl:value-of select="/document/module/moduleID"/>.add('section<xsl:value-of select="sectionID"/>','<xsl:value-of select="$parentSectionID"/>','<xsl:value-of select="$name"/>','<xsl:value-of select="/document/requestinfo/currentURI"/>/<xsl:value-of select="/document/module/alias"/>/copy/<xsl:value-of select="/document/copyNewsForm/news/newsID"/>/<xsl:value-of select="sectionID"/>','<xsl:value-of select="$description"/>','','<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/img/folder.gif','<xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/dtree/img/folderopen.gif','','<xsl:if test="noPageModuleWarning"><img src="{/document/requestinfo/contextpath}/static/f/{/document/module/sectionID}/{/document/module/moduleID}/pics/warning.png" alt="{$varning.noPageviewModuleInSection}" title="{$varning.noPageviewModuleInSection}"/></xsl:if>');

		<xsl:apply-templates select="subsections/section" mode="copy"/>
	</xsl:template>
	
	<xsl:template match="groups">
		<h3><xsl:value-of select="$groups"/></h3>

		<div class="scrolllist">			
			<xsl:apply-templates select="group"/>
		</div>
		
		<br/>
	</xsl:template>	
	
	<xsl:template match="users">
		<h3><xsl:value-of select="$users"/></h3>
		
		<div class="scrolllist">			
			<xsl:apply-templates select="user"/>
		</div>
		
		<br/>
	</xsl:template>
	
	<xsl:template name="initFCKEditor">
		
		<!-- Call global CKEditor init template -->
		<xsl:call-template name="initializeFCKEditor">
			<xsl:with-param name="basePath"><xsl:value-of select="/document/requestinfo/contextpath"/>/static/f/<xsl:value-of select="/document/module/sectionID"/>/<xsl:value-of select="/document/module/moduleID"/>/ckeditor/</xsl:with-param>
			<xsl:with-param name="customConfig">config.js</xsl:with-param>
			<xsl:with-param name="editorContainerClass">fckeditor</xsl:with-param>
			<xsl:with-param name="editorHeight">400</xsl:with-param>
			<xsl:with-param name="filebrowserBrowseUri">filemanager/index.html?Connector=<xsl:value-of select="/document/requestinfo/uri"/>/../../connector</xsl:with-param>
			<xsl:with-param name="filebrowserImageBrowseUri">filemanager/index.html?Connector=<xsl:value-of select="/document/requestinfo/uri"/>/../../connector</xsl:with-param>
			<xsl:with-param name="contentsCss">
				<xsl:if test="cssPath">
					<xsl:value-of select="/document/requestinfo/contextpath"/><xsl:value-of select="cssPath"/>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>
		
	</xsl:template>
</xsl:stylesheet>