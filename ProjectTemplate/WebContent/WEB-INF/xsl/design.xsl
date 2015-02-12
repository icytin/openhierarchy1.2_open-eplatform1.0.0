<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="ISO-8859-1" indent="yes" />
  
	<xsl:include href="classpath://se/unlogic/hierarchy/core/xsl/CommonCoreTemplates.xsl" />
	<xsl:include href="classpath://se/unlogic/hierarchy/core/xsl/Errors.sv.xsl" />
  
	<xsl:template match="document">
    <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html></xsl:text>
    <xsl:comment><![CDATA[[if lt IE 8]>      <html class="no-js lt-ie9 lt-ie8"> <![endif]]]></xsl:comment>
    <xsl:comment><![CDATA[[if IE 8]>         <html class="no-js lt-ie9"> <![endif]]]></xsl:comment>
    <xsl:comment><![CDATA[[if gt IE 8]><!]]></xsl:comment>
    <html class="no-js" lang="sv">
    <xsl:comment><![CDATA[<![endif]]]></xsl:comment>
    <head>
      <meta charset="ISO-8859-1" />
      <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
      
      <title>
      	<xsl:text>ProjectTemplate</xsl:text>
      	<xsl:if test="title">
      		<xsl:text> - </xsl:text>
      		<xsl:value-of select="title" />
      	</xsl:if>
      </title>
      
      <!-- Latest compiled and minified CSS -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" />
      	
      <!-- Optional theme -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
      
      <!-- Project styles -->
      <link rel="stylesheet" href="{/document/requestinfo/contextpath}/css/styles.css" />
      
      <!-- Openhierarchy styles -->
      <link href="{/document/requestinfo/contextpath}/css/openhierarchy.css" rel="stylesheet" type="text/css" media="screen,projection" />
      
      <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
      <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
      <xsl:comment><![CDATA[[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <![endif]]]></xsl:comment>
      
      <xsl:apply-templates select="links/link" />
      
      <xsl:apply-templates select="scripts/script" />
	</head>
	<body class="non-responsive">
      <xsl:comment><![CDATA[[if lt IE 9]>
          <div class="outdated-browser">
            <p>
              <img src="assets/images/sad.png" alt="Inget browserstöd" />
              Din webbläsare är <em>väldigt</em> gammal. Den är så pass gammal att den här webbplatsen inte kommer att fungera riktigt som den ska. På <a href="http://browsehappy.com/">browsehappy.com</a> kan du få hjälp med att uppgradera din webbläsare och förbättra upplevelsen.
            </p>
          </div>
        <![endif]]]></xsl:comment>
      
      <xsl:call-template name="page" />
      
      <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
      
      <!-- Latest compiled and minified JavaScript -->
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    </body>
    </html>
  </xsl:template>
  
  <xsl:template name="login">
    <xsl:apply-templates select="errors"/>
    <xsl:value-of select="moduleHTMLResponse" disable-output-escaping="yes"/>
    <xsl:value-of select="moduleTransformedResponse" disable-output-escaping="yes"/>
  </xsl:template>
  
  <xsl:template name="page">
    <xsl:call-template name="header" />
    
    <xsl:call-template name="content" />
    
    <xsl:call-template name="footer" />
    
  </xsl:template>
  
  <xsl:template name="header">
    <!-- Fixed navbar -->
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">ProjectTemplate</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
            <xsl:if test="user/admin = 'true'"><li><a href="{/document/requestinfo/contextpath}/sysadmin">Systemadmin</a></li></xsl:if>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
  </xsl:template>
  
  <xsl:template name="content">
    <xsl:call-template name="submenu" />
    
    <div class="container theme-showcase" role="main">

      <!-- Main jumbotron for a primary marketing message or call to action -->
      <div class="jumbotron">
        <xsl:apply-templates select="errors"/>
        <xsl:value-of select="moduleHTMLResponse" disable-output-escaping="yes"/>
        <xsl:value-of select="moduleTransformedResponse" disable-output-escaping="yes"/>
      </div>
    </div>
  </xsl:template>
  
  <xsl:template name="footer">
  </xsl:template>
  
  <xsl:template name="submenu">
    <nav class="of-sidebar-menu">
        <xsl:choose>
          <xsl:when test="not(menus/menu/menuitem/menu)">
            <xsl:apply-templates select="menus/menu/menuitem[itemType!='SECTION']"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select="menus/menu/menuitem/menu"/>
          </xsl:otherwise>
        </xsl:choose>
    </nav>
  </xsl:template>
  
  <xsl:template match="menuitem" mode="sections">
    <li>
      <xsl:if test="starts-with(/document/requestinfo/uri, concat(/document/requestinfo/contextpath, url))">
        <xsl:attribute name="class">active</xsl:attribute>
      </xsl:if>
      <a>
        <xsl:choose>
          <xsl:when test="urlType='RELATIVE_FROM_CONTEXTPATH'">
            <xsl:attribute name="href"><xsl:value-of
              select="/document/requestinfo/contextpath" /><xsl:value-of
              select="url" /></xsl:attribute>
          </xsl:when>
          <xsl:when test="urlType='FULL'">
            <xsl:attribute name="href"><xsl:value-of select="url" /></xsl:attribute>
          </xsl:when>
        </xsl:choose>

        <xsl:value-of select="name" />
      </a>
    </li>
  </xsl:template>
  
  <xsl:template match="menuitem">
    <xsl:variable name="menuLevel" select="count(../preceding-sibling::menuitems)" />
    
    <!-- Add indentation padding if child -->
    <xsl:variable name="padding-left">
      <xsl:choose>
        <xsl:when test="$menuLevel > 1">
          <xsl:value-of select="($menuLevel - 1) * 10" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="0" />
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    
    <xsl:choose>
      <xsl:when test="itemType='BLANK'">
        <xsl:if test="position() != 1 and (preceding-sibling::menuitem[position()=1]/itemType = 'MENUITEM' or (preceding-sibling::menuitem[position()=1]/itemType = 'SECTION' and $menuLevel > 0) or ($menuLevel > 1 and preceding-sibling::menuitem[position()=1]/itemType = 'TITLE'))">
          <xsl:text disable-output-escaping="yes"><![CDATA[</ul>]]></xsl:text>
        </xsl:if>
        <xsl:choose>
          <xsl:when test="1 > $menuLevel">
            <br />
          </xsl:when>
          <xsl:otherwise>
            <hr class="noscreen" />
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>

      <xsl:when test="itemType='MENUITEM' or itemType='SECTION' or (itemType='TITLE' and $menuLevel > 1)">
        <xsl:if test="position() = 1 or (preceding-sibling::menuitem[position()=1]/itemType != 'MENUITEM' and (preceding-sibling::menuitem[position()=1]/itemType != 'SECTION' or (preceding-sibling::menuitem[position()=1]/itemType = 'SECTION' and $menuLevel = 0)) and not($menuLevel > 1 and preceding-sibling::menuitem[position()=1]/itemType = 'TITLE'))">
          <xsl:choose>
            <xsl:when test="$menuLevel > 1">
              <xsl:text disable-output-escaping="yes"><![CDATA[<ul class="nested">]]></xsl:text>
            </xsl:when>
            <xsl:otherwise>
              <xsl:text disable-output-escaping="yes"><![CDATA[<ul data-of-menu="main">]]></xsl:text>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:if>
        <li>
          <xsl:choose>
            <xsl:when test="url">
              <!-- Add active as class if current page -->
              <xsl:if test="(itemType='SECTION' and ../following-sibling::menuitems[position()=1]/@sectionID=subSectionID) or 
                            (itemType='MENUITEM' and url and ((urlType='RELATIVE_FROM_CONTEXTPATH' and 
                                string-length(concat(/document/requestinfo/contextpath,url)) = string-length(/document/requestinfo/uri) and
                                starts-with(concat(/document/requestinfo/contextpath,url),/document/requestinfo/uri)) or
                            (urlType='FULL' and string-length(url) = string-length(/document/requestinfo/url) and starts-with(url,/document/requestinfo/url)))) or
                            /document/breadcrumbs/breadcrumb[last()]/url = url">
                <xsl:attribute name="class">
                  <xsl:text>of-icon active</xsl:text>
                </xsl:attribute>
              </xsl:if>
                
              <a class="of-icon">
                <xsl:choose>
                  <xsl:when test="urlType='RELATIVE_FROM_CONTEXTPATH'">
                    <xsl:attribute name="href">
                      <xsl:value-of select="/document/requestinfo/contextpath" />
                      <xsl:value-of select="url" />
                    </xsl:attribute>
                  </xsl:when>
                  <xsl:when test="urlType='FULL'">
                    <xsl:attribute name="href">
                      <xsl:value-of select="url" />
                    </xsl:attribute>
                  </xsl:when>
                </xsl:choose>
                <span><xsl:value-of select="name" /></span>
              </a>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="name" />
            </xsl:otherwise>
          </xsl:choose>
        </li>
        <xsl:if test="position() = last()">
          <xsl:text disable-output-escaping="yes"><![CDATA[</ul>]]></xsl:text>
        </xsl:if>
      </xsl:when>

      <xsl:when test="itemType='TITLE'">
        <xsl:if
          test="position() != 1 and (preceding-sibling::menuitem[position()=1]/itemType = 'MENUITEM' or (preceding-sibling::menuitem[position()=1]/itemType = 'SECTION' and $menuLevel > 0) or ($menuLevel > 1 and preceding-sibling::menuitem[position()=1]/itemType = 'TITLE'))">
          <xsl:text disable-output-escaping="yes"><![CDATA[</ul>]]></xsl:text>
        </xsl:if>

        <xsl:choose>
          <xsl:when test="url">
            <a title="{../description}">
              <xsl:choose>
                <xsl:when test="urlType='RELATIVE_FROM_CONTEXTPATH'">
                  <xsl:attribute name="href">
                      <xsl:value-of select="/document/requestinfo/contextpath" />
                      <xsl:value-of select="url" />
                    </xsl:attribute>
                </xsl:when>
                <xsl:when test="urlType='FULL'">
                  <xsl:attribute name="href">
                      <xsl:value-of select="url" />
                    </xsl:attribute>
                </xsl:when>
              </xsl:choose>
              <xsl:value-of select="name" />
            </a>
          </xsl:when>
          <xsl:otherwise>
            <h3><xsl:value-of select="name" /></h3>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>

      <xsl:otherwise>
        <xsl:if test="position() != 1 and (preceding-sibling::menuitem[position()=1]/itemType = 'MENUITEM' or (preceding-sibling::menuitem[position()=1]/itemType = 'SECTION' and $menuLevel > 0) or ($menuLevel > 1 and preceding-sibling::menuitem[position()=1]/itemType = 'TITLE'))">
          <xsl:text disable-output-escaping="yes"><![CDATA[</ul>]]></xsl:text>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:if test="itemType='SECTION' and ../following-sibling::menuitems[position()=1]/@sectionID=subSectionID and count(../following-sibling::menuitems[position()=1]/menuitem) > 0">
      <li>
        <xsl:apply-templates select="../following-sibling::menuitems[position()=1]/menuitem" />
      </li>
    </xsl:if>
  </xsl:template>

</xsl:stylesheet>