<?xml version="1.0" encoding="ISO-8859-1" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" version="4.0" encoding="ISO-8859-1"/>

	<xsl:include href="classpath://se/unlogic/hierarchy/core/utils/xsl/Common.xsl"/>
	
	<xsl:variable name="imagePath"><xsl:value-of select="/Document/requestinfo/contextpath" />/static/f/<xsl:value-of select="/Document/module/sectionID" />/<xsl:value-of select="/Document/module/moduleID" />/pics</xsl:variable>

	<xsl:variable name="globalscripts">
		/jquery/jquery.js
		/jquery/jquery-ui.js
		/ckeditor/ckeditor.js
		/ckeditor/adapters/jquery.js
		/ckeditor/init.js
	</xsl:variable>	

	<xsl:variable name="scripts">
		/common/js/queryadmin.js
		/js/calculationbasisqueryadmin.js
	</xsl:variable>

	<xsl:template match="Document">	
		
		<div id="CalculationBasisQueryProvider" class="contentitem">
			<div>Så här ser frågan ut för administratören</div>
			<xsl:apply-templates select="validationError"/>
			
			<xsl:apply-templates select="ShowCalculationBasisQuery"/>
			<xsl:apply-templates select="UpdateCalculationBasisQuery"/>
			<xsl:apply-templates select="AddCalculationBasis"/>
			<xsl:apply-templates select="UpdateCalculationBasis"/>
			<xsl:apply-templates select="SortCalculationBasiss"/>
		</div>
		
	</xsl:template>

	<xsl:template match="ShowCalculationBasisQuery">
	
		<h1><xsl:value-of select="$i18n.UpdateQuery" /><xsl:text>:&#160;</xsl:text><xsl:value-of select="CalculationBasisQuery/QueryDescriptor/name" /></h1>
		
		<fieldset>
			<legend><xsl:value-of select="$i18n.BaseInfo"/></legend>
			
			<div class="floatright">
				<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/updatequery/{CalculationBasisQuery/queryID}" title="{$i18n.UpdateBaseInformation}">
					<img class="alignbottom" src="{$imagePath}/pen.png"/>
				</a>
			</div>
			
			<xsl:call-template name="createCommonShowFields">
				<xsl:with-param name="element" select="CalculationBasisQuery" />
			</xsl:call-template>
			
			<xsl:variable name="layout" select="CalculationBasisQuery/layout" />
			
			<div class="floatleft full bigmarginbottom">
				<label class="floatleft clearboth"><xsl:value-of select="$i18n.Layout" /></label>
				<div class="floatleft full">
					<xsl:value-of select="FieldLayout[value = $layout]/name" />
			    </div>
			</div>
			
		</fieldset>
		
		<fieldset>
			<legend><xsl:value-of select="$i18n.CalculationBasiss"/></legend>
			
			<xsl:if test="CalculationBasisQuery/Fields/CalculationBasis">
				
				<div class="floatleft full">
				
					<xsl:apply-templates select="CalculationBasisQuery/Fields/CalculationBasis" mode="list" />
			
				</div>
			
			</xsl:if>
			
			<xsl:if test="CalculationBasisQuery/Fields/CalculationBasis">
			
				<div class="floatright">
					<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/sortcalculationbasiss/{CalculationBasisQuery/queryID}" title="{$i18n.SortCalculationBasiss.Title}">
						<xsl:value-of select="$i18n.SortCalculationBasiss.Title"/>
						<img class="alignbottom marginleft" src="{$commonImagePath}/move.png"/>
					</a>
				</div>
				
			</xsl:if>
			
			<div class="clearboth floatright">
				<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/addcalculationbasis/{CalculationBasisQuery/queryID}" title="{$i18n.AddCalculationBasis}">
					<xsl:value-of select="$i18n.AddCalculationBasis"/>
					<img class="alignbottom marginleft" src="{$commonImagePath}/add.png"/>
				</a>
			</div>
			
		</fieldset>
		
		<div class="floatright margintop clearboth">
			<input type="button" value="{$i18n.Done}" onclick="window.location = '{showFlowURL}'" />
		</div>
		
	</xsl:template>
		
	<xsl:template match="CalculationBasis" mode="list">
				
		<div class="floatleft hover border full marginbottom border-radius lightbackground">
			
			<div class="padding floatleft">
				<div class="marginleft"><b><xsl:value-of select="label" /></b></div>
			</div>
			<div class="padding floatright">
				<div class="floatright marginright">
					<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/deletecalculationbasis/{calculationBasisID}" onclick="return confirm('{$i18n.DeleteCalculationBasis.Confirm}: {label}?');" title="{$i18n.DeleteCalculationBasis}: {label}">
						<img src="{$imagePath}/delete.png"/>
					</a>
				</div>
				<div class="floatright">
					<a href="{/Document/requestinfo/currentURI}/{/Document/module/alias}/updatecalculationbasis/{calculationBasisID}" title="{$i18n.UpdateCalculationBasis}: {label}">
						<img src="{$imagePath}/pen.png"/>
					</a>
				</div>
			</div>
			
		</div>
				
	</xsl:template>
	
	<xsl:template match="AddCalculationBasis">
	
		<h1><xsl:value-of select="$i18n.AddCalculationBasis" /></h1>
		
		<xsl:apply-templates select="validationException/validationError" />
	
		<form id="addCalculationBasisForm" name="queryAdminForm" method="post" action="{/Document/requestinfo/uri}">
			
			<xsl:call-template name="showCalculationBasisForm" />
		
			<div class="floatright margintop clearboth">
				<input type="submit" value="{$i18n.Add}" />
			</div>
		
		</form>
	
	</xsl:template>
	
	<xsl:template match="UpdateCalculationBasis">
	
		<h1><xsl:value-of select="$i18n.UpdateCalculationBasis" /><xsl:text>:&#160;</xsl:text><xsl:value-of select="CalculationBasis/label" /></h1>
		
		<xsl:apply-templates select="validationException/validationError" />
		
		<form id="udpateCalculationBasisForm" name="queryAdminForm" method="post" action="{/Document/requestinfo/uri}">
		
			<xsl:call-template name="showCalculationBasisForm">
				<xsl:with-param name="element" select="CalculationBasis" />
			</xsl:call-template>
		
			<div class="floatright margintop clearboth">
				<input type="submit" value="{$i18n.SaveChanges}" />
			</div>
		
		</form>
	
	</xsl:template>
	
	<xsl:template name="showCalculationBasisForm">
		
		<xsl:param name="element" select="null" />
		
		<div class="floatleft full bigmarginbottom">
			<label for="label" class="floatleft clearboth"><xsl:value-of select="$i18n.Label" /></label>
			<div class="floatleft full">
				<xsl:call-template name="createCalculationBasis">
					<xsl:with-param name="id" select="'label'"/>
					<xsl:with-param name="name" select="'label'"/>
					<xsl:with-param name="title" select="$i18n.Label"/>
					<xsl:with-param name="element" select="$element" />
				</xsl:call-template>
		    </div>
		</div>
		
		<div class="floatleft full bigmarginbottom">
			<div class="floatleft full">
				<xsl:call-template name="createCheckbox">
					<xsl:with-param name="id" select="'required'" />
					<xsl:with-param name="name" select="'required'" />
					<xsl:with-param name="element" select="$element" /> 
					<xsl:with-param name="class" select="'vertical-align-middle'" />
				</xsl:call-template>
					
				<label for="required">
					<xsl:value-of select="$i18n.Required" />
				</label>
		    </div>
		</div>
		
		<div class="floatleft full bigmarginbottom">
			<label for="width" class="floatleft clearboth"><xsl:value-of select="$i18n.Width" /></label>
			<div class="floatleft full">
				<xsl:call-template name="createCalculationBasis">
					<xsl:with-param name="id" select="'width'"/>
					<xsl:with-param name="name" select="'width'"/>
					<xsl:with-param name="title" select="$i18n.Width"/>
					<xsl:with-param name="size" select="'30'" />
					<xsl:with-param name="element" select="$element" />
				</xsl:call-template>
		    </div>
		</div>
		
		<div class="floatleft full bigmarginbottom">
			<label for="maxContentLength" class="floatleft clearboth"><xsl:value-of select="$i18n.MaxLength" /></label>
			<div class="floatleft full">
				<xsl:call-template name="createCalculationBasis">
					<xsl:with-param name="id" select="'maxContentLength'"/>
					<xsl:with-param name="name" select="'maxContentLength'"/>
					<xsl:with-param name="title" select="$i18n.MaxLength"/>
					<xsl:with-param name="size" select="'30'"/>
					<xsl:with-param name="element" select="$element" />
				</xsl:call-template>
		    </div>
		</div>
		
		<div class="floatleft full bigmarginbottom">
			<label for="formatValidator" class="floatleft clearboth"><xsl:value-of select="$i18n.FormatValidator" /></label>
			<div class="floatleft full">
				<xsl:call-template name="createDropdown">
					<xsl:with-param name="id" select="'formatValidator'" />
					<xsl:with-param name="name" select="'formatValidator'" />
					<xsl:with-param name="valueElementName" select="'className'" />
					<xsl:with-param name="labelElementName" select="'name'" />
					<xsl:with-param name="element" select="FormatValidator" />
					<xsl:with-param name="addEmptyOption" select="'&#160;'" />
					<xsl:with-param name="selectedValue" select="$element/formatValidator" />
				</xsl:call-template>
				<xsl:apply-templates select="FormatValidator" />
		    </div>
		</div>
		
		<div class="floatleft full bigmarginbottom">
			<label for="invalidFormatMessage" class="floatleft clearboth"><xsl:value-of select="$i18n.InvalidFormatMessage" /></label>
			<div class="floatleft full">
				<xsl:call-template name="createCalculationBasis">
					<xsl:with-param name="id" select="'invalidFormatMessage'"/>
					<xsl:with-param name="name" select="'invalidFormatMessage'"/>
					<xsl:with-param name="title" select="$i18n.InvalidFormatMessage"/>
					<xsl:with-param name="element" select="$element" />
				</xsl:call-template>
		    </div>
		</div>
		
	</xsl:template>
	
	<xsl:template match="FormatValidator">
		
		<xsl:call-template name="createHiddenField">
			<xsl:with-param name="id" select="concat('validatorMessage-', formatValidatorID)" />
			<xsl:with-param name="name" select="concat('validatorMessage-', formatValidatorID)" />
			<xsl:with-param name="value" select="validationMessage" />
		</xsl:call-template>
		
	</xsl:template>
	
	<xsl:template match="UpdateCalculationBasisQuery">
	
		<h1><xsl:value-of select="$i18n.UpdateBaseInformation" /></h1>
		
		<xsl:apply-templates select="validationException/validationError" />
		
		<form id="updateCalculationBasisQueryForm" name="queryAdminForm" method="post" action="{/Document/requestinfo/uri}">
		
			<xsl:call-template name="createCommonFieldsForm">
				<xsl:with-param name="element" select="CalculationBasisQuery" />
			</xsl:call-template>
			
			<div class="floatleft full bigmarginbottom">
				<label for="layout" class="floatleft clearboth"><xsl:value-of select="$i18n.Layout" /></label>
				<div class="floatleft full">
					<xsl:call-template name="createDropdown">
						<xsl:with-param name="id" select="'layout'"/>
						<xsl:with-param name="name" select="'layout'"/>
						<xsl:with-param name="valueElementName" select="'value'" />
						<xsl:with-param name="labelElementName" select="'name'" />
						<xsl:with-param name="element" select="FieldLayout" />
						<xsl:with-param name="selectedValue" select="CalculationBasisQuery/layout" />
					</xsl:call-template>
			    </div>
			</div>
			
			<div class="floatright margintop clearboth">
				<input type="submit" value="{$i18n.SaveChanges}" />
			</div>
		
		</form>
		
	</xsl:template>

	<xsl:template match="SortCalculationBasiss">
		
		<h1><xsl:value-of select="$i18n.SortCalculationBasiss" /><xsl:text>:&#160;</xsl:text><xsl:value-of select="CalculationBasisQuery/QueryDescriptor/name" /></h1>
		
		<form id="sortCalculationBasissForm" name="queryAdminForm" method="post" action="{/Document/requestinfo/uri}">
		
			<div class="floatleft full sortable">
							
				<xsl:apply-templates select="CalculationBasisQuery/Fields/CalculationBasis" mode="sort" />
							
			</div>
			
			<div class="floatright margintop clearboth">
				<input type="submit" value="{$i18n.SaveChanges}" />
			</div>

		</form>
		
	</xsl:template>
	
	<xsl:template match="CalculationBasis" mode="sort">
	
		<div id="calculationbasis_{calculationBasisID}" class="floatleft hover border full marginbottom lightbackground cursor-move border-radius">
			<div class="padding">
				<img class="vertical-align-middle marginright" src="{$commonImagePath}/move.png" title="{$i18n.MoveAlternative}"/>
				<xsl:value-of select="label" />
				<xsl:call-template name="createHiddenField">
					<xsl:with-param name="id" select="concat('sortorder_', calculationBasisID)" />
					<xsl:with-param name="name" select="concat('sortorder_', calculationBasisID)" />
					<xsl:with-param name="value" select="sortIndex" />
					<xsl:with-param name="requestparameters" select="//requestparameters" />
				</xsl:call-template>
			</div>
		</div>
	
	</xsl:template>
	
	<xsl:template match="validationError[messageKey = 'UpdateFailedCalculationBasisQueryNotFound']">
		
		<p class="error">
			<xsl:value-of select="$i18n.CalculationBasisQueryNotFound" />
		</p>
		
	</xsl:template>

	<xsl:template match="fieldName">

		<xsl:variable name="fieldName" select="." />
	
		<xsl:choose>
			<xsl:when test="$fieldName = 'maxContentLength'">
				<xsl:value-of select="$i18n.maxLength" />
			</xsl:when>
			<xsl:when test="$fieldName = 'label'">
				<xsl:value-of select="$i18n.label" />
			</xsl:when>
			<xsl:when test="$fieldName = 'width'">
				<xsl:value-of select="$i18n.width" />
			</xsl:when>
			<xsl:when test="$fieldName = 'invalidFormatMessage'">
				<xsl:value-of select="$i18n.invalidFormatMessage" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$fieldName" />
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>

</xsl:stylesheet>